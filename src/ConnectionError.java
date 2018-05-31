import java.awt.Frame;

import javax.swing.JOptionPane;

public class ConnectionError implements MessageWindow
{

	@Override
	public void show(Frame frame)
	{
		String message = "Nie uda�o po��czy� si� z baz� danych. Sprawdz czy podany URL jest poprawny.";

		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
		
	}

}
