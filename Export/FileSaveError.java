import java.awt.Frame;

import javax.swing.JOptionPane;

public class FileSaveError implements MessageWindow
{

	public void show(Frame frame)
	{
		String message = "Wyst�pi� b��d podczas zapisu do pliku.";	       		
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
