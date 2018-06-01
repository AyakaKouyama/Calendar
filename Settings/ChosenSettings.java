
/**
 * Klasa reprezentuj�ca wybrane ustawienia aplikacji przez u�ytkownika. Wykorzystywana do p�zniejszej serializacji do pliku.
 * @author Ayaka
 *
 */
public class ChosenSettings
{
	public String sound;
	public String theme;
	public String mode;
	public String url;


	/**
	 * Ustawia nazw� wybranego pliku audio
	 * @param value nazwa pliku (bez rozszerzenia)
	 */
	public void setSound(String value)
	{
		sound = value;
	}

	
	/**
	 * Ustawia nazw� wybranego motywu
	 * @param value nazwa motywu
	 */
	public void setTheme(String value)
	{
		theme = value;
	}

	/**
	 * Ustawia nazw� wybranego trybu
	 * @param value nazwa trybu
	 */
	public void setMode(String value)
	{
		mode = value;
	}

	/**
	 * Ustawia nazw� wybranego URL bazy
	 * @param value nazwa URL bazy
	 */
	public void setUrl(String value)
	{
		url = value;
	}

	/**
	 * Metoda zwracaj�ca nazw� wybranego dzwi�ku alarmu.
	 * @param value nazwa pliku dzwi�ku alarmu
	 */
	public String getSound()
	{
		return sound;
	}

	/**
	 * Metoda zwracaj�ca nazw� wybranego motywu.
	 * @param value nazwa wybranego motywy
	 */
	public String getTheme()
	{
		return theme;
	}

	/**
	 * Metoda zwracaj�ca nazw� wybranego trybu.
	 * @param value nazwa wybranego trybu.
	 */
	public String getMode()
	{
		return mode;
	}

	/**
	 * Metoda zwracaj�ca nazw� wybranego URL bazy.
	 * @param value nazwa wybranego URL bazy.
	 */
	public String getUrl()
	{
		return url;
	}
}
