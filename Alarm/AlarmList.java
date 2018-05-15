import java.util.ArrayList;

public class AlarmList 
{
	ArrayList<String> alarm;
	ArrayList<Boolean> init;
	
	AlarmTable db;
	
	AlarmList()
	{
		alarm = new ArrayList<String>();
		init = new ArrayList<Boolean>();
		db = new AlarmTable();
		fillInit();
	}
	
	public void fillInit()
	{
		ArrayList<Integer> ids = db.getAllIds();
		for(int i = 0; i<ids.size(); i++)
		{
			init.add(false);
		}
	}
	
	public void fill()
	{
		ArrayList<Integer> ids = db.getAllIds();
		
		for(int i = 0; i<ids.size(); i++)
		{
			alarm.add(db.getDate(ids.get(i)));
		}
		
	}
	
	public void addAlarm(String value)
	{
		init.add(false);
		alarm.add(value);
	}
	
	public ArrayList<String> getList()
	{
		return alarm;
	}
	
	public void addAlarmToDB(String value)
	{
		ArrayList<Integer> ids = db.getAllIds();
		db.insertDate(ids.size() + 1, value);
	}
	
	public ArrayList<Boolean> getInit()
	{
		return init;
	}
	
	public void update(int id, String value)
	{
		db.setDate(id, value);
	}
	
	public void updateList(int id, String value)
	{
		alarm.set(id, value);
	}
	
	public void removeAlarm(String value)
	{
		db.remove(value);
	}
}
