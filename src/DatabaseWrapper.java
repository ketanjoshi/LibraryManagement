import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseWrapper
{

	private static Connection connection = getConnection();

	@SuppressWarnings("finally")
	public synchronized static Connection getConnection()
	{
		try {
			if(connection == null)
			{
				connection = DriverManager.getConnection(Globals.DRIVER_PATH, Globals.USER, Globals.PASSWORD);
				connection.createStatement().execute("use library;");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			return connection;
		}
	}

	private DatabaseWrapper()
	{
	}

	public static ResultSet ExecuteSelectQuery(String query) throws SQLException
	{
		return connection.createStatement().executeQuery(query);
	}

	public static void ExecuteUpdateQuery(String query) throws SQLException
	{
		connection.prepareStatement(query).execute();
	}

	public static void attachShutDownHook()
	{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				try { connection.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		});
	}
	
	public static boolean validateUser(String username, String password)
	{
		String query = "select * from users where username = ? and password = ?;";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			return ps.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}