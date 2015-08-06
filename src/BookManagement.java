import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class BookManagement
{

	public static String returnMessage = "";

	public static boolean canCheckOutBooks(int cardNumber)
	{
		String query = "select count(*) as totalbooks from book_loans where card_no = " + cardNumber + 
				" AND isnull(date_in);";
		try
		{
			ResultSet rs = DatabaseWrapper.ExecuteSelectQuery(query);
			rs.next();
			if(rs.getInt("totalbooks") == 3)
			{
				returnMessage = "You already have 3 books checked out.\n";
				return false;
			}
			else
				return true;
		}
		catch (Exception e)
		{
			returnMessage = e.getMessage();
			return false;
		}
	}

	public static boolean checkInBook(int loanId, String bookId, int branchId, int card)
	{
		try
		{
			//Update book_loans table
			updateBookLoan(loanId, bookId, branchId);
			returnMessage = "Book has been checked in.\n";
			return true;
		}
		catch (Exception e)
		{
			returnMessage = e.getMessage();
			return false;
		}
	}

	private static void updateBookLoan(int loanId, String bookId, int branchId) throws SQLException
	{
		java.util.Date today = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		//Update book_loans table
		String bookLoanUpdateQuery = "update book_loans set date_in = '" + sdf.format(today) + 
				"' where loan_id = " + loanId + ";";
		DatabaseWrapper.ExecuteUpdateQuery(bookLoanUpdateQuery);
	}

	public static boolean checkOutBook(String bookId, String branchId, int cardNumber)
	{
		try
		{
			if(checkIfBooksAvailable(bookId, branchId))
			{
				//Add new entry in book loans
				addBookLoan(bookId, branchId, cardNumber);
				return true;
			}
			else
				return false;
		}
		catch (Exception e)
		{
			returnMessage = e.getMessage();
			return false;
		}
	}

	private static boolean checkIfBooksAvailable(String bookId, String branchId) throws SQLException
	{
		/*
		 * select available from SearchAvailableBooks where branch_id = 2 and book_id='0911625291';
		 */
		String checkCopies = "select available as copies from SearchAvailableBooks where "
				+ "book_id = '" + bookId + "' and branch_id = " + branchId + ";";
		ResultSet rs = DatabaseWrapper.ExecuteSelectQuery(checkCopies);
		if(!rs.next())
		{
			returnMessage = "No copies available at this branch. Try another branch.";
			return false;
		}
		else {
			return true;
		}
	}

	private static void addBookLoan(String bookId, String branchId, int cardNumber)
			throws SQLException
	{
		int loanId = readNewLoanId();
		insertNewLoanEntry(loanId, bookId, branchId, cardNumber);
		updateLoanId();
	}

	private static int readNewLoanId() throws SQLException
	{
		String readLoanId = "select nextid as loan from next_id where idtype = 'loan';";
		ResultSet rs = DatabaseWrapper.ExecuteSelectQuery(readLoanId);
		rs.next();
		return rs.getInt("loan");
	}

	private static void updateLoanId() throws SQLException
	{
		String updateLoanId = "update next_id set nextid = nextid + 1 where idtype = 'loan';";
		DatabaseWrapper.ExecuteUpdateQuery(updateLoanId);
	}

	private static void insertNewLoanEntry(int loanId, String bookId, String branchId, int cardNumber) 
			throws SQLException
	{
		Connection con = DatabaseWrapper.getConnection();
		String insertLoanQuery = "insert into book_loans(loan_id, book_id, branch_id, card_no, " +
			"date_out, due_date, date_in) values (?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(insertLoanQuery);
		ps.setInt(1, loanId);
		ps.setString(2, bookId);
		ps.setInt(3, Integer.parseInt(branchId));
		ps.setInt(4, cardNumber);

		java.util.Date today = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ps.setString(5, sdf.format(today));

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, 14);
		java.util.Date dueDate = cal.getTime();
		ps.setString(6, sdf.format(dueDate));

		ps.setNull(7, Types.DATE);
		ps.execute();
	}
}