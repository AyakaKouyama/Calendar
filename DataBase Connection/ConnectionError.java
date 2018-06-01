import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno z informacj¹ o b³êdzie po³¹czenia z baz¹ danych.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class ConnectionError implements MessageWindow
{

	/**
	 * Metoda wyœwietlaj¹ca okno.
	 */
	@Override
	public void show(Frame frame)
	{
		String message = "Nie uda³o po³¹czyæ siê z baz¹ danych. Sprawdz czy podany URL jest poprawny.\n"
				+ "                   Aplikacja dzia³a w trybie odczytu danych z pliku XML.";
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
