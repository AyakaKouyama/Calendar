import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa ³¹cz¹ca siê bezpoœrednio z baz¹ danych. Pobieraj¹ca dane z bazy z
 * tabeli "alarm" oraz zapisuj¹ca dane do bazy. Posiada metody operuj¹ce na
 * poszczegó³nych polach tabli.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
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
	 * Metoda sprawdzaj¹ca, czy szukane ID wystêpujê w bazie.
	 * 
	 * @param id
	 *            szukane ID
	 * @return zwraca ID w przypadku, gdy szukane ID wystêpuje w bazie, w przeciwnym
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
	 * Metoda zwracaj¹ca datê alarmu o podanym ID.
	 * 
	 * @param id
	 *            ID alarmu
	 * @return zwraca ³añcuch znaków opisuj¹cy datê alarmu
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
	 * Metoda dodaj¹ca nowy alarm do bazy danych.
	 * 
	 * @param id
	 *            ID alarmu
	 * @param value
	 *            ³añcuch zanków opisuj¹cy datê alarmu
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
	 * Metoda zwracaj¹ca listê wszystkich ID alarmów dostêpnych w bazie.
	 * 
	 * @return zwraca listê wszystkich dostêpnych ID alarmów w bazie
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
	 * Metoda aktualizuj¹ca datê alarmu o podanym ID w bazie.
	 * 
	 * @param id
	 *            ID alarmu
	 * @param value
	 *            ³añcuch znaków opisuj¹cy now¹ datê alarmu
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
	 * Metoda usuwaj¹ca alarm opisany przez konkretn¹ datê z bazy danych.
	 * 
	 * @param value
	 *            ³añcuch znaków opisuj¹cy datê do usuniêcia
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
	 * Metoda zwracaj¹ca domyœlny URL bazy danych.
	 * 
	 * @return
	 */
	public String getDefaultUrl()
	{
		return db.getDefaultUrl();
	}

	/**
	 * Meotda zwracaj¹ca obecny URL bazy danych.
	 * 
	 * @return
	 */
	public String getUrl()
	{
		return db.getUrl();
	}

	/**
	 * Metoda ustawiaj¹ca now¹ wartoœæ URL bazy dancyh.
	 * 
	 * @param value
	 *            nowa wartoœæ URL
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void setUrl(String value) throws ClassNotFoundException, SQLException
	{
		db.setUrl(value);
	}

}
