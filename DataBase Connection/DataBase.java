import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Klasa posiadaj¹ca metody s³u¿¹ce do ustanowienia po³¹czenia z baz¹ danych
 * oraz modyfikacji adresu URL bazy.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class DataBase
{
	private Connection con = null;
	private String connectionUrl;
	private String defaultUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=meetings;integratedSecurity=true;";

	/**
	 * Konstruktor klasy.
	 */
	public DataBase()
	{
		connectionUrl = defaultUrl;
	}

	/**
	 * Metoda uzyskuj¹ca po³¹czenie z baz¹ danych o podanym URL.
	 * 
	 * @throws ClassNotFoundException
	 *             <a href=
	 *             "https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html">https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html</a>
	 * @throws SQLException
	 *             <a href=
	 *             "https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html">https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html</a>
	 */
	public void connectToDataBase() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		con = DriverManager.getConnection(connectionUrl);
	}

	/**
	 * Metoda zwracaj¹ca obiekt Connection - po³¹czenie z baz¹ danych.
	 * 
	 * @return zwraca obiekt reprezentuj¹cy po³¹czenie z baz¹.
	 */
	public Connection getConnection()
	{
		return con;
	}

	/**
	 * Metoda ustawiaj¹ca wartoœæ URL bazy danych.
	 * 
	 * @param value
	 *            nowa wartoœæ URL
	 * @throws ClassNotFoundException
	 *             <a href=
	 *             "https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html">https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html</a>
	 * @throws SQLException
	 *             <a href=
	 *             "https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html">https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html</a>
	 */
	public void setUrl(String value) throws ClassNotFoundException, SQLException
	{
		connectionUrl = value;
		connectToDataBase();
	}

	/**
	 * Metoda zwracaj¹ca domyœlny URL bazy.
	 * @return zwraca domyœlny URL bazy
	 */
	public String getDefaultUrl()
	{
		return defaultUrl;
	}

	/**
	 * Metoda zwracaj¹ca obecny URL bazy.
	 * @return zwraca obecyn URL bazy
	 */
	public String getUrl()
	{
		return connectionUrl;
	}

}
