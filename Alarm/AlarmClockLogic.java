import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

public class AlarmClockLogic implements ActionListener
{

	java.net.URL url;
	AudioInputStream audioIn;
	Clip clip;
	Timer timer;
	int delay = 1000;
	AlarmClock alarm;
	InsertMeetingWindow meeting;
	String musicName;
	StopAlarm stopAlarm;
	OptionsContext options;
	int minutes;
	int hours;
	int months;
	int days;
	int years;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	int id;
	
	AlarmList alarms;

	AlarmClockLogic()
	{
		options = new OptionsContext();
		this.musicName = options.getSound();
		this.stopAlarm = new StopAlarm(this);
		alarms = new AlarmList();
		alarms.fill();
		init();
	}
	
	public void setSound()
	{
		if(clip.isOpen() == true)
		{
			clip.close();
		}
		musicName = options.getSound();
		init();
	}
	
	public void init()
	{
		timer = new Timer(delay, this);
		timer.start();
		
		try
		{
			url = this.getClass().getClassLoader().getResource(musicName + ".wav");
	        audioIn = AudioSystem.getAudioInputStream(url);
	        clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        
	    } 
		catch (UnsupportedAudioFileException exc)
		{
	          System.out.println(exc.getMessage());
	    }
		catch (LineUnavailableException exc) 
		{
			System.out.println(exc.getMessage());
	    }
		catch(IOException exc)
		{
			System.out.println(exc.getMessage());
		}
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
		
		for(int i = 0; i< size; i++)
		{
			id = i;
			
			try 
			{
				cal.setTime(format.parse(alarms.getList().get(i)));
				
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			
			days = cal.get(Calendar.DATE);
			months = cal.get(Calendar.MONTH);
			years = cal.get(Calendar.YEAR);
			hours = cal.get(Calendar.HOUR_OF_DAY);
			minutes = cal.get(Calendar.MINUTE);
			

			if(Calendar.getInstance().get(Calendar.YEAR) == years &&  Calendar.getInstance().get(Calendar.MINUTE) == minutes
			   && Calendar.getInstance().get(Calendar.MONTH) == months &&  Calendar.getInstance().get(Calendar.DATE) == days
			   && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == hours && Calendar.getInstance().get(Calendar.SECOND) == 0)
			{
				alarms.removeAlarm(years + "-" + (months + 1) + "-" + days + " " + hours + ":" + minutes + ":00.0");
				System.out.println(years + "-" + (months + 1) + "-" + days + " " + hours + ":" + minutes + ":00.0");
			}
			
		}
		
		
		return false;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
		if(stopAlarm.getNapValue() == true)
		{
			Calendar cal = Calendar.getInstance();
			stopAlarm.setNapValue(false);
			
			int currentMinutes = cal.get(Calendar.MINUTE);
			int currentHour = cal.get(Calendar.HOUR_OF_DAY);
			
			currentMinutes += stopAlarm.getNapTime();
			if(minutes >= 60)
			{
			   minutes = minutes % 60;
			   hours += 1;
			}
			
			System.out.println(id);
			String sDate = Integer.toString(cal.get(Calendar.YEAR)) + '-' + Integer.toString(cal.get(Calendar.MONTH) + 1) + '-' + Integer.toString(cal.get(Calendar.DATE)) + " " + Integer.toString(currentHour) + ":" + Integer.toString(currentMinutes);
			alarms.update(id, sDate);
		    alarms.updateList(id, sDate);
		}

		
		if(chceckDate() == true)
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

}

