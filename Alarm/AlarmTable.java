import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlarmTable
{
	Connection con;
	java.sql.Statement stmt;
	ResultSet rs;
	DataBase db;
	
	AlarmTable() throws ClassNotFoundException, SQLException
	{
	    db = new DataBase();
		db.connectToDataBase();
		con = db.getConnection();
	}
	
	
	public int getId(int id)
	{
		int res;
        try 
        {
        	
        	stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM alarm WHERE id = " + id);
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
	
	public String getDate(int id)
	{
		String res;
        try 
        {
        	
        	stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT data FROM alarm where id = " + id);
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
	
	public void insertDate(int id, String value)
	{
		try 
		{   
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO alarm VALUES(" + id + ", '" + value + "')");
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> getAllIds()
	{
		ArrayList<Integer> ids = new ArrayList<Integer>();
		try
		{
			stmt = con.createStatement();
	        rs = stmt.executeQuery("SELECT id FROM alarm");
	  	    while(rs.next())
	  	    {
	  	       ids.add(rs.getInt("id"));
	  	    }
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return ids;
	}
	
	public void setDate(int id, String value)
	{
		try 
		{   stmt = con.createStatement();
			stmt.executeUpdate("UPDATE alarm SET data = '" + value + "' WHERE id = " + id);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void remove(String value)
	{
		try 
		{   stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM alarm WHERE data = '" + value + "'");
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getDefaultUrl()
	{
		return db.getDefaultUrl();
	}
	
	public String getUrl()
	{
		return db.getUrl();
	}
	
	public void setUrl(String value) throws ClassNotFoundException, SQLException
	{
		db.setUrl(value);
	}
	
}
