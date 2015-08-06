import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;


public class SearchBook
{

	public static String returnMessage = null;

	public static DefaultTableModel returnLoanedBooks(BookLoanData bookloan)
	{
		DefaultTableModel loanedBooks = new DefaultTableModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
			
		};
		loanedBooks.addColumn("Card No.");
		loanedBooks.addColumn("Loan Id");
		loanedBooks.addColumn("Book Id");
		loanedBooks.addColumn("Branch Id");
		loanedBooks.addColumn("Book_Title");

		/*
		 * select l.loan_id, l.book_id, l.branch_id, b.title from book_loans as l, book as b 
		 * where l.book_id LIKE '%0967697603%' and b.book_id = l.book_id and 
		 * l.card_no like '%%' and isnull(l.date_in);
		 */
		String query = "select l.card_no, l.loan_id, l.book_id, l.branch_id, b.title from "
				+ "book_loans as l, book as b, borrower as br "
				+ "where l.book_id like '%" + bookloan.getBookId() + "%' and b.book_id = l.book_id and "
				+ "l.card_no like '%" + bookloan.getCardNumber() + "%' and isnull(l.date_in) and "
				+ "(br.fname like '%" + bookloan.getBorrower() + "%' OR br.lname like '%"
				+ bookloan.getBorrower() + "%') and br.card_no = l.card_no;";

		ResultSet rs;
		try
		{
			rs = DatabaseWrapper.ExecuteSelectQuery(query);
			while(rs.next())
			{
				String[] resultRecord = new String[5];
				resultRecord[0] = rs.getString(1);
				resultRecord[1] = rs.getString(2);
				resultRecord[2] = rs.getString(3);
				resultRecord[3] = rs.getString(4);
				resultRecord[4] = rs.getString(5);
				loanedBooks.addRow(resultRecord);
			}
		}
		catch (SQLException e)
		{
			returnMessage = e.getMessage();
			return null;
		}
		return loanedBooks;
	}

	public static DefaultTableModel returnAvailability(SearchBookQueryParams searchBook)
	{
		DefaultTableModel availableBooks = new DefaultTableModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		availableBooks.addColumn("Book Id");
		availableBooks.addColumn("Title");
		availableBooks.addColumn("Authors");
		availableBooks.addColumn("Branch Id");
		availableBooks.addColumn("Total Copies");
		availableBooks.addColumn("Available Copies"); 

		String query = "select * from SearchAvailableBooks where book_id LIKE '%" + searchBook.getBookId()
				+ "%' AND title LIKE '%" + searchBook.getBookTitle()
				+ "%' AND authors LIKE '%" + searchBook.getBookAuthor() + "%';";

		ResultSet rs;
		try
		{
			rs = DatabaseWrapper.ExecuteSelectQuery(query);
			while(rs.next())
			{
				String[] resultRecord = new String[6];
				resultRecord[0] = rs.getString(1);
				resultRecord[1] = rs.getString(2);
				resultRecord[2] = rs.getString(3);
				resultRecord[3] = rs.getString(4);
				resultRecord[4] = rs.getString(5);
				resultRecord[5] = rs.getString(6);
				availableBooks.addRow(resultRecord);
			}
		}
		catch (SQLException e)
		{
			returnMessage = e.getMessage();
			return null;
		}
		return availableBooks;
	}
}
