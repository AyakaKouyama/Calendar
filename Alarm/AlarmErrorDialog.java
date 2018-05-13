import java.awt.Frame;

import javax.swing.JOptionPane;


public class AlarmErrorDialog 
{
		
	Frame frame;
	
	AlarmErrorDialog(Frame frame)
	{
		this.frame = frame;
	}
	
	public void show()
	{
		JOptionPane.showMessageDialog(frame, "Nie mo¿na ustawiæ alarmu. Data ju¿ minê³a.", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
