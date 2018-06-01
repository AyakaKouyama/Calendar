import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Options extends JFrame implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	private JLabel alarmSound;
	private JLabel themeLabel;
	private JLabel urlLabel;
	private JButton ok;
	private JButton change;
	private JButton setDefualt;

	private Choice choice;
	private Choice theme;

	private String sTheme;
	private String sMode;
	private String sChoice;
	
	private JRadioButton modeXml;
	private JRadioButton modeBase;
	private ButtonGroup mode;
	private JTextField url;

	private int iMode;

	private AlarmClockLogic alarm;
	private Window window;

	Options(Window window, AlarmClockLogic alarm)
	{

		this.window = window;
		this.alarm = alarm;
		initFrame();
		initComponents();
	}

	private void initFrame()
	{
		setTitle("Ustawienia");
		setResizable(false);
		setLayout(null);
		pack();
		setSize(380, 270);
		setLocationRelativeTo(null);
	}
	
	private void initComponents()
	{
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
		if (window.getSettings().getMode().equals("XML"))
		{
			iMode = 2;
			modeXml.setSelected(true);
		} else
		{
			iMode = 1;
			modeBase.setSelected(true);
		}
		
		if(alarm.getConnectionStatus() == true)
		{
			iMode = 2;
			modeXml.setSelected(true);
		}
		modeXml.addItemListener(this);
		modeBase.addItemListener(this);
		add(modeXml);
		add(modeBase);

		url = new JTextField(100);
		url.setBounds(160, 110, 195, 25);
		url.addActionListener(this);
		url.setText(window.getSettings().getUrl());
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
		setIconImage(new ImageIcon("calendar.png").getImage());
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
			try
			{
				alarm.setSound();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | NullPointerException e1)
			{
				SoundNotFoundError error = new SoundNotFoundError();
				error.show(this);
			}
		}

		if (source == theme)
		{
			sTheme = theme.getSelectedItem();
			window.getSettings().setTheme(sTheme);
			window.getCalendarWindow().refresh();
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
			window.getCalendarWindow().refresh();
			if (sMode == "XML")
			{
				iMode = 2;
				try
				{
					window.getCalendarWindow().getCalendar().setMode(iMode);
					window.getAlarmLogic().setMode(iMode);
				} catch (ClassNotFoundException | SQLException e1)
				{
					e1.printStackTrace();
				}


				window.setMode();

			} else
			{
				iMode = 1;
					try
					{
						window.getCalendarWindow().getCalendar().setMode(iMode);
						window.getAlarmLogic().setMode(iMode);
					} catch (ClassNotFoundException | SQLException e1)
					{
						e1.printStackTrace();
					}

				window.setMode();
			}
		}
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
				e1.printStackTrace();
				url.setText(alarm.getDefaultUrl());
			}
		}
		if (source == setDefualt)
		{
			try
			{
				alarm.setUrl(alarm.getDefaultUrl());
				url.setText(alarm.getDefaultUrl());

			} catch (ClassNotFoundException | SQLException e1)
			{
				e1.printStackTrace();
			}

		}

	}

	public int getMode()
	{
		return iMode;
	}
	
	public String getChoice()
	{
		return sChoice;
	}

	public String getTheme()
	{
		return sTheme;
	}

}
