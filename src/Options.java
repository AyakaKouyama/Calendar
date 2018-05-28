import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class Options extends JFrame implements ItemListener, ActionListener
{
	
	JLabel alarmSound;
	JLabel style;
	JButton ok;
	Choice choice;
	String sChoice;
	AlarmClockLogic alarm;
	OptionsContext optionsContext;
	
	Options(Window window, AlarmClockLogic alarm)
	{
		
		this.alarm = alarm;
		optionsContext = new OptionsContext();
		
		setTitle("Alarm Clock");
		setResizable(false);
		setLayout(null);
		pack();
		setSize(400, 300);
		setLocationRelativeTo(null);
		
		ok = new JButton("OK");
		ok.setBounds(290,220, 80, 30);
		ok.addActionListener(this);
		add(ok);
		
		choice = new Choice();
		choice.setBounds(20, 40, 100, 30);
		choice.addItemListener(this);
	
		for(int i = 0; i<5; i++)
		{
			choice.add("sound" + (i + 1));
		}
		
		sChoice = choice.getItem(0);
		choice.select(optionsContext.getSound());
		add(choice);
		
		alarmSound = new JLabel("Dzwiêk alarmu");
		alarmSound.setBounds(20, 10, 100, 30);
		add(alarmSound);

	}


	public void openWindiow()
	{
		setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		Object source = e.getSource();
		
		if(source == choice)
		{
			sChoice = choice.getSelectedItem();
			optionsContext.setSound(1, sChoice);
			alarm.setSound();
		}
	}
	
	public String getChoice()
	{
		return sChoice;
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if(source == ok)
		{
			dispose();
		}
		
	}
	
	
}
