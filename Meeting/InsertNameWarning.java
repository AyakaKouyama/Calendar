import java.awt.Frame;

import javax.swing.JOptionPane;

public class InsertNameWarning implements MessageWindow
{

	public void show(Frame frame)
	{
		String message = "Nale�y wprowadzi� nazw� wydarzenia, aby je doda�.";
		JOptionPane.showMessageDialog(frame, message);
	}
}