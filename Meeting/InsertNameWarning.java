import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno informacyjne. Pojawia si� w przypadku pr�by dodania spotkania bez podania jego nazwy.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class InsertNameWarning implements MessageWindow
{

	/**
	 * Metoda wy�wietlaj�ca okno.
	 */
	public void show(Frame frame)
	{
		String message = "Nale�y wprowadzi� nazw� wydarzenia, aby je doda�.";
		JOptionPane.showMessageDialog(frame, message);
	}
}
