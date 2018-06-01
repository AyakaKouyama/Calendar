import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase
{
	private Connection con = null;
	private String connectionUrl;
	private String defaultUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=meetings;integratedSecurity=true;";

	DataBase()
	{
		connectionUrl = defaultUrl;
	}

	public void connectToDataBase() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		con = DriverManager.getConnection(connectionUrl);

	}

	public Connection getConnection()
	{
		return con;
	}

	public void setUrl(String value) throws ClassNotFoundException, SQLException
	{
		connectionUrl = value;
		connectToDataBase();
	}

	public String getDefaultUrl()
	{
		return defaultUrl;
	}

	public String getUrl()
	{
		return connectionUrl;
	}

}
