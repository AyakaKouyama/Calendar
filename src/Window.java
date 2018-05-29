import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Window implements ActionListener
{
	JFrame frame;
	JButton about;
	JButton alarmClock;
	JButton options;
	JButton allAlarms;
	JButton filter;
	
	JPanel alarmCloclkPanel;
	
	JTextField text1;
	
	JLabel year;
	JLabel month;
	JLabel dateTime;
	JLabel time;
	
	String meetingDetails = null;
	MyClock clock;
	Options optionsWidnow;
	AlarmClock alarmWindow;
	AlarmClockLogic alarmLogic;
	CalendarWindow calendarWindow;
	
	Window(String title) 
	{
		alarmLogic = new AlarmClockLogic();
		alarmWindow = new AlarmClock(this, alarmLogic);
		optionsWidnow = new Options(this, alarmLogic);
		
		frame = new JFrame();
		
		about = new JButton("Info");
		alarmClock = new JButton("Alarm");
		allAlarms = new JButton("Alarmy");
		alarmCloclkPanel = new JPanel();
	
		
		about.addActionListener(this);
		alarmClock.addActionListener(this);
		allAlarms.addActionListener(this);
	
	    
		frame.getContentPane().setLayout(null);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setSize(850, 750);
		frame.setLocationRelativeTo(null);
		

		
		allAlarms.setBounds(655, 280, 100, 30);
		frame.add(allAlarms);
		

		about.setBounds(655, 520, 100, 30);
		frame.add(about);
		
		clock = new MyClock(frame);
		
		alarmClock.setBounds(655, 200, 100, 30);
		frame.add(alarmClock);
		
		options = new JButton("Opcje");
		options.setBounds(655, 440, 100, 30);
		options.addActionListener(this);
		frame.add(options);
		
		filter = new JButton("Spotkania");
		filter.setBounds(655, 360, 100, 30);
		filter.addActionListener(this);
		frame.add(filter);
		calendarWindow = new CalendarWindow(this);

	}
	
	public void show()
	{
		frame.setVisible(true);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		
		if(source == about)
		{
			About aboutInfo = new About();
			aboutInfo.show(frame);	
		}
		
		if(source == alarmClock)
		{
			alarmWindow.updateDate();
			alarmWindow.add();
		}
		
		if(source == options)
		{
			optionsWidnow.openWindiow();
		}
		
		if(source == allAlarms)
		{
			AllAlarms alarmList = new AllAlarms();
			alarmList.show();
		}
		
		if(source == filter)
		{
			MeetingsFilter meetingFilter = new MeetingsFilter(this);
			meetingFilter.show();
		}

		
	}
	

	public Frame getFrame()
	{
		return frame;
	}
	
	public String getOptionsChoice()
	{
		return optionsWidnow.getChoice();
	}
	

	public Options getOptionWindow()
	{
		return optionsWidnow;
	}
	
	public AlarmClockLogic getAlarmLogic()
	{
		return alarmLogic;
	}
	
	public CalendarWindow getCalendaeWindow()
	{
		return calendarWindow;
	}
}
