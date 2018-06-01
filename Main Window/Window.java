import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends WindowAdapter implements ActionListener
{
	private MyClock clock;
	private Options optionsWidnow;
	private AlarmClock alarmWindow;
	private AlarmClockLogic alarmLogic;
	private CalendarWindow calendarWindow;
	private Serializer saveSettings;
	private ChosenSettings settings;
	
	private JFrame frame;
	private JButton about;
	private JButton alarmClock;
	private JButton options;
	private JButton allAlarms;
	private JButton filter;
	private JButton export;
	private JPanel alarmCloclkPanel;

	Window()
	{
		frame = new JFrame();
		initWindow();
		initObjects();
		initComponents();
	}

	private void initWindow()
	{
		frame.getContentPane().setLayout(null);
		frame.setTitle("Kalendarz");
		frame.setIconImage(new ImageIcon("calendar.png").getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setSize(850, 750);
		frame.setLocationRelativeTo(null);
	}

	private void initObjects()
	{
		saveSettings = new Serializer("settings.xml");
		if (saveSettings.deserialize() == null)
		{
			settings = new ChosenSettings();
			settings.setSound("Dzwiêk 1");
			settings.setTheme("Niebieski");
			settings.setMode("XML");
			settings.setUrl("jdbc:sqlserver://localhost:1433;" + "databaseName=meetings;integratedSecurity=true;");
		} else
		{
			settings = (ChosenSettings) saveSettings.deserialize();
		}
		try
		{
			alarmLogic = new AlarmClockLogic(settings);
		} catch (ClassNotFoundException | SQLException e)
		{
			ConnectionError error = new ConnectionError();
			error.show(frame);
			settings.setMode("XML");	
		}
			optionsWidnow = new Options(this, alarmLogic);
			alarmWindow = new AlarmClock(this, alarmLogic);
			calendarWindow = new CalendarWindow(this);
}

	private void initComponents()
	{
		about = new JButton("Info");
		alarmClock = new JButton("Alarm");
		allAlarms = new JButton("Alarmy");
		options = new JButton("Opcje");
		filter = new JButton("Spotkania");
		export = new JButton("Ekspotuj");
		alarmCloclkPanel = new JPanel();

		about.addActionListener(this);
		alarmClock.addActionListener(this);
		allAlarms.addActionListener(this);
		options.addActionListener(this);
		export.addActionListener(this);
		filter.addActionListener(this);
		frame.addWindowListener(this);

		allAlarms.setBounds(655, 280, 100, 30);
		frame.add(allAlarms);
		about.setBounds(655, 600, 100, 30);
		frame.add(about);

		clock = new MyClock(frame);
		alarmClock.setBounds(655, 200, 100, 30);
		frame.add(alarmClock);

		options.setBounds(655, 440, 100, 30);
		frame.add(options);

		filter.setBounds(655, 360, 100, 30);
		frame.add(filter);

		export.setBounds(655, 520, 100, 30);
		frame.add(export);
	}

	public void show()
	{
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == about)
		{
			About aboutInfo = new About();
			aboutInfo.show(frame);
		}

		if (source == alarmClock)
		{
			alarmWindow.updateDate();
			alarmWindow.add();
		}

		if (source == options)
		{
			optionsWidnow.openWindiow();
		}

		if (source == allAlarms)
		{
			AllAlarms alarmList = new AllAlarms();
			alarmList.show();
		}

		if (source == filter)
		{
			MeetingsFilter meetingFilter = new MeetingsFilter(this);
			meetingFilter.show();
		}
		if (source == export)
		{
			ExportWindow exportWindow = new ExportWindow(frame, calendarWindow.getFillMeetingData());
			exportWindow.saveCSV();
		}

	}

	public void setMode()
	{
		calendarWindow.updateCalendar();
		calendarWindow.refresh();
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		saveSettings.serialize(settings);
		Serializer serializer = new Serializer("meeting.xml");
		serializer.serialize(calendarWindow.getMeeting());
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		saveSettings.serialize(settings);
		Serializer serializer = new Serializer("meeting.xml");
		serializer.serialize(calendarWindow.getMeeting());
	}
	
	public ChosenSettings getSettings()
	{
		return settings;
	}

	public Frame getFrame()
	{
		return frame;
	}

	public Options getOptionWindow()
	{
		return optionsWidnow;
	}

	public AlarmClockLogic getAlarmLogic()
	{
		return alarmLogic;
	}

	public CalendarWindow getCalendarWindow()
	{
		return calendarWindow;
	}

}
