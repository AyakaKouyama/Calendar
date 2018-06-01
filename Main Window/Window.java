import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**  
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 */
public class Window extends WindowAdapter implements ActionListener
{
	private MyClock clock;
	private Settings optionsWidnow;
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


	/**
	 * Konstruktor klasy Widnow. Inicjalizuje okno oraz zawarte w nim komponenty.
	 */
	public Window()
	{
		frame = new JFrame();
		initWindow();
		initObjects();
		initComponents();
	}

	/**
	 * Metoda do inicjalizacji okna. Ustawia niezbêdne parametry okna takie jak nazwa, rozmiar itp.
	 */
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

	/**
	 * Metoda do inicjalizacji obiektów potrzebnych do obslugi aplikacji.
	 */
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
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | NullPointerException e)
		{
			SoundNotFoundError error = new SoundNotFoundError();
			error.show(frame);
		}
		optionsWidnow = new Settings(this, alarmLogic);
		alarmWindow = new AlarmClock(this, alarmLogic);
		calendarWindow = new CalendarWindow(this);
	}

	/**
	 * Metoda do inicjalizacji komponentów zawartych w oknie takich jak np. przyciski, napisy.
	 */
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

	/**
	 * Metoda s³u¿¹ca do wyœwietlania okna.
	 */
	public void show()
	{
		frame.setVisible(true);
	}

	/**
	 * @param e zdarzenie generowane po naciœniêciu na komponent
	 * Metoda do ob³ugi zdarzeñ po naciœniêciu na dany przycisk.
	 */
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
			AllAlarms alarmList;
				int mode = settings.getMode().equals("XML") ? 2 : 1;
				alarmList = new AllAlarms(mode, alarmLogic.getAlarmList());
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

	/**
	 * Metoda aktualizuj¹ca stan okna po zmianie trybu pracy aplikacji z "XML" na "Baza danych" i odworotnie.
	 */
	public void setMode()
	{
		calendarWindow.updateCalendar();
		calendarWindow.refresh();
	}


	/**
	 * @param e zdarzenie generowane w momencie zamknêcia okna
	 * Metoda wywo³uj¹ca odpowiednie metody maj¹ce na celu zapis stanu aplikacji w momencie zakoñczenia jej dzia³ania.
	 */
	@Override
	public void windowClosing(WindowEvent e)
	{
		if (calendarWindow != null)
		{
			saveSettings.serialize(settings);
			calendarWindow.saveData();
		}
		
		if (alarmLogic != null)
		{
			alarmLogic.saveData();
		}

	}

	/**
	 * 
	 * @return obiekt typu ChosenSettings zawieraj¹cy informacje o obecnej konfiguracji aplikacji
	 */
	public ChosenSettings getSettings()
	{
		return settings;
	}

	/**
	 * 
	 * @return okno
	 */
	public Frame getFrame()
	{
		return frame;
	}

	/**
	 * 
	 * @return okno ustawieñ aplikacji
	 */
	public Settings getOptionWindow()
	{
		return optionsWidnow;
	}

	/**
	 * 
	 * @return obiekt typu AlarmClockLogic odpowiedzialny za logikê kalendarza
	 */
	public AlarmClockLogic getAlarmLogic()
	{
		return alarmLogic;
	}

	/**
	 * 
	 * @return okno tabeli kalendarza
	 */
	public CalendarWindow getCalendarWindow()
	{
		return calendarWindow;
	}

}
