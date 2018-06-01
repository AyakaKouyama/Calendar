import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno pojawiaj�ce si� w przypadku, gdy nie odnaleziono pliku audio w formacie .wav w pliku /bin.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class SoundNotFoundError implements MessageWindow
{

	/**
	 * Metoda wy�wietlaj�ca okno z informacj� o b��dzie.
	 */
	@Override
	public void show(Frame frame)
	{
		String message = "Nie znaleziono plik�w audio. Pliki z rozszerzeniem .wav nale�y umie�ci� w katalogu /bin.";

		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);	
	}
	
}
