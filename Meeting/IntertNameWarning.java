import java.awt.Frame;

import javax.swing.JOptionPane;

public class IntertNameWarning implements MessageWindow
{

	public void show(Frame frame)
	{
		String message = "Nale¿y wprowadziæ nazwê wydarzenia, aby je dodaæ.";
		JOptionPane.showMessageDialog(frame, message);
	}
}
