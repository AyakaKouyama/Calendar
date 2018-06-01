import java.awt.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
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
	 * Konstruktor kalsy; Tworzy obiekt MeetingTable. W przypadku nieudanej pr�by
	 * po��czenia z baz� danych zmienia tryb pracy aplikacji na "XML"
	 * 
	 * @param calendarLogic
	 *            obiekt obs�uguj�cy logik� kalendarza
	 * @param mode
	 *            tryb pracy aplikacji
	 * @param list
	 *            mapa wszystkich spotka�
	 * @param allIds
	 *            lista wszystkich ID spotka�
	 * @param showBar
	 *            warto�� logiczna determinuj�ca czy pokazany ma zosta� pasek
	 *            post�pu podczas pr�by po��czenia z baz� danycyh
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
	 * Metoda wype�niaj�ca list� spotka� danymi z bazy lub pliku XML (w zale�no�ci od trybu)
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
						+ date + "\nSzczeg�y:" + db.getDetails(id);
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
						+ "\nSzczeg�y:" + meetingObject.get(id).getDetails();
				meeting.put(id, value);
				names.put(id, meetingObject.get(id).getName());
			}

		}
	}

	/**
	 * 
	 * @return zwraca map� wszystkich spotka�
	 */
	public Map<Integer, String> getMap()
	{
		return meeting;
	}

	/**
	 * Wype�nia kom�rk� kalendarza nazw� spotkania w danym dniu.
	 * @param month miesi�c
	 * @param year rok 
	 * @param x dzie�
	 * @param row rz�d w tabeli
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
	 * Meteoda aktualizuj�ca nazw� spotkania danego dnia w bazie danych (na podstawie id spotkania).
	 * @param id id spotkania
	 * @param value nazwa spotkania
	 */
	public void setName(int id, String value)
	{
		db.setName(id, value);
	}

	/**
	 * Meteoda aktualizuj�ca lokalizacje spotkania danego dnia w bazie danych (na podstawie id spotkania).
	 * @param id id spotkania
	 * @param value lokalizacja spotkania
	 */
	public void setLocalization(int id, String value)
	{
		db.setLocalization(id, value);
	}

	/**
	 * Meteoda aktualizuj�ca dat� spotkania danego dnia w bazie danych (na podstawie id spotkania).
	 * @param id id spotkania
	 * @param value data spotkania
	 */
	public void setDate(int id, String value)
	{
		db.setDate(id, value);
	}

	/**
	 * Meteoda aktualizuj�ca szczeg�y spotkania danego dnia w bazie danych (na podstawie id spotkania).
	 * @param id id spotkania
	 * @param value szczeg�y spotkania
	 */
	public void setDetails(int id, String value)
	{
		db.setDetails(id, value);
	}

	/**
	 * Metoda sprawdzaj�ca czy dane id wyst�puje w bazie dancyh.
	 * @param id szukane id spotkania
	 * @return zwraca id w przypadku znalzienia id w bazie danych; zwraca -1 w przypadku, gdy szukane id nie wyst�puje w bazie
	 */
	public int getId(int id)
	{
		return db.getId(id);
	}

	/**
	 * Metoda wstawiaj�ca nowe spotkanie do bazy danych.
	 * @param id id spotkania
	 * @param name nazwa spotkania
	 * @param localization lokalizacja spotkania
	 * @param date data spotkania
	 * @param details szczeg�y spotkania
	 */
	public void insert(int id, String name, String localization, String date, String details)
	{
		db.insert(id, name, localization, date, details);
	}

	/**
	 * Metoda usuwaj�ca spotkanie z bazy/serializowanego obiektu (w zale�no�ci od trubu).
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
	 * Metoda usuwaj�ca spotkanie z mapy spotka�.
	 * @param id id spotkania
	 */
	public void deleteMeetingFromList(int id)
	{
		meeting.remove(id);
	}

	/**
	 * Metoda dodaj�ca nowe spotkanie do mapy spotka�.
	 * @param id id spotkania
	 * @param value �a�cuch zankowy opisuj�cy spotkanie
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
	 * Metoda dodaj�ca now� nazw� spotkania do listy nazw sptoka�.
	 * @param id id spotkania  
	 * @param value nazwa spotkania
	 */
	public void addName(int id, String value)
	{
		names.put(id, value);
	}

	/**
	 * 
	 * @return zwraca list� wszystkich dost�pnych id spotka�
	 */
	public ArrayList<Integer> getAllIDs()
	{
		if (mode == 1)
			return db.getAllIds();
		else return xmlIds;
	}
}
