import java.awt.Frame;

import javax.swing.JOptionPane;

public class FileSaveSuccess implements MessageWindow
{
	public void show(Frame frame)
	{
		String message = "Pomyœlnie zapisano do pliku.";	       		
		JOptionPane.showMessageDialog(frame, message, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
}
