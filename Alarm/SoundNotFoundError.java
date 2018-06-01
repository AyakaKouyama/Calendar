import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno pojawiaj¹ce siê w przypadku, gdy nie odnaleziono pliku audio w formacie .wav w pliku /bin.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class SoundNotFoundError implements MessageWindow
{

	/**
	 * Metoda wyœwietlaj¹ca okno z informacj¹ o b³êdzie.
	 */
	@Override
	public void show(Frame frame)
	{
		String message = "Nie znaleziono plików audio. Pliki z rozszerzeniem .wav nale¿y umieœciæ w katalogu /bin.";

		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);	
	}
	
}
