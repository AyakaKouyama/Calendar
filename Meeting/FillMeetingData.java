import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FillMeetingData
{
	CalendarLogic calendarLogic;
	MeetingTable db;
	Map<Integer, String> meeting;
	Map<Integer, String> names;
	int mode;
	HashMap<Integer, MeetingObject> meetingObject;
	ArrayList<Integer> xmlIds;

	FillMeetingData(CalendarLogic calendarLogic, int mode, HashMap<Integer, MeetingObject> list, ArrayList<Integer> allIds) throws ClassNotFoundException, SQLException
	{
		this.calendarLogic = calendarLogic;
		this.mode = mode;
		db = new MeetingTable();
		meetingObject = list;
		meeting = new HashMap<Integer, String>();
		names = new HashMap<Integer, String>();
		xmlIds = allIds;
		fillMeetingsDictionary();
	}

	FillMeetingData() throws ClassNotFoundException, SQLException
	{
		db = new MeetingTable();
		meeting = new HashMap<Integer, String>();
		fillMeetingsDictionary();
	}

	public void fillMeetingsDictionary()
	{

		if (mode == 1)
		{
			ArrayList<Integer> ids = db.getAllIds();
			for (int i = 0; i < ids.size(); i++)
			{
				int id = ids.get(i);
				String date = db.getDate(id).substring(10, 16);
				String value = "Nazwa:" + db.getName(id) + "\nLokalizacja:" + db.getLocalization(id) + "\nGodzina:"
						+ date + "\nSzczegó³y:" + db.getDetails(id);
				meeting.put(id, value);
				names.put(id, db.getName(id));
			}
		}
		if (mode == 2)
		{

			for (int i = 0; i < xmlIds.size(); i++)
			{
				int id = xmlIds.get(i);
				String value = "Nazwa:" + meetingObject.get(id).getName() + "\nLokalizacja:"
						+ meetingObject.get(id).getLocalization() + "\nGodzina:" + meetingObject.get(id).getTime()
						+ "\nSzczegó³y:" + meetingObject.get(id).getDetails();
				meeting.put(id, value);
				names.put(id, meetingObject.get(id).getName());
			}

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

		if (meeting.get(id) != null)
		{
			if (mode == 1)
			{
				calendarLogic.setCellValue(names.get(id), row + 1, column);
			}
			if (mode == 2)
			{
				calendarLogic.setCellValue(names.get(id), row + 1, column);
			}
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
		if (mode == 2)
			xmlIds.add(id);
	}

	public void addName(int id, String value)
	{
		names.put(id, value);
	}

	public ArrayList<Integer> getAllIDs()
	{
		if (mode == 1)
			return db.getAllIds();
		else
			return xmlIds;
	}

}
