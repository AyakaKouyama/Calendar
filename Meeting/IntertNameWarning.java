import java.awt.Frame;

import javax.swing.JOptionPane;

public class IntertNameWarning
{
	String message;
	Frame frame;
	int answer;
	public IntertNameWarning(Frame frame)
	{
		this.frame = frame;
		message = "Nale�y wprowadzi� nazw� wydarzenia, aby je doda�.";
		         
	}
	
	public void show()
	{
		JOptionPane.showMessageDialog(frame, message);
	}
}
