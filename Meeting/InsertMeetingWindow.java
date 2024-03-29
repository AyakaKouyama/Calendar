import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Okno s�u��ce do dodawania nowego spotkania w danym dniu, usuwania spotkania,
 * dodawania przypomnie� dzwi�kowych o wydarzeniu oraz modyfikacji istniej�cych
 * spotka�.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class InsertMeetingWindow implements ActionListener, ItemListener
{
	private JFrame frame;
	private JLabel detalisLabel;
	private JLabel nameLabel;
	private JLabel localizationLabel;
	private JLabel dateLabel;

	private JButton accept;
	private JButton alarm;
	private JButton deleteMeeting;
	private JButton cancel;
	private Choice remindChoice;

	private JTextField details;
	private JTextField name;
	private JTextField localization;
	private JTextField time;
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private String stringDetails = null;
	private String stringLocalization = null;
	private String stringDate = null;
	private String stringName = null;

	private boolean done = false;
	private boolean accepted = false;
	private int column;
	private int row;
	private int day;
	private int month;
	private int year;
	private int reminderTime;

	private AlarmClockLogic alarmClock;
	private AlarmInfoDialog alarmInfo;
	private CalendarLogic mcalenadar;
	private Window window;

	/**
	 * Konstruktor klasy inicjalizuje okno oraz komponenty zawarte w oknie.
	 * 
	 * @param window
	 *            okno - rodzic
	 * @param calendar
	 *            klasa do obs�ugi logiki kalendarza
	 * @param column
	 *            kolumna dla kt�rej wywo�ano okno
	 * @param row
	 *            rz�d dla kt�rego wywo�ano okno
	 * @param year
	 *            rok dla kt�rego wyow�ano okno
	 * @param month
	 *            miesi�c dla kt�rego wywo�ano okno
	 * @param day
	 *            dzie� dla kt�rego wywo�ano okno
	 * @param soundName
	 *            wybrany dzwi�k alarmu
	 * @param alarmClock
	 *            klasa do obs�ugi logiki alarmu
	 */
	InsertMeetingWindow(Window window, CalendarLogic calendar, int column, int row, int year, int month, int day,
			String soundName, AlarmClockLogic alarmClock)
	{
		this.window = window;
		this.column = column;
		this.row = row;
		this.mcalenadar = calendar;
		this.day = day;
		this.year = year;
		this.month = month;
		this.alarmClock = alarmClock;

		initFrame();
		initComponents();
		initFillTable();

	}

	/**
	 * Metoda s�u��ca do wype�nienia kom�rek z informacjami o spotkaniu na podstawie
	 * listy spotka�.
	 */
	public void initFillTable()
	{

		String sId = Integer.toString(day) + Integer.toString(month) + Integer.toString(year % 100);
		int id = Integer.parseInt(sId);
		String meeting = mcalenadar.getFillMeetingData().getMap().get(id);

		if (meeting != null && meeting != "")
		{
			int[] index = new int[5];
			index[0] = meeting.indexOf(':');
			for (int i = 1; i < 5; i++)
			{
				index[i] = meeting.indexOf(':', (index[i - 1] + 1));
			}

			if (index[4] == -1)
			{
				name.setText(meeting.substring(index[0] + 1, index[1] - "Lokalizacja".length() - 1));
				localization.setText(meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1));
				time.setText(meeting.substring(index[2] + 1, index[3] - "Szczeg�y".length() - 1));
				details.setText(meeting.substring(index[3] + 1, meeting.length()));
			} else
			{
				name.setText(meeting.substring(index[0] + 1, index[1] - "Lokalizacja".length() - 1));
				localization.setText(meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1));
				time.setText(meeting.substring(index[3] - 2, index[4] - "Szczeg�y".length() - 1));
				details.setText(meeting.substring(index[4] + 1, meeting.length()));
			}

		}

	}

	/**
	 * Metoda do inicjalizacji komponent�w: p�l tekstowych, przycisk�w, listy
	 * wyboru.
	 */
	private void initComponents()
	{
		detalisLabel = new JLabel("Szczeg�y");
		nameLabel = new JLabel("Nazwa");
		localizationLabel = new JLabel("Lokalizacja");
		dateLabel = new JLabel("Data");
		details = new JTextField(20);
		accept = new JButton("Dodaj");
		alarm = new JButton("Alarm");
		deleteMeeting = new JButton("Usu� spotkanie");
		deleteMeeting.setText("<html><center>" + "Usu�" + "<br>" + "spotkanie" + "</center></html>");
		cancel = new JButton("Anuluj");

		remindChoice = new Choice();

		name = new JTextField(20);
		localization = new JTextField(20);
		time = new JFormattedTextField(timeFormat);
		time.setText((new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()))));

		remindChoice.setBounds(300, 70, 80, 30);
		remindChoice.add("0 min");
		remindChoice.add("5 min");
		remindChoice.add("15 min");
		remindChoice.add("30 min");
		remindChoice.add("1 godz");
		remindChoice.add("12 godz");
		remindChoice.add("1 dzie�");
		remindChoice.select(2);
		remindChoice.addItemListener(this);
		reminderTime = 15;

		alarm.setBounds(300, 20, 80, 30);
		alarm.addActionListener(this);
		frame.add(alarm);

		name.setBounds(100, 20, 150, 30);
		localization.setBounds(100, 60, 150, 30);
		time.setBounds(100, 100, 150, 30);
		details.setBounds(100, 140, 150, 30);

		nameLabel.setBounds(20, 20, 150, 30);
		localizationLabel.setBounds(20, 60, 150, 30);
		dateLabel.setBounds(20, 100, 150, 30);
		detalisLabel.setBounds(20, 140, 150, 30);

		deleteMeeting.setBounds(300, 120, 80, 50);
		deleteMeeting.addActionListener(this);

		frame.add(deleteMeeting);
		frame.add(detalisLabel);
		frame.add(remindChoice);
		frame.add(nameLabel);
		frame.add(localizationLabel);
		frame.add(dateLabel);
		frame.add(localization);
		frame.add(time);
		frame.add(name);
		frame.add(detalisLabel);
		frame.add(details);
		accept.setBounds(200, 200, 80, 30);
		cancel.setBounds(100, 200, 80, 30);
		frame.add(cancel);
		frame.add(accept);

		accept.addActionListener(this);
		details.addActionListener(this);
		cancel.addActionListener(this);

	}

	/**
	 * Metoda do inicjalizacji okna. Ustawia parametry takie jak np. rozmiar okna,
	 * nazwa.
	 */
	public void initFrame()
	{
		frame = new JFrame();
		frame.setIconImage(new ImageIcon("calendar.png").getImage());
		frame.setTitle("Meeting");
		frame.setResizable(false);
		frame.setLayout(null);
		frame.pack();
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Metoda s�u�aca do wy�wietlania okna.
	 */
	public void show()
	{
		frame.setVisible(true);
	}

	/**
	 * Metoda obs�uguj�ca zdarzenia generowane przez naci�ni�cie przycisku. Wywo�uje
	 * metody do zamkni�cia okna, usuni�cia spotkania, ustawienia przypomnienia o
	 * spotkaniu.
	 * 
	 * @param e
	 *            zdarzenie generowane przez naci�niecie przycisku.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == accept)
		{
			done = true;
			stringDetails = details.getText();
			stringName = name.getText();
			stringLocalization = localization.getText();
			stringDate = time.getText();

			if (stringName.equals("") && stringDetails.equals("") && stringLocalization.equals("")
					&& stringDate.equals(""))
			{
				frame.dispose();
				accepted = false;
				frame.dispose();
			} else if (stringName.equals("") == true)
			{
				InsertNameWarning warning = new InsertNameWarning();
				warning.show(frame);
			} else
			{
				fill(column, row);
				fillData(column, row);
				accepted = false;
				frame.dispose();
			}

		}

		if (source == alarm)
		{
			accepted = true;
			alarmInfo = new AlarmInfoDialog(getDate());
			alarmInfo.show(window.getFrame());
		}

		if (source == deleteMeeting)
		{
			String sId = Integer.toString(day) + Integer.toString(month) + Integer.toString(year % 1000);
			int id = Integer.parseInt(sId);

			name.setText("");
			localization.setText("");
			time.setText("");
			details.setText("");

			mcalenadar.deleteMeeting(id);
			fill(column, row);
			fillData(column, row);
		}

		if (source == cancel)
		{
			frame.dispose();
		}
	}

	/**
	 * Metoda aktualizuj�ca stan tabeli kalendarza po dodaniu nowego spotkania.
	 * 
	 * @param row
	 *            modyfikowany rz�d
	 * @param column
	 *            modyfikowana kolumna
	 */
	public void fill(int row, int column)
	{
		if (!name.getText().equals("") || !localization.getText().equals("") || !time.getText().equals("")
				|| !details.getText().equals(""))
		{

			window.getCalendarWindow().fillCell(stringName, row, column);
		} else
		{
			window.getCalendarWindow().fillCell("", row, column);
		}
	}

	/**
	 * Metdoa wywo�uj�ca metody s�u��ce do dodania spotkania do listy spotka�,
	 * modyfikuj�ce stan bazy oraz obiektu przechowuj�cego spotkania do pliku XML(w
	 * zale�no�ci od u�ywanego trybu).
	 * 
	 * @param row
	 *            modyfikowany rz�d
	 * @param column
	 *            modyfikowana kolumna
	 */
	public void fillData(int row, int column)
	{
		String sId = Integer.toString(day) + Integer.toString(month) + Integer.toString(year % 1000);
		int id = Integer.parseInt(sId);
		if (!name.getText().equals("") || !localization.getText().equals("") || !time.getText().equals("")
				|| !details.getText().equals(""))
		{
			String value = "Nazwa:" + stringName + "\nLokalizacja:" + stringLocalization + "\nGodzina: " + stringDate
					+ "\nSzczeg�y:" + stringDetails;
			String date = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day) + " "
					+ stringDate;

			mcalenadar.AddElementToList(id, value);
			mcalenadar.addNameToList(id, stringName);

			if (mcalenadar.getMode() == 1) if (mcalenadar.getId(id) != -1)
			{
				mcalenadar.setName(id, stringName);
				mcalenadar.setLocalization(id, stringLocalization);
				mcalenadar.setDate(id, stringDate);
				mcalenadar.setDetails(id, stringDetails);
			} else
			{
				mcalenadar.insert(id, stringName, stringLocalization, date, stringDetails);
			}

			if (mcalenadar.getMode() == 2)
			{
				mcalenadar.setNameXml(id, stringName);
				mcalenadar.setLocalizationXml(id, stringLocalization);
				mcalenadar.setDateXml(id, stringDate);
				mcalenadar.setDetailsXml(id, stringDetails);
			}
		} else
		{
			mcalenadar.deleteMeetingFromList(id);
		}
	}

	/**
	 * Metoda zwracaj�ca �a�cuch znak�w opisuj�cy szczeg�y spotkania.
	 * @return �a�cuch znak�w zawieraj�cy szczeg�y spotkania
	 */
	public String getDetails()
	{
		return stringDetails;
	}

	/**
	 * Metoda s�u�aca do dodania noewego powiadomienia o spotkaniu.
	 * 
	 * @return kalendarz z ustawion� dat� przypomnienia o spotkaniu
	 */
	public Calendar getDate()
	{
		if (accepted == true)
		{
			String sTime = time.getText();

			String minutes = sTime.substring(3);
			String hour = sTime.substring(0, 2);

			int h = Integer.parseInt(hour);
			int min = Integer.parseInt(minutes);
			int d = day;
			if (reminderTime < 60)
			{
				min -= reminderTime;

				if (min < 0)
				{
					min = 60 + min;
					h -= 1;
				}

			} else if (reminderTime >= 60)
			{
				if (reminderTime == 60)
				{
					h -= 1;

					if (h < 0)
					{
						d -= 1;
						h = 24 + h;
					}
				}
				if (reminderTime == 60 * 12)
				{
					h -= 12;

					if (h < 0)
					{
						d -= 1;
						h = 24 + h;
					}
				}
				if (reminderTime == 60 * 24)
				{
					d -= 1;
				}
			}

			sTime = Integer.toString(h) + ":" + Integer.toString(min);

			String sDate = Integer.toString(d) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
			Calendar cal = Calendar.getInstance();

			Calendar formatted = Calendar.getInstance();
			try
			{
				formatted.setTime(format.parse(sDate + " " + sTime));
			} catch (ParseException e1)
			{
				e1.printStackTrace();
			}

			SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00.0");
			String ssDate = newformat.format(formatted.getTime());

			alarmClock.addAlarm(ssDate);
			alarmClock.addAlarmToList(ssDate);
			try
			{
				cal.setTime(format.parse(sDate + " " + sTime));
				return cal;
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Metoda do obs�ugi zdarze� generowanych przez zmian� na li�cie wyboru czasu przypomnienia o
	 * spotkaniu.
	 * 
	 * @param e
	 *            zdarzenie generwoane przez zmian� na li�cie wyboru czasu
	 *            przypomnienia o spotkaniu
	 */
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getSource();

		if (source == remindChoice)
		{
			if (remindChoice.getSelectedItem() == "0 min")
			{
				reminderTime = 0;
			}
			if (remindChoice.getSelectedItem() == "5 min")
			{
				reminderTime = 5;
			}
			if (remindChoice.getSelectedItem() == "15 min")
			{
				reminderTime = 15;
			}
			if (remindChoice.getSelectedItem() == "30 min")
			{
				reminderTime = 30;
			}
			if (remindChoice.getSelectedItem() == "1 godz")
			{
				reminderTime = 60;
			}
			if (remindChoice.getSelectedItem() == "12 godz")
			{
				reminderTime = 60 * 12;
			}
			if (remindChoice.getSelectedItem() == "1 dzie�")
			{
				reminderTime = 60 * 24;
			}
		}
	}
}
