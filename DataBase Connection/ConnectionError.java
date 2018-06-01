import java.awt.Frame;

import javax.swing.JOptionPane;

public class ConnectionError implements MessageWindow
{

	@Override
	public void show(Frame frame)
	{
		String message = "Nie uda�o po��czy� si� z baz� danych. Sprawdz czy podany URL jest poprawny.\n"
				+ "                   Aplikacja dzia�a w trybie odczytu danych z pliku XML.";
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
