import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno z informacj¹ o b³êdzie zapisu do pliku.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class FileSaveError implements MessageWindow
{

	/**
	 * Metoda wyœwietlaj¹ca okno o b³êdzie.
	 */
	public void show(Frame frame)
	{
		String message = "Wyst¹pi³ b³¹d podczas zapisu do pliku.";	       		
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
