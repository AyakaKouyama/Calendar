import java.awt.Frame;

import javax.swing.JOptionPane;


public class AlarmErrorDialog implements MessageWindow
{
		
	public void show(Frame frame)
	{
		JOptionPane.showMessageDialog(frame, "Nie mo¿na ustawiæ alarmu. Data ju¿ minê³a.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
