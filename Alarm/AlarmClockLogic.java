import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

/**
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class AlarmClockLogic implements ActionListener
{

	private java.net.URL url;
	private AudioInputStream audioIn;
	private Clip clip;
	private Timer timer;

	private String musicName;
	private StopAlarm stopAlarm;
	private ChosenSettings settings;

	private int delay = 1000;
	private int minutes;
	private int hours;
	private int months;
	private int days;
	private int years;
	private int id;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	AlarmList alarms;

	/**
	 * Konstruktor klasy; tworzy potrzebne obiekty oraz wywo³uje metodê otwieraj¹c¹
	 * plik audio.
	 * 
	 * @param settings
	 *            obiekt zawieraj¹cy konfiguracjê aplikacji
	 * @throws UnsupportedAudioFileException
	 *             wyj¹tek rzucany w przypadku próby otwarcia pliku audio w
	 *             nieos³ugiwanym formacie (obs³ugiwany format .wav)
	 * @throws IOException
	 *             wyj¹tek rzucany w przypadku nieudanej próby odczytu z pliku
	 * @throws LineUnavailableException
	 *             wyj¹tek rzucany w przypaku nieudanej próby otwarcia linii
	 * @throws NullPointerException
	 *             wyj¹tek rzucany w przypadku braku pliku w katalogu
	 */
	public AlarmClockLogic(ChosenSettings settings)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException, NullPointerException
	{
		this.settings = settings;
		this.musicName = settings.getSound();
		this.stopAlarm = new StopAlarm(this);
		int mode = settings.getMode().equals("XML") ? 2 : 1;
		alarms = new AlarmList(mode, true);
		timer = new Timer(delay, this);
		timer.start();
		init();
	}

	/**
	 * Metoda s³u¿¹ca do zmiany pliku audio (zamkniêcie poprzedniego klipu oraz
	 * otwarcie nowego).
	 * 
	 * @throws UnsupportedAudioFileException
	 *             wyj¹tek rzucany w przypadku próby otwarcia pliku audio w
	 *             nieos³ugiwanym formacie (obs³ugiwany format .wav)
	 * @throws IOException
	 *             wyj¹tek rzucany w przypadku nieudanej próby odczytu z pliku
	 * @throws LineUnavailableException
	 *             wyj¹tek rzucany w przypaku nieudanej próby otwarcia linii
	 * @throws NullPointerException
	 *             wyj¹tek rzucany w przypadku braku pliku w katalogu
	 */
	public void setSound()
			throws UnsupportedAudioFileException, IOException, LineUnavailableException, NullPointerException
	{
		if (clip.isOpen() == true)
		{
			clip.close();
		}
		musicName = settings.getSound();
		init();
	}

	/**
	 * Metoda s³u¿¹ca do otwarcia pliku audio.
	 * 
	 * @throws UnsupportedAudioFileException
	 *             wyj¹tek rzucany w przypadku próby otwarcia pliku audio w
	 *             nieos³ugiwanym formacie (obs³ugiwany format .wav)
	 * @throws IOException
	 *             wyj¹tek rzucany w przypadku nieudanej próby odczytu z pliku
	 * @throws LineUnavailableException
	 *             wyj¹tek rzucany w przypaku nieudanej próby otwarcia linii
	 * @throws NullPointerException
	 *             wyj¹tek rzucany w przypadku braku pliku w katalogu
	 */
	public void init() throws UnsupportedAudioFileException, IOException, LineUnavailableException, NullPointerException
	{
		url = this.getClass().getClassLoader().getResource(musicName + ".wav");

		audioIn = AudioSystem.getAudioInputStream(url);
		clip = AudioSystem.getClip();
		clip.open(audioIn);
	}

	/**
	 * Metoda s³u¿¹ca do rozpocz¹cia odtwarzania pliku audio.
	 */
	public void playMusic()
	{
		clip.start();
	}

	/**
	 * Metoda slu¿¹ca do zakoñczenia odtwarzania pliku audio.
	 */
	public void stopMusic()
	{
		clip.stop();
	}

	/**
	 * Metoda sprawdzaj¹ca czy na liœcie wszystkich alarmów znajduje siê taki,
	 * którego data odpowiada danej chwili w rzeczywistoœci.
	 * 
	 * @return zwraca prawdê w przypadku, gdy na liœcie alarmów znajduje siê taki
	 *         który odpowiada danej chwili w rzeczywistoœci; zwraca fa³sz w
	 *         przypadku, gdy na liœcie nie ma alarmu, który odpowiada danej chwili
	 *         w rzeczywistosci
	 */
	public boolean chceckDate()
	{
		int size = alarms.getList().size();
		Calendar cal = Calendar.getInstance();

		for (int i = 0; i < size; i++)
		{
			id = i;

			try
			{
				cal.setTime(format.parse(alarms.getList().get(i)));
			} catch (ParseException e)
			{
				e.printStackTrace();
			}

			days = cal.get(Calendar.DATE);
			months = cal.get(Calendar.MONTH);
			years = cal.get(Calendar.YEAR);
			hours = cal.get(Calendar.HOUR_OF_DAY);
			minutes = cal.get(Calendar.MINUTE);

			if (Calendar.getInstance().get(Calendar.YEAR) == years
					&& Calendar.getInstance().get(Calendar.MINUTE) == minutes
					&& Calendar.getInstance().get(Calendar.MONTH) == months
					&& Calendar.getInstance().get(Calendar.DATE) == days
					&& Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == hours
					&& Calendar.getInstance().get(Calendar.SECOND) == 0)
			{
				return true;
			}

		}

		return false;
	}

	/**
	 * Metoda, która co sekundê (na podstawie timera) sprawdza wartoœæ zwracan¹
	 * przez metodê chceckDate(). Uruchamia alarm w przypadku, gdy metoda
	 * chceckDate() zwróci wartoœæ true. Aktualizuje listê alarmów w przyppadku
	 * wybrania opcji "Drzemka" dodaj¹c wybrany czas drzemki.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{

		if (stopAlarm.getNapValue() == true)
		{
			Calendar cal = Calendar.getInstance();
			stopAlarm.setNapValue(false);

			int currentMinutes = cal.get(Calendar.MINUTE);
			int currentHour = cal.get(Calendar.HOUR_OF_DAY);
			int currentDay = cal.get(Calendar.DATE);

			currentMinutes += stopAlarm.getNapTime();
			if (currentMinutes >= 60)
			{
				currentMinutes = currentMinutes % 60;
				currentHour += 1;
				if (currentHour > 23)
				{
					currentHour = currentHour % 24;
					currentDay += 1;
				}
			}

			cal.set(Calendar.MINUTE, currentMinutes);
			cal.set(Calendar.HOUR_OF_DAY, currentHour);
			cal.set(Calendar.DATE, currentDay);

			SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00.0");
			String sDate = newformat.format(cal.getTime());
			alarms.addAlarmToDB(sDate);
			alarms.updateList(id, sDate);
		}

		if (chceckDate() == true)
		{
			playMusic();
			stopAlarm.show();
		}

	}

	/**
	 * Metoda dodaj¹ca alarm do bazy danych/serializowanego obiektu (w zale¿noœci od
	 * trybu)
	 * 
	 * @param ³añcuch
	 *            znaków reprezentuj¹cy datê alarmu
	 */
	public void addAlarm(String value)
	{
		alarms.addAlarmToDB(value);
	}

	/**
	 * Metoda dodaj¹ca alarm do listy alarmów.
	 * 
	 * @param value
	 *            lañcuch znaków repreentuj¹cy datê alarmu
	 */
	public void addAlarmToList(String value)
	{
		alarms.addAlarm(value);
	}

	/**
	 * 
	 * @return zwraca domyœlny URL bazy danych
	 */
	public String getDefaultUrl()
	{
		return alarms.getDefaultUrl();
	}

	/**
	 * 
	 * @return zwraca obecny URL bazy danych
	 */
	public String getUrl()
	{
		return alarms.getUrl();
	}

	/**
	 * Wywoluje metodê do ustawiania URL bazy danych
	 * 
	 * @param value
	 *            nowa wartoœæ URL
	 * @throws ClassNotFoundException
	 *             {@link https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html}
	 * @throws SQLException
	 *             {@link https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html}
	 */
	public void setUrl(String value) throws ClassNotFoundException, SQLException
	{
		alarms.setUrl(value);
	}

	/**
	 * 
	 * @return zwraca listê wszystkich alarmów
	 */
	public ArrayList<String> getList()
	{
		return alarms.getList();
	}

	/**
	 * 
	 * @return zwraca listê obiektów do serializacji zawieraj¹cych alarmy
	 */
	public ArrayList<AlarmObject> getAlarmXml()
	{
		return alarms.getAlarmXml();
	}

	/**
	 * 
	 * @return zwraca obiekt typu AlarmList
	 */
	public AlarmList getAlarmList()
	{
		return alarms;
	}

	/**
	 * Metoda s³u¿¹ca do zmiany trybu pracy aplikacji. Wywo³uje odpowienie metody
	 * odpwiedzialne za zmianê trybu. Zapisuje stan aplikacji.
	 * 
	 * @param mode
	 *            wartoœæ liczbowa trybu w jakim ma pracowaæ aplikacja (1 - "XML", 2
	 *            - "Do bazy")
	 */
	public void setMode(int mode)
	{
		Serializer serializer = new Serializer("alarm.xml");
		serializer.serialize(alarms.getAlarmXml());
		alarms = new AlarmList(mode, false);
	}

	/**
	 * Metoda wywo³uj¹ca odpowiednie metody informuj¹ce o stanie po³¹czenia z baz¹ danych.
	 * @return zwraca true w przypadku, gdy uda³o siê po³¹czyæ z baz¹ danych; zwraca false w przeciwnym wypadku
	 */
	public boolean getConnectionStatus()
	{
		return alarms.getConnectionStatus();
	}

	/**
	 * Metoda wywo³uj¹ca metody s³u¿¹ce do zapisu stanu aplikacji
	 */
	public void saveData()
	{
		alarms.saveData();
	}

}
