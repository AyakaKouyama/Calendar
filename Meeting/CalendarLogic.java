import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Klasa obs�uguj�ca logik� kalendarza tj. wype�nianie kom�rek odopwiednimi
 * warto�ciami w zale�no�ci od wybranego miesi�ca, roku oraz dost�pnych spotka�.
 * Przekazuje informacje, kt�re maj� zosta� zapisane w bazie/pliku do klas
 * warstw ni�szych.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
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

	private String[] columnNames =
	{ "Pon", "Wto", "�r", "Czw", "Pt", "Sob", "Niedz" };
	private int[] currentPosition =
	{ -1, -1 };
	private int dayOfWeek;
	private int mode;

	private FillMeetingData dbFill;
	private HashMap<Integer, MeetingObject> meetingObject;
	private ArrayList<Integer> allIds;

	private CalendarWindow window;

	/**
	 * Konstruktor klasy.Tworzy odpowiednie obiekty potrzebne do pracy aplikacji.
	 * 
	 * @param window
	 *            okno rodzic
	 */
	public CalendarLogic(CalendarWindow window)
	{
		this.window = window;
		MeetingObjectList list = new MeetingObjectList();
		meetingObject = list.deserializeList();
		allIds = list.getAllIds();
		mode = window.getMode();

		if (meetingObject != null)
		{
			dbFill = new FillMeetingData(this, mode, meetingObject, allIds, true);
		} else
		{
			meetingObject = new HashMap<Integer, MeetingObject>();
			allIds = new ArrayList<Integer>();
			dbFill = new FillMeetingData(this, mode, meetingObject, allIds, true);
		}
	}

	/**
	 * Metoda zwracj�ca ilo�� dni w miesi�cu, kt�ry jest aktualnie obs�ugiwany.
	 * 
	 * @return zwraca ilo�� dni w miesi�cu, kt�ry aktualnie jest obs�ugiwany w
	 *         kalendarzu.
	 */
	public int getDays()
	{
		Calendar cal = new GregorianCalendar(window.getYear(), window.getMonth() - 1, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Metoda s�u��ca do wype�nienia tablicy danymi wy�wietlanymi w kom�rkach tabeli
	 * kalendarza (nag��wek, dni miesi�ca, nazwy wydarze�).
	 * 
	 * @param year
	 *            obecnie obs�ugiwany rok przez kalendarz
	 * @param month
	 *            obecnie obs�ugiwany miesi�c przez kalendarz
	 * @return tablica wype�niona danymi, kt�re maj� zosta� wy�wietlnone w oknie
	 *         kalendarza
	 */
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
							dbFill.fillCalendar(month, year, x, i, j);
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

	/**
	 * Metoda zwracaj�ca rz�d, w kt�rym znajduje si� obecnie obs�ugiwany dzie�.
	 * 
	 * @param day
	 *            obs�ugiwany dzi��
	 * @return nr rz�du, w kt�rym znajduje si� dany dzie�
	 */
	public int currentDayRow(int day)
	{
		return currentPosition[0];
	}

	/**
	 * Metoda zwracaj�ca kolumn�, w kt�rej znajduje si� obecnie obs�ugiwany dzie�.
	 * 
	 * @param day
	 *            obs�ugiwany dzi��
	 * @return nr kolumny, w kt�rej znajduje si� dany dzie�
	 */
	public int currentDayColumn(int day)
	{
		return currentPosition[1];
	}

	/**
	 * 
	 * @param i
	 *            rz�d
	 * @param j
	 *            kolumna
	 * @return zwraca warto�� przechowywan� w tabeli, w rz�dzie i, kolumnie j.
	 */
	public Object getCellValue(int i, int j)
	{
		return data[i][j];
	}

	/**
	 * Metoda ustawiaj�ca warto�� konkretnej pozycji w tabeli.
	 * 
	 * @param value
	 *            wstawiana warto��
	 * @param i
	 *            rz�d
	 * @param j
	 *            kolumna
	 */
	public void setCellValue(String value, int i, int j)
	{
		data[i][j] = value;
	}

	/**
	 * Metoda wywo�uj�ca metody zapisuj�ce nazw� spotkania do bazy.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            nazwa spotkania
	 */
	public void setName(int id, String value)
	{
		dbFill.setName(id, value);
	}

	/**
	 * Metoda wywo�uj�ca metody zapisuj�ce nazw� spotkania do serializowanego
	 * obiektu.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            nazwa spotkania
	 */
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

	/**
	 * Metoda wywo�uj�ca metody zapisuj�ce lokalizacje spotkania do bazy.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            lokalizacja spotkania
	 */
	public void setLocalization(int id, String value)
	{
		dbFill.setLocalization(id, value);
	}

	/**
	 * Metoda wywo�uj�ca metody zapisuj�ce nazw� spotkania do serializowanego
	 * obiektu.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            lokalizacja spotkania
	 */
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

	/**
	 * Metoda wywo�uj�ca metody zapisuj�ce dat� spotkania do bazy.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            data spotkania
	 */
	public void setDate(int id, String value)
	{
		dbFill.setDate(id, value);
	}

	/**
	 * Metoda wywo�uj�ca metody zapisuj�ce dat� spotkania do serializowanego
	 * obiektu.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            data spotkania
	 */
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

	/**
	 * Metoda wywo�uj�ca metody zapisuj�ce szczeg�y spotkania do bazy.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            szczdeg�y spotkania
	 */
	public void setDetails(int id, String value)
	{
		dbFill.setDetails(id, value);
	}

	/**
	 * Metoda wywo�uj�ca metody zapisuj�ce szczeg�y spotkania do serializowanego
	 * obiektu.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            szczeg�y spotkania
	 */
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

	/**
	 * Metoda wywo�uj�ca metod� sprawdzaj�c�, czy dane ID wyst�puje w bazie.
	 * 
	 * @param id
	 *            id spotkania
	 * @return zwraca id w przypadku znalezienia id w bazie; zwraca -1 w przypadku,
	 *         gdy szukane id nie wyst�puje w bazie
	 */
	public int getId(int id)
	{
		return dbFill.getId(id);
	}

	/**
	 * Metoda wywo�ujaca odpowienie metody wstawiaj�ce nowy rekord (spotkanie) do
	 * bazy danych.
	 * 
	 * @param id
	 *            id spotkania
	 * @param name
	 *            nazwa spotkania
	 * @param localization
	 *            lokalizacja spotkania
	 * @param date
	 *            data spotkania
	 * @param details
	 *            szczeg�y spotkania
	 */
	public void insert(int id, String name, String localization, String date, String details)
	{
		dbFill.insert(id, name, localization, date, details);
	}

	/**
	 * Metoda wywo�uj�ca metody s�u��ce do usuni�cia spotkania z
	 * bazy/serializowanego obiektu.
	 * 
	 * @param id
	 *            id spotkania
	 */
	public void deleteMeeting(int id)
	{
		if (mode == 1)
			dbFill.deleteMeeting(id);
		else meetingObject.remove(id);
	}

	/**
	 * Metoda wyow�uj�ca metod� usuwaj�c� spotkanie z listy.
	 * 
	 * @param id
	 *            id spotkania
	 */
	public void deleteMeetingFromList(int id)
	{
		dbFill.deleteMeetingFromList(id);
	}

	/**
	 * Metoda wyow�uj�ca metody s�u��ce do dodania elementu do listy spotka�.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            �a�cuch znak�w opisuj�cy spotkanie
	 */
	public void AddElementToList(int id, String value)
	{
		dbFill.addToDictionary(id, value);
	}

	/**
	 * Meotda wywo�uj�ca metod� dodaj�c� nazw� spotkania do listy.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            nazwa spotkania
	 */
	public void addNameToList(int id, String value)
	{
		dbFill.addName(id, value);
	}

	/**
	 * Metoda zwracaj�ca map� wszystkich spotka�.
	 * 
	 * @return zwraca map� wszystkich spotka�
	 */
	public HashMap<Integer, MeetingObject> getObjectMeetings()
	{
		return meetingObject;
	}

	/**
	 * Metoda zwracaj�ca warto�� liczbow� aktualnego trubu pracy aplikacji.
	 * 
	 * @return zwraca warto�� liczbow� trybu w jakim pracuje aplikcja. 1 - "XML", 2
	 *         - "Baza dancyh"
	 */
	public int getMode()
	{
		return mode;
	}

	/**
	 * Metoda ustawiaj�ca tryb pracy aplikacji.
	 * 
	 * @param value
	 *            warto�� liczbowa ustawianego trybu pracy
	 * @throws ClassNotFoundException
	 *             <a href=
	 *             "https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html">https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html</a>
	 * @throws SQLException
	 *             <a href=
	 *             "https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html">https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html</a>
	 * 
	 */
	public void setMode(int value) throws ClassNotFoundException, SQLException
	{
		mode = value;
		dbFill = new FillMeetingData(this, mode, meetingObject, allIds, false);
	}

	/**
	 * Metoda zwracaj�ca obiekt typu FillMeetingData.
	 * @return obiekt typu FilleMeetingData
	 */
	public FillMeetingData getFillMeetingData()
	{
		return dbFill;
	}

	/**
	 * Metoda zapisuj�ca stan aplikacji.
	 */
	public void saveData()
	{
		Serializer serializer = new Serializer("meeting.xml");
		serializer.serialize(meetingObject);
	}

}
