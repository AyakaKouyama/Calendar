import java.awt.Frame;

import javax.swing.JOptionPane;

public class SoundNotFoundError implements MessageWindow
{

	@Override
	public void show(Frame frame)
	{
		String message = "Nie znaleziono plików audio. Pliki z rozszerzeniem .wav nale¿y umieœciæ w katalogu /bin.";

		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);	
	}
	
}
