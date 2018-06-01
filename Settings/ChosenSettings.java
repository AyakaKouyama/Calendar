
/**
 * Klasa reprezentuj¹ca wybrane ustawienia aplikacji przez u¿ytkownika. Wykorzystywana do pózniejszej serializacji do pliku.
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
	 * Ustawia nazwê wybranego pliku audio
	 * @param value nazwa pliku (bez rozszerzenia)
	 */
	public void setSound(String value)
	{
		sound = value;
	}

	
	/**
	 * Ustawia nazwê wybranego motywu
	 * @param value nazwa motywu
	 */
	public void setTheme(String value)
	{
		theme = value;
	}

	/**
	 * Ustawia nazwê wybranego trybu
	 * @param value nazwa trybu
	 */
	public void setMode(String value)
	{
		mode = value;
	}

	/**
	 * Ustawia nazwê wybranego URL bazy
	 * @param value nazwa URL bazy
	 */
	public void setUrl(String value)
	{
		url = value;
	}

	/**
	 * Metoda zwracaj¹ca nazwê wybranego dzwiêku alarmu.
	 * @param value nazwa pliku dzwiêku alarmu
	 */
	public String getSound()
	{
		return sound;
	}

	/**
	 * Metoda zwracaj¹ca nazwê wybranego motywu.
	 * @param value nazwa wybranego motywy
	 */
	public String getTheme()
	{
		return theme;
	}

	/**
	 * Metoda zwracaj¹ca nazwê wybranego trybu.
	 * @param value nazwa wybranego trybu.
	 */
	public String getMode()
	{
		return mode;
	}

	/**
	 * Metoda zwracaj¹ca nazwê wybranego URL bazy.
	 * @param value nazwa wybranego URL bazy.
	 */
	public String getUrl()
	{
		return url;
	}
}
