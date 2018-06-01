import java.sql.SQLException;
import java.util.ArrayList;

public class AlarmList 
{
	private ArrayList<String> alarm;
	private ArrayList<AlarmObject> alarmXml;
	private AlarmTable db;
	private ProgressBar bar;
	private int mode;
	private boolean connectionFailed = false;
	AlarmList(int mode, boolean showBar) 
	{
		this.mode = mode;
		alarm = new ArrayList<String>();
		AlarmObjectList alarmsXml = new AlarmObjectList();
		bar = new ProgressBar();
		alarmXml = alarmsXml.deserializeList();
		
		if(showBar == true)
			bar.show();
		
		try
		{
			db = new AlarmTable();
		} catch (ClassNotFoundException | SQLException e)
		{
			mode = 2;
			connectionFailed = true;
			bar.close();
			fill();
		}
		bar.close();
		fill();
	}
	
	public void fill()
	{
		mode = connectionFailed == true ? 2 : mode;
		if(mode == 1)
		{
			ArrayList<Integer> ids = db.getAllIds();
			
			for(int i = 0; i<ids.size(); i++)
			{
				alarm.add(db.getDate(ids.get(i)));
			}
		}
		else
		{
			if(alarmXml != null)
			{
				for(int i = 0; i<alarmXml.size(); i++)
				{
					alarm.add(alarmXml.get(i).getDate());
				}
			}
			else
			{
				alarm = new ArrayList<String>();
				alarmXml = new ArrayList<AlarmObject>();
			}
		}
		
	}
	
	public void addAlarm(String value)
	{
		alarm.add(value);
	}
	
	public ArrayList<String> getList()
	{
		return alarm;
	}
	
	public void addAlarmToDB(String value)
	{
		if(mode == 1)
		{
			ArrayList<Integer> ids = db.getAllIds();
		    db.insertDate(ids.size() + 1, value);
		}
		else
		{
			AlarmObject newAlarm = new AlarmObject();
			newAlarm.setDate(value);
			alarmXml.add(newAlarm);
		}
		
	}
	
	public void update(int id, String value)
	{
		if(mode == 1)
			db.setDate(id, value);
		else
			alarmXml.get(id).setDate(value);
	}
	
	public void updateList(int id, String value)
	{
		alarm.set(id, value);
	}
	
	public void removeAlarm(String value)
	{
		if(mode == 1)
			db.remove(value);
		else
		{
			for(int i = 0; i<alarmXml.size(); i++)
			{
				if(alarmXml.get(i).getDate().equals(value))
				{
					alarmXml.remove(i);
					break;
				}
			}
		}
		
		for(int i = 0; i<alarm.size(); i++)
		{
			if(alarm.get(i).equals(value))
			{
				alarm.remove(i);
				break;
			}
		}
	}
	
	public String getDefaultUrl()
	{
		return db.getDefaultUrl();
	}
	
	public String getUrl()
	{
		return db.getUrl();
	}
	
	public void setUrl(String value) throws ClassNotFoundException, SQLException
	{
		db.setUrl(value);
	}
	
	public ArrayList<AlarmObject> getAlarmXml()
	{
		return alarmXml;
	}
	
	public boolean getConnectionStatus()
	{
		return connectionFailed;
	}
	
	public void saveData()
	{
		Serializer serialzier2 = new Serializer("alarm.xml");
		serialzier2.serialize(alarmXml);
	}
}
