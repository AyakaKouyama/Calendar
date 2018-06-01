import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno "o programie". Zawiera informacje o programie oraz autorach programu".
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 */
public class About implements MessageWindow
{

	/**
	 * Metoda wy�wietlaj�ca okno.
	 */
	public void show(Frame frame)
	{
		String message = "                   Kalendarz     \n" + "Programowanie Komponentowe 2018\n"
				+ "Sylwia Mieszkowska, Anna Ciep�ucha" ;

		JOptionPane.showMessageDialog(frame, message, "O programie", JOptionPane.INFORMATION_MESSAGE);
	}

}
