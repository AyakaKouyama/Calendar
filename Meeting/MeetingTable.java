import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa ��cz�ca si� bezpo�rednio z baz� danych. Pobieraj�ca dane z bazy z
 * tabeli "meeting" oraz zapisuj�ca dane do bazy. Posiada metody operuj�ce na
 * poszczeg�nych polach tabli.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class MeetingTable
{
	private Connection con;
	private java.sql.Statement stmt;
	private ResultSet rs;

	/**
	 * Konstruktor klasy.Uzyskkuje po��czenie z baz� danych.
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
	 * Metoda sprawdzaj�ca czy podane ID wyst�puje w bazie.
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
	 * Metoda zwracaj�ca nazw� spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @return zwraca �a�cuch znak�w opisuj�cy nazw� spotkania w przypadku, gdy
	 *         spotkanie o podanym ID wyst�puje w bazie, w przeciwym wypadku wraca
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
	 * Metoda zwracaj�ca lokalizacje spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @return zwraca �a�cuch znak�w opisuj�cy lokalizacje spotkania w przypadku,
	 *         gdy spotkanie o podanym ID wyst�puje w bazie, w przeciwym wypadku
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
	 * Metoda zwracaj�ca dat� spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @return zwraca �a�cuch znak�w opisuj�cy dat� spotkania w przypadku, gdy
	 *         spotkanie o podanym ID wyst�puje w bazie, w przeciwym wypadku wraca
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
	 * Metoda zwracaj�ca szczeg�y spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @return zwraca �a�cuch znak�w opisuj�cy szczeg�y spotkania w przypadku, gdy
	 *         spotkanie o podanym ID wyst�puje w bazie, w przeciwym wypadku wraca
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
	 * Metoda wstawiaj�ca nowe spotkanie do bazy o padanych parametrach.
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
	 *            szczeg�y spotkania
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
	 * Metoda ustawiaj�ca now� nazw� spotkania dla spotkania o podanym ID.
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
	 * Metoda ustawiaj�ca now� lokalizacj� spotkania dla spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @param value
	 *            nowa lokalizacj� spotkania
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
	 * Metoda ustawiaj�ca nowe szczeg�y spotkania dla spotkania o podanym ID.
	 * 
	 * @param id
	 *            ID spotkania
	 * @param value
	 *            nowe szczeg�y spotkania
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
	 * Metoda ustawiaj�ca now� dat� spotkania dla spotkania o podanym ID.
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
	 * Metoda usuwaj�ca spotkanie o podanym ID z bazy.
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
	 * Metoda zwracaj�ca list� wszystkich ID wyst�puj�cych w bazie.
	 * 
	 * @return zwraca list� wszystkich ID wyst�puj�cych w bazie
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
