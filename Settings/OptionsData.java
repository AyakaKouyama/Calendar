import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OptionsData
{
	Connection con;
	java.sql.Statement stmt;
	ResultSet rs;

	OptionsData()
	{
		DataBase db = new DataBase();
		db.connectToDataBase();
		con = db.getConnection();
	}

	public String getSound(int id)
	{
		String res;
		try
		{

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT dzwiek FROM options WHERE id = " + id);
			while (rs.next())
			{
				res = rs.getString("dzwiek");
				return res;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public String getTheme(int id)
	{
		String res;
		try
		{

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT motyw FROM options WHERE id = " + id);
			while (rs.next())
			{
				res = rs.getString("motyw");
				return res;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public void setSound(int id, String value)
	{
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE options SET dzwiek = '" + value + "' WHERE id = " + id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void setTheme(int id, String value)
	{
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE options SET motyw = '" + value + "' WHERE id = " + id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}