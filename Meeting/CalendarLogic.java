import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class CalendarLogic
{

	private Object[][] data =
	{
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null }, };
	
	private String[] columnNames = { "Pon", "Wto", "Œr", "Czw", "Pt", "Sob", "Niedz" };
	private int[] currentPosition = { -1, -1 };
	private int dayOfWeek;
	private int mode;
	
	private FillMeetingData dbFill;
	private HashMap<Integer, MeetingObject> meetingObject;
	private ArrayList<Integer> allIds;
	
	private CalendarWindow window;
	
	CalendarLogic(CalendarWindow window) 
	{
		this.window = window;
		MeetingObjectList list = new MeetingObjectList();
		meetingObject = list.deserializeList();
		allIds = list.getAllIds();
		mode = window.getMode();

		if (meetingObject != null)
		{
			dbFill = new FillMeetingData(this, mode, meetingObject, allIds, true);
		} 
		else
		{
			meetingObject = new HashMap<Integer, MeetingObject>();
			allIds = new ArrayList<Integer>();
			dbFill = new FillMeetingData(this, mode, meetingObject, allIds, true);
		}
	}

	public int getDays()
	{
		Calendar cal = new GregorianCalendar(window.getYear(), window.getMonth() - 1, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public Object[][] fillCalendar(int year, int month)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		if (dayOfWeek == 0)
		{
			dayOfWeek = 7;
		}

		for (int i = 0; i < 7; i++)
		{
			data[0][i] = columnNames[i];
		}

		int x = 1;
		for (int i = 1; i < 13; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				if (i % 2 == 1)
				{
					if (x == window.getCurrentDay())
					{
						currentPosition[0] = i;
						currentPosition[1] = j;
					}
					if (i == 1 && j < (dayOfWeek - 1))
					{
						data[i][j] = null;
					} else
					{
						if (x <= getDays())
						{
							data[i][j] = x;
							dbFill.fillCalendar(month, year, dayOfWeek, getDays(), x, i, j);
							x++;
						} else
						{
							data[i][j] = null;
						}
					}
				}
			}
		}

		return data;
	}

	public int currentDayRow(int day)
	{
		return currentPosition[0];
	}

	public int currentDayColumn(int day)
	{
		return currentPosition[1];
	}

	public Object getData()
	{
		return data;
	}

	public void setObject(String value, int i, int j)
	{
		data[i][j] = value;
	}

	public Object getCellValue(int i, int j)
	{
		return data[i][j];
	}

	public void setCellValue(String value, int i, int j)
	{
		data[i][j] = value;
	}

	public void setName(int id, String value)
	{
		dbFill.setName(id, value);
	}

	public void setNameXml(int id, String value)
	{

		if (meetingObject.get(id) != null)
		{
			meetingObject.get(id).setName(value);
		} else
		{
			meetingObject.put(id, new MeetingObject());
			meetingObject.get(id).setName(value);
		}
	}

	public void setLocalization(int id, String value)
	{
		dbFill.setLocalization(id, value);
	}

	public void setLocalizationXml(int id, String value)
	{
		if (meetingObject.get(id) != null)
		{
			meetingObject.get(id).setLocalization(value);
		} else
		{
			meetingObject.put(id, new MeetingObject());
			meetingObject.get(id).setLocalization(value);
		}
	}
	

	public void setDate(int id, String value)
	{
		dbFill.setDate(id, value);
	}

	public void setDateXml(int id, String value)
	{
		if (meetingObject.get(id) != null)
		{
			meetingObject.get(id).setTime(value);
		} else
		{
			meetingObject.put(id, new MeetingObject());
			meetingObject.get(id).setTime(value);
		}

	}

	public void setDetails(int id, String value)
	{
		dbFill.setDetails(id, value);
	}

	public void setDetailsXml(int id, String value)
	{

		if (meetingObject.get(id) != null)
		{
			meetingObject.get(id).setDetails(value);
		} else
		{
			meetingObject.put(id, new MeetingObject());
			meetingObject.get(id).setDetails(value);
		}

	}

	public int getId(int id)
	{
		return dbFill.getId(id);
	}

	public void insert(int id, String name, String localization, String date, String details)
	{
		dbFill.insert(id, name, localization, date, details);
	}

	public void deleteMeeting(int id)
	{
		if(mode == 1)
			dbFill.deleteMeeting(id);
		else
			meetingObject.remove(id);
	}

	public void deleteMeetingFromList(int id)
	{
		dbFill.deleteMeetingFromList(id);
	}

	public void AddElementToList(int id, String value)
	{
		dbFill.addToDictionary(id, value);
	}

	public void addNameToList(int id, String value)
	{
		dbFill.addName(id, value);
	}

	public FillMeetingData getDB()
	{
		return dbFill;
	}

	public HashMap<Integer, MeetingObject> getObjectMeetings()
	{
		return meetingObject;
	}

	public int getMode()
	{
		return mode;
	}

	public void setMode(int value) throws ClassNotFoundException, SQLException
	{
		mode = value;
		dbFill = new FillMeetingData(this, mode, meetingObject, allIds, false);
	}
	
	public FillMeetingData getFillMeetingData()
	{
		return dbFill;
	}

}
