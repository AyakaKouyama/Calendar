import java.awt.Frame;

import javax.swing.JOptionPane;

public class RemoveWarning 
{
	
	int answer;

	public void show(Frame frame)
	{
		String message = "    Czy na pewno chcesz kontynowa�? \n "
				+ " Naci�ni�cie \"Tak\" spowoduje usuni�cie \n"
				+ " wszystkich element�w widocznych na li�cie.";
		
		answer = JOptionPane.showOptionDialog(frame, message, "Ostrze�enie!",  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, 
		        null, new Object[] {"Tak", "Nie"}, JOptionPane.YES_OPTION);
	}
	
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
