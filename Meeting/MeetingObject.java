
/**
 * Klasa reprezentuj¹ca spotkanie. Klasa do pózniejszej serializacji. W
 * obiektach tej klasy przechowywane s¹ informacje na temat spotkañ.
 * 
 * @author Sylwia Mieszkowska 
 * @author Anna Ciep³ucha
 *
 */
public class MeetingObject
{
	public String name;
	public String localization;
	public String time;
	public String date;
	public String details;

	/**
	 * Metoda do ustawienia wartoœci pola name (nazwa spotkania).
	 * 
	 * @param value
	 *            ³añcuch reprezentuj¹cy nazwê spotkania
	 */
	public void setName(String value)
	{
		name = value;
	}

	/**
	 * Metoda do ustawienia wartoœci pola localization(lokalizacja spotkania).
	 * 
	 * @param value
	 *            ³añcuch reprezentuj¹cy lokalizacjê
	 */
	public void setLocalization(String value)
	{
		localization = value;
	}

	/**
	 * Metoda do ustawienia wartoœci pola time (godzina spotkania).
	 * 
	 * @param value
	 *            ³añcuch reprezentuj¹cy nazwê spotkania
	 */
	public void setTime(String value)
	{
		time = value;
	}

	/**
	 * Metoda do ustawienia wartoœci pola date (data spotkania).
	 * 
	 * @param value
	 *            ³añcuch reprezentuj¹cy datê spotkania
	 */
	public void setDtae(String value)
	{
		date = value;
	}

	/**
	 * Metoda do ustawienia wartoœci pola details (szczegó³y spotkania).
	 * 
	 * @param value
	 *            ³añcuch reprezentuj¹cy szczegó³y spotkania
	 */
	public void setDetails(String value)
	{
		details = value;
	}

	/**
	 * Metoda zwracaj¹ca nazwê spotkania.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Metoda zwracaj¹ca lokalizacjê spotkania.
	 */
	public String getLocalization()
	{
		return localization;
	}

	/**
	 * Metoda zwracaj¹ca godzinê spotkania.
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * Metoda zwracaj¹ca datê spotkania.
	 */
	public String getDate()
	{
		return date;
	}

	/**
	 * Metoda zwracaj¹ca szczegó³y spotkania.
	 */
	public String getDetails()
	{
		return details;
	}
}
