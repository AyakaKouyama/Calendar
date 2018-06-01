import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno z informacj� o b��dzie. Wyst�puje w przypadku, gdy data alarmu, kt�r� chce ustawi� u�ytkownik ju� min�a. 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class AlarmErrorDialog implements MessageWindow
{
		
	/**
	 * Metoda wy�wietlaj�ca okno.
	 */
	public void show(Frame frame)
	{
		JOptionPane.showMessageDialog(frame, "Nie mo�na ustawi� alarmu. Data ju� min�a.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
