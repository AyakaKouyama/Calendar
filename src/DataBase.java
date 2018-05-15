import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DataBase 
{

	java.sql.Statement stmt;
	ResultSet rs;
	Connection con = null;
	
	public void connectToDataBase()
	{
	   String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
				              "databaseName=meetings;integratedSecurity=true;";

	  try 
	  {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	       	con = DriverManager.getConnection(connectionUrl);       
	  }
	  catch (Exception e) 
	  {
	   	e.printStackTrace();
	  }
	  
	}
	
	public Connection getConnection()
	{
		return con;
	}
	
	
}
