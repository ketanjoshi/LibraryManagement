
public class SearchBookQueryParams
{

	private String bookId;
	private String title;
	private String author;

	public SearchBookQueryParams(String BookId, String Title, String Author)
	{
		bookId = BookId;
		title = Title;
		author = Author;
	}

	public SearchBookQueryParams()
	{
	}

	public String getBookId()
	{
		return bookId;
	}

	public String getBookTitle()
	{
		return title;
	}

	public String getBookAuthor()
	{
		return author;
	}
}
