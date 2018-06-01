import java.awt.Frame;

import javax.swing.JOptionPane;

public class SoundNotFoundError implements MessageWindow
{

	@Override
	public void show(Frame frame)
	{
		String message = "Nie znaleziono plik�w audio. Pliki z rozszerzeniem .wav nale�y umie�ci� w katalogu /bin.";

		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);	
	}
	
}
