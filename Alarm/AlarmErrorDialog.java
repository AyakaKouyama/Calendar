import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno z informacj¹ o b³êdzie. Wystêpuje w przypadku, gdy data alarmu, któr¹ chce ustawiæ u¿ytkownik ju¿ minê³a. 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class AlarmErrorDialog implements MessageWindow
{
		
	/**
	 * Metoda wyœwietlaj¹ca okno.
	 */
	public void show(Frame frame)
	{
		JOptionPane.showMessageDialog(frame, "Nie mo¿na ustawiæ alarmu. Data ju¿ minê³a.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
