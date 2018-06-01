import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno z informacj� o pomy�lnym zapisie do pliku.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class FileSaveSuccess implements MessageWindow
{
	/**
	 * Metoda wy�wietlaj�ca okno.
	 */
	public void show(Frame frame)
	{
		String message = "Pomy�lnie zapisano do pliku.";	       		
		JOptionPane.showMessageDialog(frame, message, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
}
