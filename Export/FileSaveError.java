import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno z informacj� o b��dzie zapisu do pliku.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class FileSaveError implements MessageWindow
{

	/**
	 * Metoda wy�wietlaj�ca okno o b��dzie.
	 */
	public void show(Frame frame)
	{
		String message = "Wyst�pi� b��d podczas zapisu do pliku.";	       		
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
