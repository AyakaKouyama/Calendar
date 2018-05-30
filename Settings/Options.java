import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Options extends JFrame implements ItemListener, ActionListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel alarmSound;
	JLabel themeLabel;
	JButton ok;
	Choice choice;
	String sChoice;
	
	Choice theme;
	String sTheme;
	AlarmClockLogic alarm;
	Window window;
	
	Options(Window window, AlarmClockLogic alarm)
	{
		
		this.window = window;
		this.alarm = alarm;
		
		setTitle("Ustawienia");
		setResizable(false);
		setLayout(null);
		pack();
		setSize(350, 200);
		setLocationRelativeTo(null);
		
		ok = new JButton("OK");
		ok.setBounds(240,105, 80, 30);
		ok.addActionListener(this);
		add(ok);
		
		choice = new Choice();
		choice.setBounds(20, 40, 110, 150);
		choice.addItemListener(this);
		choice.setFont(new Font("Arial", Font.PLAIN, 15));
	
		for(int i = 0; i<3; i++)
		{
			choice.add("Dzwiêk " + (i + 1));
		}
		
		sChoice = choice.getItem(0);
		choice.select(window.getSettings().getSound());
		add(choice);

		theme = new Choice();
		theme.setBounds(20, 110, 110, 100);
		theme.setFont(new Font("Arial", Font.PLAIN, 15));
		theme.addItemListener(this);
		theme.add("Niebieski");
		theme.add("Ró¿owy");
		theme.add("Zielony");
		theme.select(window.getSettings().getTheme());
		add(theme);
		sTheme = theme.getSelectedItem();
		
		alarmSound = new JLabel("Dzwiêk alarmu");
		alarmSound.setBounds(20, 10, 100, 30);
		alarmSound.setFont(new Font("Arial", Font.PLAIN, 14));
		add(alarmSound);
		
		themeLabel = new JLabel("Motyw");
		themeLabel.setBounds(20, 80, 100, 30);
		themeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		add(themeLabel);
		
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
			window.getSettings().setSound(sChoice);
			alarm.setSound();
		}
		
		if(source == theme)
		{
			sTheme = theme.getSelectedItem();
			window.getSettings().setTheme(sTheme);
			window.getCalendaeWindow().refresh();
		}
	}
	
	public String getChoice()
	{
		return sChoice;
	}
	
	public String getTheme()
	{
		return sTheme;
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
