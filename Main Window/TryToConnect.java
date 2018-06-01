import java.awt.Frame;

import javax.swing.JOptionPane;

public class TryToConnect implements MessageWindow
{

	@Override
	public void show(Frame frame)
	{
		String message = "Trwa próba nawi¹zania po³¹czenia z baz¹ danych. Proszê czekaæ.";
		
		JOptionPane.showMessageDialog(frame, message, "O programie", JOptionPane.INFORMATION_MESSAGE);
		
	}
}
