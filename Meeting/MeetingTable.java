import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa ³¹cz¹ca siê bezpoœrednio z baz¹ danych. Pobieraj¹ca dane z bazy z
 * tabeli "meeting" oraz zapisuj¹ca dane do bazy. Posiada metody operuj¹ce na
 * poszczegó³nych polach tabli.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class MeetingTable
{
	private Connection con;
	private java.sql.Statement stmt;
	private ResultSet rs;

	/**
	 * Konstruktor klasy.Uzyskkuje po³¹czenie z baz¹ danych.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MeetingTable() throws ClassNotFoundException, SQLException
	{
		DataBase db = new DataBase();
		db.connectToDataBase();
		con = db.getConnection();
	}

	/**
	 * Metoda sprawdzaj¹ca czy podane ID wystêpuje w bazie.
	 * 
	 * @param id
	 *            szukane ID
	 * @return zwraca ID w przypadku, gdy podane ID wystepuje w bazie, w przeciwynym
	 *         wypadku zwraca -1
	 */
	public int getId(int id)
	{
		int res;
		try
		{

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT id FROM meeting WHERE id = " + id);
			while (rs.next())
			{
				res = rs.getInt("id");
				return res;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * Metoda zwracaj¹ca nazwê spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @return zwraca ³añcuch znaków opisuj¹cy nazwê spotkania w przypadku, gdy
	 *         spotkanie o podanym ID wystêpuje w bazie, w przeciwym wypadku wraca
	 *         null
	 */
	public String getName(int id)
	{
		String res;
		try
		{

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT nazwa FROM meeting where id = " + id);
			while (rs.next())
			{
				res = rs.getString("nazwa");
				return res;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Metoda zwracaj¹ca lokalizacje spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @return zwraca ³añcuch znaków opisuj¹cy lokalizacje spotkania w przypadku,
	 *         gdy spotkanie o podanym ID wystêpuje w bazie, w przeciwym wypadku
	 *         wraca null
	 */
	public String getLocalization(int id)
	{
		String res;
		try
		{

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT lokalizacja FROM meeting where id = " + id);
			while (rs.next())
			{
				res = rs.getString("lokalizacja");
				return res;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Metoda zwracaj¹ca datê spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @return zwraca ³añcuch znaków opisuj¹cy datê spotkania w przypadku, gdy
	 *         spotkanie o podanym ID wystêpuje w bazie, w przeciwym wypadku wraca
	 *         null
	 */
	public String getDate(int id)
	{
		String res;
		try
		{

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT data FROM meeting where id = " + id);
			while (rs.next())
			{
				res = rs.getString("data");
				return res;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Metoda zwracaj¹ca szczegó³y spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @return zwraca ³añcuch znaków opisuj¹cy szczegó³y spotkania w przypadku, gdy
	 *         spotkanie o podanym ID wystêpuje w bazie, w przeciwym wypadku wraca
	 *         null
	 */
	public String getDetails(int id)
	{
		String res;
		try
		{

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT szczegoly FROM meeting where id = " + id);
			while (rs.next())
			{
				res = rs.getString("szczegoly");
				return res;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Metoda wstawiaj¹ca nowe spotkanie do bazy o padanych parametrach.
	 * 
	 * @param id
	 *            ID spotkania
	 * @param name
	 *            nazwa spotkania
	 * @param localization
	 *            lokalizacja spotkania
	 * @param date
	 *            data spotkania
	 * @param details
	 *            szczegó³y spotkania
	 */
	public void insert(int id, String name, String localization, String date, String details)
	{
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO meeting VALUES(" + id + ", '" + name + "', '" + localization + "', '" + date
					+ "', '" + details + "')");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Metoda ustawiaj¹ca now¹ nazwê spotkania dla spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @param value
	 *            nowa nazwa spotkania
	 */
	public void setName(int id, String value)
	{
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE meeting SET nazwa = '" + value + "' WHERE id = " + id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Metoda ustawiaj¹ca now¹ lokalizacjê spotkania dla spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @param value
	 *            nowa lokalizacjê spotkania
	 */
	public void setLocalization(int id, String value)
	{
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE meeting SET lokalizacja = '" + value + "' WHERE id = " + id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Metoda ustawiaj¹ca nowe szczegó³y spotkania dla spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @param value
	 *            nowe szczegó³y spotkania
	 */
	public void setDetails(int id, String value)
	{
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE meeting SET szczegoly = '" + value + "' WHERE id = " + id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Metoda ustawiaj¹ca now¹ datê spotkania dla spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @param value
	 *            nowa data spotkania
	 */
	public void setDate(int id, String value)
	{
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE meeting SET data = '" + value + "' WHERE id = " + id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Metoda usuwaj¹ca spotkanie o podanym ID z bazy.
	 * 
	 * @param id
	 *            ID spotkania
	 */
	public void deleteMeeting(int id)
	{
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM meeting WHERE id = " + id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Metoda zwracaj¹ca listê wszystkich ID wystêpuj¹cych w bazie.
	 * 
	 * @return zwraca listê wszystkich ID wystêpuj¹cych w bazie
	 */
	public ArrayList<Integer> getAllIds()
	{
		ArrayList<Integer> ids = new ArrayList<Integer>();
		try
		{
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT id FROM meeting");
			while (rs.next())
			{
				ids.add(rs.getInt("id"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return ids;
	}
}
