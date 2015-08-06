
public class BookLoanData
{

	private String bookId;
	private String card;
	private String borrower;

	public BookLoanData(String BookId, String Card, String Borrower)
	{
		bookId = BookId;
		card = Card;
		borrower = Borrower;
	}

	public BookLoanData()
	{
	}

	public String getBookId()
	{
		return bookId;
	}

	public String getBorrower()
	{
		return borrower;
	}

	public String getCardNumber()
	{
		return card;
	}

}
