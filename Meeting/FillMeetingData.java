import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FillMeetingData 
{
	CalendarLogic calendarLogic;
	MeetingTable db;
	Map<Integer, String> meeting;

	
	FillMeetingData(CalendarLogic calendarLogic)
	{
		this.calendarLogic = calendarLogic;
		db = new MeetingTable();
		meeting = new HashMap<Integer, String>();
		fillMeetingsDictionary();
	}
	
	FillMeetingData()
	{
		db = new MeetingTable();
		meeting = new HashMap<Integer, String>();
		fillMeetingsDictionary();
	}
	
	
	public void fillMeetingsDictionary()
	{
		System.out.println(this);
		ArrayList<Integer> ids = db.getAllIds();
		for(int i = 0; i<ids.size(); i++)
		{
			int id = ids.get(i);
			String date = db.getDate(id).substring(10, 16);
			String value = "Nazwa:" + db.getName(id) + "\nLokalizacja:" + db.getLocalization(id) + "\nGodzina:" + date + "\nSzczegó³y:" + db.getDetails(id);
			meeting.put(id, value);
		}
	}
	
	public Map<Integer, String> getMap()
	{
		return meeting;
	}
	
	public void fillCalendar(int month, int year, int dayOfWeek, int daysInMonth, int x, int row, int column)
	{	   
	    String sId = Integer.toString(x) + Integer.toString(month) + Integer.toString((year % 1000));
	    int id = Integer.parseInt(sId);
	      
	    if(meeting.get(id) != null)
		{
	    	System.out.println(id);
		  calendarLogic.setCellValue(meeting.get(id), row + 1, column);
		}
		
	}
	
	public void setName(int id, String value)
	{
		db.setName(id, value);
	}
	
	public void setLocalization(int id, String value)
	{
		db.setLocalization(id, value);
	}
	
	public void setDate(int id, String value)
	{
		db.setDate(id, value);
	}
	
	public void setDetails(int id, String value)
	{
		db.setDetails(id, value);
	}
	
	public int getId(int id)
	{
		return db.getId(id);
	}
	
	public void insert(int id, String name, String localization, String date, String details)
	{
		db.insert(id, name, localization, date, details);
	}
	
	public void deleteMeeting(int id)
	{
		db.deleteMeeting(id);
	}
	
	public void deleteMeetingFromList(int id)
	{
		meeting.remove(id);
	}
	
	public void addToDictionary(int id, String value)
	{
		meeting.put(id, value);
	}
	
	public ArrayList<Integer> getAllIDs()
	{
		return db.getAllIds();
	}
	
}
