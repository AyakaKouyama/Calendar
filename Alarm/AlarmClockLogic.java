import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;

import javax.print.DocFlavor.URL;
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
	int minutes;
	int hours;
	int months;
	int days;
	int years;
	
	boolean initMinutes = false;

	AlarmClockLogic(AlarmClock alarm, String musicName)
	{
		this.alarm = alarm;
		this.musicName = musicName;
		this.stopAlarm = new StopAlarm(this);
		init();
	}
	
	AlarmClockLogic(InsertMeetingWindow meeting, String musicName)
	{
		this.meeting = meeting;
		this.musicName = musicName;
		this.stopAlarm = new StopAlarm(this);
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
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(alarm != null || meeting != null)
		{
			if(alarm != null)
			{
			    if(alarm.getDate() != null)
				{
					if(initMinutes == false)
					{
						minutes = alarm.getDate().get(Calendar.MINUTE);
						hours = alarm.getDate().get(Calendar.HOUR_OF_DAY);
						days = alarm.getDate().get(Calendar.DATE);
						months = alarm.getDate().get(Calendar.MONTH);
						years = alarm.getDate().get(Calendar.YEAR);
						
						initMinutes = true;
					}
				}
			}
			
			if(meeting != null)
			{
				if(meeting.getDate() != null)
				{
					if(initMinutes == false)
					{
						minutes = meeting.getDate().get(Calendar.MINUTE);
						hours = meeting.getDate().get(Calendar.HOUR_OF_DAY);
						days = meeting.getDate().get(Calendar.DATE);
						months = meeting.getDate().get(Calendar.MONTH);
						years = meeting.getDate().get(Calendar.YEAR);
						
						initMinutes = true;
					}
				}
			}

	    	if(stopAlarm.getNapValue() == true)
	    	{			
	    		 minutes += stopAlarm.getNapTime();
				 stopAlarm.setNapValue(false);
				 if(minutes >= 60)
				 {
					 minutes = minutes % 60;
					 hours += 1;
				 }
	    	}
	    		
			if(Calendar.getInstance().get(Calendar.YEAR) == years &&  Calendar.getInstance().get(Calendar.MINUTE) == minutes
			   && Calendar.getInstance().get(Calendar.MONTH) == months &&  Calendar.getInstance().get(Calendar.DATE) == days
			    && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == hours && Calendar.getInstance().get(Calendar.SECOND) == 0)
			{
				playMusic();
				stopAlarm.show();
			}
			
		}
	}
}

