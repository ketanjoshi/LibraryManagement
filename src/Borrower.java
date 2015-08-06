import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Borrower
{

	private String fname;
	private String lname;
	private String address;
	private String phone;
	private int cardNumber;

	public String returnMessage = "";

	public Borrower(String fname, String lname, String address, String phone)
	{
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.phone = phone;
	}

	public Borrower(int card)
	{
		cardNumber = card;
	}

	public Borrower()
	{
	}

	public boolean addToDb()
	{
		Connection con = DatabaseWrapper.getConnection();

		if(con == null)
		{
			returnMessage = "Couldn't connect to the database.";
			return false;
		}

		String insertBorrowerStmt = "insert into borrower(card_no, fname, lname, address, phone) "
				+ "values(?,?,?,?,?)";
		try
		{
			//Check if user exists
			if(checkIfExists())
			{
				returnMessage = "User already exists. Cannot add a duplicate user.";
				return false;
			}
			PreparedStatement ps = con.prepareStatement(insertBorrowerStmt);

			cardNumber = generateNewCardNumber();
			ps.setInt(1, cardNumber);
			ps.setString(2, fname);
			ps.setString(3, lname);
			ps.setString(4, address);
			ps.setString(5, phone);
			ps.execute();
			returnMessage = "New borrower added successfully.";
			return true;
		}
		catch (Exception e)
		{
			returnMessage = e.getMessage();
			return false;
		}
	}

	private int generateNewCardNumber() throws SQLException
	{
		String readCardNum = "select nextid as card from next_id where idtype = 'card';";
		String updateCardNum = "update next_id set nextid = nextid + 1 where idtype = 'card';";

		ResultSet rs = DatabaseWrapper.ExecuteSelectQuery(readCardNum);
		rs.next();
		int cardNumber = rs.getInt("card");
		DatabaseWrapper.ExecuteUpdateQuery(updateCardNum);
		return cardNumber;
	}

	private boolean checkIfExists() throws SQLException
	{
		String query = "select count(card_no) as counter from borrower where fname = '" + fname + 
				"' and lname = '" + lname + "' and address = '" + address + 
				"' and phone = '" + phone + "';";
		ResultSet rs = DatabaseWrapper.ExecuteSelectQuery(query);
		rs.next();
		if(rs.getInt("counter") > 0)
			return true;
		else
			return false;
	}
	
	public boolean checkOutNewBook(String bookId, String branchId) throws SQLException
	{
		if(BookManagement.canCheckOutBooks(cardNumber) && (getIfAnyFine() == 0.0))
		{
			if(BookManagement.checkOutBook(bookId, branchId, cardNumber))
				return true;
			else {
				returnMessage = BookManagement.returnMessage;
				BookManagement.returnMessage = "";
				return false;
			}
		}
		else
		{
			returnMessage = BookManagement.returnMessage + "Cannot check out book.";
			BookManagement.returnMessage = "";
			return false;
		}
	}

	private double getIfAnyFine() throws SQLException
	{
		refreshFinesTable();

		// select sum(f.fine_amt) from fines as f, book_loans as bl 
		//where bl.card_no = 9040 and bl.loan_id = f.loan_id and f.paid is false;
		String query = "select sum(fine_amt) as amount from fines as f "
				+ "where card_no = " + cardNumber + " and paid is false;";
		ResultSet rs = DatabaseWrapper.ExecuteSelectQuery(query);
		rs.next();
		double fine = rs.getDouble("amount");
		if(fine != 0.0)
			returnMessage = "Currently you have fine of $" + fine + ".\n";
		return fine;
	}

	public boolean checkInBook(String loanId, String bookId, String branchId) 
			throws SQLException
	{
		double fineAmount = getIfAnyFine();
		if(fineAmount != 0.0)
			returnMessage = "You have been charged late fine of $" + fineAmount + " for this book.";
		BookManagement.checkInBook(Integer.parseInt(loanId), bookId, Integer.parseInt(branchId), cardNumber);
		return true;
	}

	public double[] getBorrowerStats()
	{
		try
		{
			refreshFinesTable();

			String totalCheckOutBooks = "select count(*) as totalbooks from book_loans where card_no = "
					+ cardNumber + " and isnull(date_in);";
			ResultSet rs1 = DatabaseWrapper.ExecuteSelectQuery(totalCheckOutBooks);
			rs1.next();
			int books = rs1.getInt("totalbooks");

			String finePaidSoFarQuery = "select sum(amount) as paidfine from BorrowerFine "
					+ "where card_no = " + cardNumber + " and paid is true;";
			ResultSet rs2 = DatabaseWrapper.ExecuteSelectQuery(finePaidSoFarQuery);
			rs2.next();
			double finePaid = rs2.getDouble("paidfine");

			String checkedInFineQuery = "select sum(amount) as fineamt from BorrowerFine "
					+ "where card_no = " + cardNumber + " and not isnull(date_in) and paid = false;";
			ResultSet rs3 = DatabaseWrapper.ExecuteSelectQuery(checkedInFineQuery);
			rs3.next();
			double checkedInFine = rs3.getDouble("fineamt");

			String checkedOutFineQuery = "select sum(amount) as fineamt from BorrowerFine "
					+ "where card_no = " + cardNumber + " and isnull(date_in) and paid = false;";
			ResultSet rs4 = DatabaseWrapper.ExecuteSelectQuery(checkedOutFineQuery);
			rs4.next();
			double checkedOutFine = rs4.getDouble("fineamt");

			double[] values = {books, finePaid, checkedInFine, checkedOutFine };
			return values;

		}
		catch (SQLException e)
		{
			returnMessage = e.getMessage();
			return null;
		}
	}
	
	private void refreshFinesTable() throws SQLException
	{
		String query = "select * from BorrowerFine where card_no = " + cardNumber 
				+ " and (paid = false or paid is null);";
		String replaceFinesQuery = "replace into fines values (?,?,?,?);";
		ResultSet rs = DatabaseWrapper.ExecuteSelectQuery(query);
		Connection con = DatabaseWrapper.getConnection();
		while(rs.next())
		{
			PreparedStatement ps = con.prepareStatement(replaceFinesQuery);
			ps.setInt(1, Integer.parseInt(rs.getString(1)));
			ps.setInt(2, cardNumber);
			ps.setDouble(3, rs.getDouble(3));
			ps.setBoolean(4, false);
			ps.execute();
		}
	}

	public void payFineAmount() throws SQLException
	{
		String getCheckInLoans = "select loan_id as loans from BorrowerFine where card_no = " + cardNumber
				+ " and not isnull(date_in);";
		ResultSet rs = DatabaseWrapper.ExecuteSelectQuery(getCheckInLoans);
		while (rs.next())
		{
			int loanId = rs.getInt("loans");
			String payFine = "update fines set paid = true where loan_id = " + loanId + ";";
			DatabaseWrapper.ExecuteUpdateQuery(payFine);
		}
		returnMessage = "Fine paid successfully.";
	}
}
