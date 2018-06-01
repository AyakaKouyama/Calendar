import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Klasa posiadaj�ca metody s�u��ce do ustanowienia po��czenia z baz� danych
 * oraz modyfikacji adresu URL bazy.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
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
	 * Metoda uzyskuj�ca po��czenie z baz� danych o podanym URL.
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
	 * Metoda zwracaj�ca obiekt Connection - po��czenie z baz� danych.
	 * 
	 * @return zwraca obiekt reprezentuj�cy po��czenie z baz�.
	 */
	public Connection getConnection()
	{
		return con;
	}

	/**
	 * Metoda ustawiaj�ca warto�� URL bazy danych.
	 * 
	 * @param value
	 *            nowa warto�� URL
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
	 * Metoda zwracaj�ca domy�lny URL bazy.
	 * @return zwraca domy�lny URL bazy
	 */
	public String getDefaultUrl()
	{
		return defaultUrl;
	}

	/**
	 * Metoda zwracaj�ca obecny URL bazy.
	 * @return zwraca obecyn URL bazy
	 */
	public String getUrl()
	{
		return connectionUrl;
	}

}
