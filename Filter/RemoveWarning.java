import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno ostrze�enia przed usuni�ciem przefiltrowanych spotka�.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class RemoveWarning 
{
	
	private int answer;

	/**
	 * Meotda wy�wietlaj�ca okno ostrze�enia.
	 * @param frame okno rodzic
	 */
	public void show(Frame frame)
	{
		String message = "    Czy na pewno chcesz kontynowa�? \n "
				+ " Naci�ni�cie \"Tak\" spowoduje usuni�cie \n"
				+ " wszystkich element�w widocznych na li�cie.";
		
		answer = JOptionPane.showOptionDialog(frame, message, "Ostrze�enie!",  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, 
		        null, new Object[] {"Tak", "Nie"}, JOptionPane.YES_OPTION);
	}
	
	/**
	 * Metoda zwracaj�ca wybran� reakcj� przez u�ytkownika.
	 * @return zwraca true w przypadku zgody na usuni�cie danych, false w przeciwnym wypadku.
	 */
	public boolean getAnswer()
	{
		if(answer == JOptionPane.YES_OPTION)
		{
			return true;
		}
		if(answer == JOptionPane.NO_OPTION)
		{
			return false;
		}
		if(answer == JOptionPane.CLOSED_OPTION)
		{
			return false;
		}
		return false;
	}
}
