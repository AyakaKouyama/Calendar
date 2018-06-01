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
 * Klasa obs�uguj�ca logik� alarm�w. Odpowiedzialna jest za otwieranie,
 * zamykanie plik�w audio, uruchamianie alarm�w, wy��czanie alarm�w,
 * aktualizowanie alarm�w (w przypadku wybrania opcji "Drzemka").
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
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
	 * Konstruktor klasy; tworzy potrzebne obiekty oraz wywo�uje metod� otwieraj�c�
	 * plik audio.
	 * 
	 * @param settings
	 *            obiekt zawieraj�cy konfiguracj� aplikacji
	 * @throws UnsupportedAudioFileException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/UnsupportedAudioFileException.html">https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/UnsupportedAudioFileException.html</a>
	 * @throws IOException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html">https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html</a>
	 * @throws LineUnavailableException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/LineUnavailableException.html">https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/LineUnavailableException.html</a>
	 * @throws NullPointerException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/java/lang/NullPointerException.html">https://docs.oracle.com/javase/7/docs/api/java/lang/NullPointerException.html</a>
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
	 * Metoda s�u��ca do zmiany pliku audio (zamkni�cie poprzedniego klipu oraz
	 * otwarcie nowego).
	 * 
	 * @throws UnsupportedAudioFileException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/UnsupportedAudioFileException.html">https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/UnsupportedAudioFileException.html</a>
	 * @throws IOException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html">https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html</a>
	 * @throws LineUnavailableException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/LineUnavailableException.html">https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/LineUnavailableException.html</a>
	 * @throws NullPointerException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/java/lang/NullPointerException.html">https://docs.oracle.com/javase/7/docs/api/java/lang/NullPointerException.html</a>
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
	 * Metoda s�u��ca do otwarcia pliku audio.
	 * 
	 * @throws UnsupportedAudioFileException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/UnsupportedAudioFileException.html">https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/UnsupportedAudioFileException.html</a>
	 * @throws IOException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html">https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html</a>
	 * @throws LineUnavailableException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/LineUnavailableException.html">https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/LineUnavailableException.html</a>
	 * @throws NullPointerException
	 *             <a href= "https://docs.oracle.com/javase/7/docs/api/java/lang/NullPointerException.html">https://docs.oracle.com/javase/7/docs/api/java/lang/NullPointerException.html</a>
	 */
	public void init() throws UnsupportedAudioFileException, IOException, LineUnavailableException, NullPointerException
	{
		url = this.getClass().getClassLoader().getResource(musicName + ".wav");

		audioIn = AudioSystem.getAudioInputStream(url);
		clip = AudioSystem.getClip();
		clip.open(audioIn);
	}

	/**
	 * Metoda s�u��ca do rozpocz�cia odtwarzania pliku audio.
	 */
	public void playMusic()
	{
		clip.start();
	}

	/**
	 * Metoda slu��ca do zako�czenia odtwarzania pliku audio.
	 */
	public void stopMusic()
	{
		clip.stop();
	}

	/**
	 * Metoda sprawdzaj�ca czy na li�cie wszystkich alarm�w znajduje si� taki,
	 * kt�rego data odpowiada danej chwili w rzeczywisto�ci.
	 * 
	 * @return zwraca prawd� w przypadku, gdy na li�cie alarm�w znajduje si� taki
	 *         kt�ry odpowiada danej chwili w rzeczywisto�ci; zwraca fa�sz w
	 *         przypadku, gdy na li�cie nie ma alarmu, kt�ry odpowiada danej chwili
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
	 * Metoda, kt�ra co sekund� (na podstawie timera) sprawdza warto�� zwracan�
	 * przez metod� chceckDate(). Uruchamia alarm w przypadku, gdy metoda
	 * chceckDate() zwr�ci warto�� true.Aktualizuje list� alarm�w w przyppadku
	 * wybrania opcji "Drzemka" dodaj�c wybrany czas drzemki.
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
	 * Metoda dodaj�ca alarm do bazy danych/serializowanego obiektu (w zale�no�ci od
	 * trybu).
	 * 
	 * @param �a�cuch
	 *            znak�w reprezentuj�cy dat� alarmu
	 */
	public void addAlarm(String value)
	{
		alarms.addAlarmToDB(value);
	}

	/**
	 * Metoda dodaj�ca alarm do listy alarm�w.
	 * 
	 * @param value
	 *            la�cuch znak�w repreentuj�cy dat� alarmu
	 */
	public void addAlarmToList(String value)
	{
		alarms.addAlarm(value);
	}

	/**
	 * Metoda zwracaj�ca domy�lny URL bazy danych.
	 * 
	 * @return zwraca domy�lny URL bazy danych
	 */
	public String getDefaultUrl()
	{
		return alarms.getDefaultUrl();
	}

	/**
	 * Metoda zwracaj�ca oceny URL bazy danych.
	 * 
	 * @return zwraca obecny URL bazy danych
	 */
	public String getUrl()
	{
		return alarms.getUrl();
	}

	/**
	 * Wywo�uje metod� do ustawiania URL bazy danych.
	 * 
	 * @param value
	 *            nowa warto�� URL
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
	 * Metoda zwracaj�ca list� wszystkich alarm�w.
	 * 
	 * @return zwraca list� wszystkich alarm�w
	 */
	public ArrayList<String> getList()
	{
		return alarms.getList();
	}

	/**
	 * Metoda zwracaj�ca list� obiekt�w do serializacji zawieraj�cych alarmy.
	 * 
	 * @return zwraca list� obiekt�w do serializacji zawieraj�cych alarmy
	 */
	public ArrayList<AlarmObject> getAlarmXml()
	{
		return alarms.getAlarmXml();
	}

	/**
	 * Metoda zwracaj�ca obiekt typu AlarmList
	 * 
	 * @return zwraca obiekt typu AlarmList
	 */
	public AlarmList getAlarmList()
	{
		return alarms;
	}

	/**
	 * Metoda s�u��ca do zmiany trybu pracy aplikacji.Wywo�uje odpowienie metody
	 * odpwiedzialne za zmian� trybu.Zapisuje stan aplikacji.
	 * 
	 * @param mode
	 *            warto�� liczbowa trybu w jakim ma pracowa� aplikacja (1 - "XML", 2
	 *            - "Do bazy")
	 */
	public void setMode(int mode)
	{
		Serializer serializer = new Serializer("alarm.xml");
		serializer.serialize(alarms.getAlarmXml());
		alarms = new AlarmList(mode, false);
	}

	/**
	 * Metoda wywo�uj�ca odpowiednie metody informuj�ce o stanie po��czenia z baz�
	 * danych.
	 * 
	 * @return zwraca true w przypadku, gdy uda�o si� po��czy� z baz� danych; zwraca
	 *         false w przeciwnym wypadku
	 */
	public boolean getConnectionStatus()
	{
		return alarms.getConnectionStatus();
	}

	/**
	 * Metoda wywo�uj�ca metody s�u��ce do zapisu stanu aplikacji.
	 */
	public void saveData()
	{
		alarms.saveData();
	}

}
