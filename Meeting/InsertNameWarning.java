import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno informacyjne. Pojawia siê w przypadku próby dodania spotkania bez podania jego nazwy.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class InsertNameWarning implements MessageWindow
{

	/**
	 * Metoda wyœwietlaj¹ca okno.
	 */
	public void show(Frame frame)
	{
		String message = "Nale¿y wprowadziæ nazwê wydarzenia, aby je dodaæ.";
		JOptionPane.showMessageDialog(frame, message);
	}
}
