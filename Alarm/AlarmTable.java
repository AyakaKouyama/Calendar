import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa ��cz�ca si� bezpo�rednio z baz� danych. Pobieraj�ca dane z bazy z
 * tabeli "alarm" oraz zapisuj�ca dane do bazy. Posiada metody operuj�ce na
 * poszczeg�nych polach tabli.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class AlarmTable
{
	private Connection con;
	private java.sql.Statement stmt;
	private ResultSet rs;
	private DataBase db;

	/**
	 * Konstruktor klasy.
	 * 
	 * @throws ClassNotFoundException
	 *             <a href=
	 *             "https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html">https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html</a>
	 * @throws SQLException
	 *             <a href=
	 *             "https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html">https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html</a>
	 */
	public AlarmTable() throws ClassNotFoundException, SQLException
	{
		db = new DataBase();
		db.connectToDataBase();
		con = db.getConnection();
	}

	/**
	 * Metoda sprawdzaj�ca, czy szukane ID wyst�puj� w bazie.
	 * 
	 * @param id
	 *            szukane ID
	 * @return zwraca ID w przypadku, gdy szukane ID wyst�puje w bazie, w przeciwnym
	 *         wypadku zwraca -1
	 */
	public int getId(int id)
	{
		int res;
		try
		{

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT id FROM alarm WHERE id = " + id);
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
	 * Metoda zwracaj�ca dat� alarmu o podanym ID.
	 * 
	 * @param id
	 *            ID alarmu
	 * @return zwraca �a�cuch znak�w opisuj�cy dat� alarmu
	 */
	public String getDate(int id)
	{
		String res;
		try
		{

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT data FROM alarm where id = " + id);
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
	 * Metoda dodaj�ca nowy alarm do bazy danych.
	 * 
	 * @param id
	 *            ID alarmu
	 * @param value
	 *            �a�cuch zank�w opisuj�cy dat� alarmu
	 */
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

	/**
	 * Metoda zwracaj�ca list� wszystkich ID alarm�w dost�pnych w bazie.
	 * 
	 * @return zwraca list� wszystkich dost�pnych ID alarm�w w bazie
	 */
	public ArrayList<Integer> getAllIds()
	{
		ArrayList<Integer> ids = new ArrayList<Integer>();
		try
		{
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT id FROM alarm");
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

	/**
	 * Metoda aktualizuj�ca dat� alarmu o podanym ID w bazie.
	 * 
	 * @param id
	 *            ID alarmu
	 * @param value
	 *            �a�cuch znak�w opisuj�cy now� dat� alarmu
	 */
	public void setDate(int id, String value)
	{
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE alarm SET data = '" + value + "' WHERE id = " + id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Metoda usuwaj�ca alarm opisany przez konkretn� dat� z bazy danych.
	 * 
	 * @param value
	 *            �a�cuch znak�w opisuj�cy dat� do usuni�cia
	 */
	public void remove(String value)
	{
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM alarm WHERE data = '" + value + "'");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Metoda zwracaj�ca domy�lny URL bazy danych.
	 * 
	 * @return
	 */
	public String getDefaultUrl()
	{
		return db.getDefaultUrl();
	}

	/**
	 * Meotda zwracaj�ca obecny URL bazy danych.
	 * 
	 * @return
	 */
	public String getUrl()
	{
		return db.getUrl();
	}

	/**
	 * Metoda ustawiaj�ca now� warto�� URL bazy dancyh.
	 * 
	 * @param value
	 *            nowa warto�� URL
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void setUrl(String value) throws ClassNotFoundException, SQLException
	{
		db.setUrl(value);
	}

}
