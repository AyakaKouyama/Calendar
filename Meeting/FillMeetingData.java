
public class FillMeetingData 
{
	CalendarLogic calendarLogic;
	DataBase db;
	

	
	FillMeetingData(CalendarLogic calendarLogic)
	{
		this.calendarLogic = calendarLogic;
		db = new DataBase();
		db.connectToDataBase();
		
	}
	
	public void fillCalendar(int month, int year, int dayOfWeek, int daysInMonth)
	{
		int x = 0;
		
		for(int i = 0; i<13; i++)
		{
			for(int j = 0; j<7; j++)
			{
				
				if(i%2 == 0 && i != 0)
				{
					
				   if(!(i == 2 && j<(dayOfWeek - 1)) && x < daysInMonth)
				   {
					  x++;
				   }
				   
				   
					String value;
					String sId = Integer.toString(x) + Integer.toString(month) + Integer.toString((year % 1000));
					int id = Integer.parseInt(sId);
					
					
					if(db.getId(id) != -1)
					{
						String date = db.getDate(id).substring(10, 16);
						value = "Nazwa:" + db.getName(id) + "\nLokalizacja:" + db.getLocalization(id) + "\nGodzina:" + date + "\nSzczegó³y:" + db.getDetails(id);
					}
					else
					{
						value = null;
					}
					
					calendarLogic.setCellValue(value, i, j);
				}
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
}
