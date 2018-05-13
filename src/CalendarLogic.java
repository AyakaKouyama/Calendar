import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarLogic 
{
	
	Object[][] data = {
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		};
	int[] currentPosition = {-1, -1};
	
	DateFormat date;
	Window window;
	int daysInMonth;
	int dayOfWeek;
	FillMeetingData dbFill;
	
	CalendarLogic(Window window)
	{
		this.window = window;
		dbFill = new FillMeetingData(this);
	}
	
	CalendarLogic()
	{
		
	}
	
	public int getDays()
	{
		if(window.getMonth() == 1 || window.getMonth() == 3 || window.getMonth() == 5 || window.getMonth() == 7
		  || window.getMonth() == 8 || window.getMonth() == 10 || window.getMonth() == 12)
		{
			daysInMonth = 31;
		}
		else
		{
			if(window.getYear()%4 == 0 && window.getMonth() == 2)
			{
				daysInMonth = 29;
			}
			else if(window.getMonth() == 2)
			{
				daysInMonth = 28;
			}
			else
			{
				daysInMonth = 30;
			}
		}
		
		return daysInMonth;	
	}
	
	public Object[][] fillCalendar()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(window.getYear(), window.getMonth() - 1, 1);
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
		if(dayOfWeek == 0)
		{
			dayOfWeek = 7;
		}
		
		int x = 1;
		for(int i = 1; i<13; i++)
		{
			for(int j = 0; j<7; j++)
			{
				if(i%2 == 1)
				{
					if(x == window.getDay())
					{
						currentPosition[0] = i;
						currentPosition[1] = j;
					}
					if(i == 1 && j<(dayOfWeek - 1))
					{
						data[i][j] = null;
					}
					else
					{
						if(x <= getDays())
						{
							data[i][j] = x;
							x++;
						}
						else
						{
							data[i][j] = null;
						}
					}	
				}
			}
		}
		
		dbFill.fillCalendar(window.getMonth(), window.getYear(), dayOfWeek, getDays());
		
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
	
	public String getCellValue(int i, int j)
	{
		return (String)data[i][j];
	}

	public void setCellValue(String value, int i, int j)
	{
		data[i][j] = value;
	}
	public void setName(int id, String value)
	{
		dbFill.setName(id, value);
	}
	
	public void setLocalization(int id, String value)
	{
		dbFill.setLocalization(id, value);
	}
	
	public void setDate(int id, String value)
	{
		dbFill.setDate(id, value);
	}
	
	public void setDetails(int id, String value)
	{
		dbFill.setDetails(id, value);
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
		dbFill.deleteMeeting(id);
	}
	
}
