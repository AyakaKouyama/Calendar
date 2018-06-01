import java.awt.Color;

/**
 * Klasa s�u��ca do wyboru motywu kalendarza.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class Theme
{
	private Color[] colors;

	/**
	 * Konstruktor klasy.
	 */
	Theme()
	{
		colors = new Color[4];
	}

	/**
	 * Metoda zwracaj�ca tablic� z kolorami wybranego motywu.
	 * @return tablica z kolorami wybranego motywu
	 */
	public Color[] getTheme()
	{
		return colors;
	}

	/**
	 * Metoda ustawiaj�ca kolory w tablicy w zale�no�ci od wyboru u�ytkownika.
	 * @param choice nazwa wybranego motywu
	 */
	public void select(String choice)
	{

		if (choice == "Niebieski")
		{
			colors[0] = new Color(43, 82, 96);
			colors[1] = new Color(43, 82, 96);
			colors[2] = new Color(145, 175, 186);
			colors[3] = new Color(218, 226, 232);
		} else if (choice == "R�owy")
		{
			colors[0] = new Color(153, 76, 130);
			colors[1] = new Color(153, 76, 130);
			colors[2] = new Color(206, 150, 191);
			colors[3] = new Color(226, 197, 218);
		} else if (choice == "Zielony")
		{
			colors[0] = new Color(97, 163, 32);
			colors[1] = new Color(97, 163, 32);
			colors[2] = new Color(146, 216, 78);
			colors[3] = new Color(226, 242, 210);
		}
	}
}
