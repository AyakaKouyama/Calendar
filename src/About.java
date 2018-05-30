import java.awt.Frame;

import javax.swing.JOptionPane;

public class About implements MessageWindow
{

	public void show(Frame frame)
	{
		String message = "                 Organizer     \n" +
				         "Programowanie Komponentowe 2018\n" +
				         "Sylwia Mieszkowska, Anna Ciep³ucha";
				         
						
		JOptionPane.showMessageDialog(frame, message, "O programie", JOptionPane.INFORMATION_MESSAGE);
	}
	
	

}
