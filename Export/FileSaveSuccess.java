import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno z informacj¹ o pomyœlnym zapisie do pliku.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class FileSaveSuccess implements MessageWindow
{
	/**
	 * Metoda wyœwietlaj¹ca okno.
	 */
	public void show(Frame frame)
	{
		String message = "Pomyœlnie zapisano do pliku.";	       		
		JOptionPane.showMessageDialog(frame, message, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
}
