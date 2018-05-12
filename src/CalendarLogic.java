import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarLogic 
{
//	Object[][] data = {
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//		    {"", "", "", "", "", "", ""},
//
//		};
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
	
	CalendarLogic(Window window)
	{
		this.window = window;
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
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
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
	
}
