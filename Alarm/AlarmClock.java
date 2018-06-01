import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 */
public class AlarmClock implements ActionListener
{
	private JFrame frame;
	private JButton ok;
	private JButton cancel;
	private JLabel dateLabel;
	private JLabel timeLabel;
	private JFormattedTextField date;
	private JFormattedTextField time;
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	private boolean accteped = false;
	private String formattedDate;

	private Window window;
	private AlarmClockLogic music;

	/**
	 * Konstruktor klasy; wywo³uje metody s³u¿¹ce do inicjalizacji okna oraz komonentów.
	 * @param window okno rodzic
	 * @param music klasa obslugj¹ca logikê alarmów
	 */
	public AlarmClock(Window window, AlarmClockLogic music)
	{
		this.window = window;
		this.music = music;
		initFrame();
		initComponents();
	}

	
	/**
	 * Metoda s³u¿aca do inicjalizacji okna. Ustawia parametry takie jak np. rozmiar okna, jego nazwa.
	 */
	private void initFrame()
	{
		frame = new JFrame();
		frame.setTitle("Alarm Clock");
		frame.setIconImage(new ImageIcon("calendar.png").getImage());
		frame.setResizable(false);
		frame.setLayout(null);
		frame.pack();
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Metoda s³u¿¹ca do inicjalizacji komponentów. Tworzy i konfiguruje etykiety, pola tekstowe, przyciski.
	 */
	private void initComponents()
	{
		dateLabel = new JLabel("Data: (dd/mm/yyyy)");
		timeLabel = new JLabel("Godzina: (hh:mm)");

		dateLabel.setBounds(150, 0, 200, 50);
		frame.add(dateLabel);
		date = new JFormattedTextField(dateFormat);
		date.setText((new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()))));
		date.setBounds(100, 40, 200, 40);
		date.setFont(new Font("Calibri", Font.PLAIN, 20));
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.addActionListener(this);
		frame.add(date);

		timeLabel.setBounds(150, 80, 200, 50);
		frame.add(timeLabel);
		time = new JFormattedTextField(timeFormat);
		time.setText((new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()))));
		time.setBounds(100, 120, 200, 40);
		time.setFont(new Font("Calibri", Font.PLAIN, 20));
		time.setHorizontalAlignment(SwingConstants.CENTER);
		time.addActionListener(this);
		frame.add(time);

		ok = new JButton("Ok");
		ok.setBounds(220, 200, 80, 30);
		ok.addActionListener(this);
		frame.add(ok);

		cancel = new JButton("Anuluj");
		cancel.setBounds(100, 200, 80, 30);
		cancel.addActionListener(this);
		frame.add(cancel);
	}

	/**
	 * Metoda s³u¿¹ca do wyœwietlania okna
	 */
	public void add()
	{
		frame.setVisible(true);
	}

	/**
	 * Metoda aktualizuj¹ca czas w polach tekstowych, tak, aby domyœlnie wyœwietlany by³ aktualny czas systemowy.
	 */
	public void updateDate()
	{
		date.setText((new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()))));
		time.setText((new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()))));
	}

	/**
	 * Metoda do obs³ugi zdarzeñ generwoanych przez naciœniêcie przyciku. Wywo³uje odpowiednie metody do zamkniêcia okna, dodania nowego alarmu.
	 * @param e zdarzenia generowane przez naciœniêcie przycisku 
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == ok)
		{
			accteped = true;
			AlarmInfoDialog alarmInfo = new AlarmInfoDialog(getDate());

			if (alarmInfo.show(window.getFrame()) == true)
			{
				music.addAlarm(formattedDate);
				music.addAlarmToList(formattedDate);
				frame.dispose();
			}

		}

		if (source == cancel)
		{
			frame.dispose();
		}

	}

	/**
	 * Metoda zwracaj¹ca obiekt klasy Calendar z ustawion¹ dat¹ alarmu wybran¹ przez u¿ytownika.
	 * @return obiekt Calendar z ustawion¹ dat¹ alarmu wybran¹ przez u¿ytkownika
	 */
	public Calendar getDate()
	{
		if (accteped == true)
		{
			try
			{
				Calendar cal = Calendar.getInstance();
				cal.setTime(format.parse(date.getText() + " " + time.getText()));
			
				SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00.0");
				Calendar formatted = Calendar.getInstance();
				formatted.setTime(format.parse(date.getText() + " " + time.getText()));
				formattedDate = newformat.format(formatted.getTime());
				
				return cal;
			} catch (java.text.ParseException e)
			{
				e.printStackTrace();
			}
		}

		return null;
	}
	
}
