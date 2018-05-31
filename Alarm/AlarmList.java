import java.sql.SQLException;
import java.util.ArrayList;

public class AlarmList 
{
	ArrayList<String> alarm;
	
	AlarmTable db;
	
	AlarmList() throws ClassNotFoundException, SQLException
	{
		alarm = new ArrayList<String>();
		db = new AlarmTable();
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
	
}
