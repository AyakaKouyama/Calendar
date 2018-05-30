import java.awt.Frame;

import javax.swing.JOptionPane;

public class FileSaveError implements MessageWindow
{

	public void show(Frame frame)
	{
		String message = "Wyst¹pi³ b³¹d podczas zapisu do pliku.";	       		
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
