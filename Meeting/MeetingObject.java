
/**
 * Klasa reprezentuj�ca spotkanie. Klasa do p�zniejszej serializacji. W
 * obiektach tej klasy przechowywane s� informacje na temat spotka�.
 * 
 * @author Sylwia Mieszkowska 
 * @author Anna Ciep�ucha
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
	 * Metoda do ustawienia warto�ci pola name (nazwa spotkania).
	 * 
	 * @param value
	 *            �a�cuch reprezentuj�cy nazw� spotkania
	 */
	public void setName(String value)
	{
		name = value;
	}

	/**
	 * Metoda do ustawienia warto�ci pola localization(lokalizacja spotkania).
	 * 
	 * @param value
	 *            �a�cuch reprezentuj�cy lokalizacj�
	 */
	public void setLocalization(String value)
	{
		localization = value;
	}

	/**
	 * Metoda do ustawienia warto�ci pola time (godzina spotkania).
	 * 
	 * @param value
	 *            �a�cuch reprezentuj�cy nazw� spotkania
	 */
	public void setTime(String value)
	{
		time = value;
	}

	/**
	 * Metoda do ustawienia warto�ci pola date (data spotkania).
	 * 
	 * @param value
	 *            �a�cuch reprezentuj�cy dat� spotkania
	 */
	public void setDtae(String value)
	{
		date = value;
	}

	/**
	 * Metoda do ustawienia warto�ci pola details (szczeg�y spotkania).
	 * 
	 * @param value
	 *            �a�cuch reprezentuj�cy szczeg�y spotkania
	 */
	public void setDetails(String value)
	{
		details = value;
	}

	/**
	 * Metoda zwracaj�ca nazw� spotkania.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Metoda zwracaj�ca lokalizacj� spotkania.
	 */
	public String getLocalization()
	{
		return localization;
	}

	/**
	 * Metoda zwracaj�ca godzin� spotkania.
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * Metoda zwracaj�ca dat� spotkania.
	 */
	public String getDate()
	{
		return date;
	}

	/**
	 * Metoda zwracaj�ca szczeg�y spotkania.
	 */
	public String getDetails()
	{
		return details;
	}
}
