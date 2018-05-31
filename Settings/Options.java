import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Options extends JFrame implements ItemListener, ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel alarmSound;
	JLabel themeLabel;
	JLabel urlLabel;
	JButton ok;
	JButton change;
	JButton setDefualt;
	Choice choice;
	String sChoice;

	Choice theme;
	String sTheme;
	String sMode;
	AlarmClockLogic alarm;
	Window window;
	JRadioButton modeXml;
	JRadioButton modeBase;
	ButtonGroup mode;
	JTextField url;
	// Choice mode;
	int iMode;

	Options(Window window, AlarmClockLogic alarm)
	{

		this.window = window;
		this.alarm = alarm;

		setTitle("Ustawienia");
		setResizable(false);
		setLayout(null);
		pack();
		setSize(380, 270);
		setLocationRelativeTo(null);

		ok = new JButton("OK");
		ok.setBounds(275, 190, 80, 30);
		ok.addActionListener(this);
		add(ok);

		choice = new Choice();
		choice.setBounds(20, 40, 110, 150);
		choice.addItemListener(this);
		choice.setFont(new Font("Arial", Font.PLAIN, 15));

		for (int i = 0; i < 3; i++)
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

		modeXml = new JRadioButton("XML");
		modeBase = new JRadioButton("Baza Danych");
		modeXml.setBounds(160, 40, 80, 30);
		modeBase.setBounds(250, 40, 100, 30);

		mode = new ButtonGroup();
		mode.add(modeXml);
		mode.add(modeBase);
		System.out.print(window.getSettings().getMode());
		if (window.getSettings().getMode().equals("XML"))
		{
			iMode = 2;
			modeXml.setSelected(true);
		} else
		{
			iMode = 1;
			modeBase.setSelected(true);
		}
		modeXml.addItemListener(this);
		modeBase.addItemListener(this);
		add(modeXml);
		add(modeBase);

		url = new JTextField(100);
		url.setBounds(160, 110, 190, 25);
		url.addActionListener(this);
		url.setText(alarm.getUrl());
		add(url);

		urlLabel = new JLabel("URL bazy danych");
		urlLabel.setBounds(160, 80, 180, 30);
		urlLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		add(urlLabel);

		change = new JButton("Zmieñ URL");
		change.setBounds(255, 150, 98, 20);
		change.addActionListener(this);
		add(change);
		
		setDefualt = new JButton("Reset URL");
		setDefualt.setBounds(160, 150, 100, 20);
		setDefualt.addActionListener(this);
		add(setDefualt);

	}

	public void openWindiow()
	{
		setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getSource();

		if (source == choice)
		{
			sChoice = choice.getSelectedItem();
			window.getSettings().setSound(sChoice);
			alarm.setSound();
		}

		if (source == theme)
		{
			sTheme = theme.getSelectedItem();
			window.getSettings().setTheme(sTheme);
			window.getCalendaeWindow().refresh();
		}
		if (source == modeXml || source == modeBase)
		{
			if (source == modeXml)
			{
				if (modeXml.getSelectedObjects() != null)
				{
					sMode = "XML";
				}
			}
			if (source == modeBase)
			{
				if (modeBase.getSelectedObjects() != null)
				{
					sMode = "Baza danych";
				}
			}

			window.getSettings().setMode(sMode);
			window.getCalendaeWindow().refresh();
			if (sMode == "XML")
			{
				iMode = 2;
				try
				{
					window.getCalendaeWindow().getCalendar().setMode(iMode);
				} catch (ClassNotFoundException | SQLException e1)
				{
					ConnectionError error = new ConnectionError();
					error.show(window.getFrame());
				}

				window.setMode();

			} else
			{
				iMode = 1;
				try
				{
					window.getCalendaeWindow().getCalendar().setMode(iMode);
				} catch (ClassNotFoundException | SQLException e1)
				{
					ConnectionError error = new ConnectionError();
					error.show(window.getFrame());
				}
				window.setMode();
			}
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

		if (source == ok)
		{
			dispose();
		}
		if (source == change)
		{
			try
			{
				alarm.setUrl(url.getText());
			} catch (ClassNotFoundException | SQLException e1)
			{
				ConnectionError error = new ConnectionError();
				error.show(window.getFrame());
			}
		}
		if(source == setDefualt)
		{
			try
			{
				alarm.setUrl(alarm.getDefaultUrl());
				url.setText(alarm.getDefaultUrl());
				
			} catch (ClassNotFoundException | SQLException e1)
			{
				ConnectionError error = new ConnectionError();
				error.show(window.getFrame());
			}
			
		}

	}

	public int getMode()
	{
		return iMode;
	}

}
