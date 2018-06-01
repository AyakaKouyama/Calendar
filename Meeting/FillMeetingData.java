import java.awt.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class FillMeetingData
{
	private Map<Integer, String> meeting;
	private Map<Integer, String> names;
	private HashMap<Integer, MeetingObject> meetingObject;
	private ArrayList<Integer> xmlIds;

	private int mode;
	private CalendarLogic calendarLogic;
	private MeetingTable db;
	private ProgressBar bar;
	private boolean connectionFailed = false;

	/**
	 * Konstruktor kalsy; Tworzy obiekt MeetingTable. W przypadku nieudanej próby
	 * po³¹czenia z baz¹ danych zmienia tryb pracy aplikacji na "XML"
	 * 
	 * @param calendarLogic
	 *            obiekt obs³uguj¹cy logikê kalendarza
	 * @param mode
	 *            tryb pracy aplikacji
	 * @param list
	 *            mapa wszystkich spotkañ
	 * @param allIds
	 *            lista wszystkich ID spotkañ
	 * @param showBar
	 *            wartoœæ logiczna determinuj¹ca czy pokazany ma zostaæ pasek
	 *            postêpu podczas próby po³¹czenia z baz¹ danycyh
	 */
	public FillMeetingData(CalendarLogic calendarLogic, int mode, HashMap<Integer, MeetingObject> list,
			ArrayList<Integer> allIds, boolean showBar)
	{
		this.calendarLogic = calendarLogic;
		this.mode = mode;
		bar = new ProgressBar();
		if (showBar == true) bar.show();

		try
		{
			db = new MeetingTable();
		} catch (ClassNotFoundException | SQLException e)
		{
			mode = 2;
			connectionFailed = true;
			bar.close();

			ConnectionError error = new ConnectionError();
			error.show(null);

			meetingObject = list;
			meeting = new HashMap<Integer, String>();
			names = new HashMap<Integer, String>();
			xmlIds = allIds;

			fillMeetingsDictionary();
		}

		bar.close();
		meetingObject = list;
		meeting = new HashMap<Integer, String>();
		names = new HashMap<Integer, String>();
		xmlIds = allIds;

		fillMeetingsDictionary();
	}

	/**
	 * Metoda wype³niaj¹ca listê spotkañ danymi z bazy lub pliku XML (w zale¿noœci od trybu)
	 */
	public void fillMeetingsDictionary()
	{
		mode = connectionFailed == true ? 2 : mode;

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

	/**
	 * 
	 * @return zwraca mapê wszystkich spotkañ
	 */
	public Map<Integer, String> getMap()
	{
		return meeting;
	}

	/**
	 * Wype³nia komórkê kalendarza nazw¹ spotkania w danym dniu.
	 * @param month miesi¹c
	 * @param year rok 
	 * @param x dzieñ
	 * @param row rz¹d w tabeli
	 * @param column kolumna w tabeli
	 */
	public void fillCalendar(int month, int year, int x, int row, int column)
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

	/**
	 * Meteoda aktualizuj¹ca nazwê spotkania danego dnia w bazie danych (na podstawie id spotkania).
	 * @param id id spotkania
	 * @param value nazwa spotkania
	 */
	public void setName(int id, String value)
	{
		db.setName(id, value);
	}

	/**
	 * Meteoda aktualizuj¹ca lokalizacje spotkania danego dnia w bazie danych (na podstawie id spotkania).
	 * @param id id spotkania
	 * @param value lokalizacja spotkania
	 */
	public void setLocalization(int id, String value)
	{
		db.setLocalization(id, value);
	}

	/**
	 * Meteoda aktualizuj¹ca datê spotkania danego dnia w bazie danych (na podstawie id spotkania).
	 * @param id id spotkania
	 * @param value data spotkania
	 */
	public void setDate(int id, String value)
	{
		db.setDate(id, value);
	}

	/**
	 * Meteoda aktualizuj¹ca szczegó³y spotkania danego dnia w bazie danych (na podstawie id spotkania).
	 * @param id id spotkania
	 * @param value szczegó³y spotkania
	 */
	public void setDetails(int id, String value)
	{
		db.setDetails(id, value);
	}

	/**
	 * Metoda sprawdzaj¹ca czy dane id wystêpuje w bazie dancyh.
	 * @param id szukane id spotkania
	 * @return zwraca id w przypadku znalzienia id w bazie danych; zwraca -1 w przypadku, gdy szukane id nie wystêpuje w bazie
	 */
	public int getId(int id)
	{
		return db.getId(id);
	}

	/**
	 * Metoda wstawiaj¹ca nowe spotkanie do bazy danych.
	 * @param id id spotkania
	 * @param name nazwa spotkania
	 * @param localization lokalizacja spotkania
	 * @param date data spotkania
	 * @param details szczegó³y spotkania
	 */
	public void insert(int id, String name, String localization, String date, String details)
	{
		db.insert(id, name, localization, date, details);
	}

	/**
	 * Metoda usuwaj¹ca spotkanie z bazy/serializowanego obiektu (w zale¿noœci od trubu).
	 * @param id id usuwanego spotkania
	 */
	public void deleteMeeting(int id)
	{
		if (mode == 1)
		{
			db.deleteMeeting(id);
		} else
		{
			meetingObject.remove(id);
		}

	}

	/**
	 * Metoda usuwaj¹ca spotkanie z mapy spotkañ.
	 * @param id id spotkania
	 */
	public void deleteMeetingFromList(int id)
	{
		meeting.remove(id);
	}

	/**
	 * Metoda dodaj¹ca nowe spotkanie do mapy spotkañ.
	 * @param id id spotkania
	 * @param value ³añcuch zankowy opisuj¹cy spotkanie
	 */
	public void addToDictionary(int id, String value)
	{
		if (mode == 1)
			meeting.put(id, value);
		else
		{
			meeting.put(id, value);
			xmlIds.add(id);
		}

	}

	/**
	 * Metoda dodaj¹ca now¹ nazwê spotkania do listy nazw sptokañ.
	 * @param id id spotkania  
	 * @param value nazwa spotkania
	 */
	public void addName(int id, String value)
	{
		names.put(id, value);
	}

	/**
	 * 
	 * @return zwraca listê wszystkich dostêpnych id spotkañ
	 */
	public ArrayList<Integer> getAllIDs()
	{
		if (mode == 1)
			return db.getAllIds();
		else return xmlIds;
	}
}
