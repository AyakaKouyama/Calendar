import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno z informacją o pomyślnym zapisie do pliku.
 * @author Sylwia Mieszkowska
 * @author Anna Ciepłucha
 *
 */
public class FileSaveSuccess implements MessageWindow
{
	/**
	 * Metoda wyświetlająca okno.
	 */
	public void show(Frame frame)
	{
		String message = "Pomyślnie zapisano do pliku.";	       		
		JOptionPane.showMessageDialog(frame, message, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
}
