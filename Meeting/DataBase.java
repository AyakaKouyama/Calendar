import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public int getId(int id)
	{
		int res;
        try 
        {
        	
        	stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM meeting WHERE id = " + id);
  	       	while(rs.next())
  	       	{
  	       		res = rs.getInt("id");
  	       		return res;
  	       	}
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
        
        return -1;
	}
	
	public String getName(int id)
	{
		String res;
        try 
        {
        	
        	stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT nazwa FROM meeting where id = " + id);
  	       	while(rs.next())
  	       	{
  	       		res = rs.getString("nazwa");
  	       		return res;
  	       	}
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
        
        return null;
	}
	
	public String getLocalization(int id)
	{
		String res;
        try 
        {
        	
        	stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT lokalizacja FROM meeting where id = " + id);
  	       	while(rs.next())
  	       	{
  	       		res = rs.getString("lokalizacja");
  	       		return res;
  	       	}
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
        
        return null;
	}
	
	public String getDate(int id)
	{
		String res;
        try 
        {
        	
        	stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT data FROM meeting where id = " + id);
  	       	while(rs.next())
  	       	{
  	       		res = rs.getString("data");
  	       		return res;
  	       	}
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
        
        return null;
	}
	
	public String getDetails(int id)
	{
		String res;
        try 
        {
        	
        	stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT szczegoly FROM meeting where id = " + id);
  	       	while(rs.next())
  	       	{
  	       		res = rs.getString("szczegoly");
  	       		return res;
  	       	}
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
        
        return null;
	}
	
	

	
	public void insert(int id, String name, String localization, String date, String details)
	{
		try 
		{   
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO meeting VALUES(" + id + ", '" + name + "', '" + localization + "', '" + date + "', '" + details + "')");
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public void setName(int id, String value)
	{
		try 
		{   stmt = con.createStatement();
			stmt.executeUpdate("UPDATE meeting SET nazwa = '" + value + "' WHERE id = " + id);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setLocalization(int id, String value)
	{
		try 
		{   stmt = con.createStatement();
			stmt.executeUpdate("UPDATE meeting SET lokalizacja = '" + value + "' WHERE id = " + id);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setDetails(int id, String value)
	{
		try 
		{   stmt = con.createStatement();
			stmt.executeUpdate("UPDATE meeting SET szczegoly = '" + value + "' WHERE id = " + id);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setDate(int id, String value)
	{
		try 
		{   stmt = con.createStatement();
			stmt.executeUpdate("UPDATE meeting SET data = '" + value + "' WHERE id = " + id);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void deleteMeeting(int id)
	{
		try 
		{   stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM meeting WHERE id = " + id);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
}
