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

	AlarmClockLogic(ChosenSettings settings) throws UnsupportedAudioFileException, IOException, LineUnavailableException, NullPointerException
	{
		this.settings = settings;
		this.musicName = settings.getSound();
		this.stopAlarm = new StopAlarm(this);
		int mode = settings.getMode().equals("XML") ? 2 : 1;
		alarms = new AlarmList(mode, true);

		init();
	}

	public void setSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException, NullPointerException
	{
		if (clip.isOpen() == true)
		{
			clip.close();
		}
		musicName = settings.getSound();
		init();
	}

	public void init() throws UnsupportedAudioFileException, IOException, LineUnavailableException, NullPointerException
	{
		timer = new Timer(delay, this);
		timer.start();

		url = this.getClass().getClassLoader().getResource(musicName + ".wav");

		audioIn = AudioSystem.getAudioInputStream(url);
		clip = AudioSystem.getClip();
		clip.open(audioIn);
	}

	public void playMusic()
	{
		clip.start();
	}

	public void stopMusic()
	{
		clip.stop();
	}

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

	public void addAlarm(String value)
	{
		alarms.addAlarmToDB(value);
	}

	public void addAlarmToList(String value)
	{
		alarms.addAlarm(value);
	}

	public String getDefaultUrl()
	{
		return alarms.getDefaultUrl();
	}

	public String getUrl()
	{
		return alarms.getUrl();
	}

	public void setUrl(String value) throws ClassNotFoundException, SQLException
	{
		alarms.setUrl(value);
	}

	public ArrayList<String> getList()
	{
		return alarms.getList();
	}

	public ArrayList<AlarmObject> getAlarmXml()
	{
		return alarms.getAlarmXml();
	}

	public AlarmList getAlarmList()
	{
		return alarms;
	}

	public void setMode(int mode)
	{

		Serializer serializer = new Serializer("alarm.xml");
		serializer.serialize(alarms.getAlarmXml());
		alarms = new AlarmList(mode, false);
		alarms.fill();
	}

	public boolean getConnectionStatus()
	{
		return alarms.getConnectionStatus();
	}

}
