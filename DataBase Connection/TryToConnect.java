import java.awt.Frame;

import javax.swing.JOptionPane;

public class TryToConnect implements MessageWindow
{

	@Override
	public void show(Frame frame)
	{
		String message = "Trwa pr�ba nawi�zania po��czenia z baz� danych. Prosz� czeka�.";
		
		JOptionPane.showMessageDialog(frame, message, "O programie", JOptionPane.INFORMATION_MESSAGE);
		
	}
}
