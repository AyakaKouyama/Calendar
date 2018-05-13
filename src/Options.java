import java.awt.Choice;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Options implements ItemListener
{
	JFrame frame;
	JLabel alarmSound;
	Choice choice;
	String sChoice;
	
	Options(Window window)
	{
		frame = new JFrame();
		frame.setTitle("Alarm Clock");
		frame.setResizable(false);
		frame.setLayout(null);
		frame.pack();
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		
		choice = new Choice();
		choice.setBounds(20, 40, 80, 30);
		choice.addItemListener(this);
	
		for(int i = 0; i<5; i++)
		{
			choice.add("sound" + (i + 1));
		}
		
		sChoice = choice.getItem(0);
		frame.add(choice);
		
		alarmSound = new JLabel("Dzwiêk alarmu");
		alarmSound.setBounds(20, 10, 100, 30);
		frame.add(alarmSound);
		}


	public void openWindiow()
	{
		frame.setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		Object source = e.getSource();
		
		if(source == choice)
		{
			sChoice = choice.getSelectedItem();
			System.out.println(sChoice);
		}
	}
	
	public String getChoice()
	{
		return sChoice;
	}
	
	
}
