import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno "o programie". Zawiera informacje o programie oraz autorach programu".
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 */
public class About implements MessageWindow
{

	/**
	 * Metoda wyœwietlaj¹ca okno.
	 */
	public void show(Frame frame)
	{
		String message = "                   Kalendarz     \n" + "Programowanie Komponentowe 2018\n"
				+ "Sylwia Mieszkowska, Anna Ciep³ucha" ;

		JOptionPane.showMessageDialog(frame, message, "O programie", JOptionPane.INFORMATION_MESSAGE);
	}

}
