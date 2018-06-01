import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Okno ostrze¿enia przed usuniêciem przefiltrowanych spotkañ.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class RemoveWarning 
{
	
	private int answer;

	/**
	 * Meotda wyœwietlaj¹ca okno ostrze¿enia.
	 * @param frame okno rodzic
	 */
	public void show(Frame frame)
	{
		String message = "    Czy na pewno chcesz kontynowaæ? \n "
				+ " Naciœniêcie \"Tak\" spowoduje usuniêcie \n"
				+ " wszystkich elementów widocznych na liœcie.";
		
		answer = JOptionPane.showOptionDialog(frame, message, "Ostrze¿enie!",  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, 
		        null, new Object[] {"Tak", "Nie"}, JOptionPane.YES_OPTION);
	}
	
	/**
	 * Metoda zwracaj¹ca wybran¹ reakcjê przez u¿ytkownika.
	 * @return zwraca true w przypadku zgody na usuniêcie danych, false w przeciwnym wypadku.
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
