import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Klasa obs³uguj¹ca logikê kalendarza tj. wype³nianie komórek odopwiednimi
 * wartoœciami w zale¿noœci od wybranego miesi¹ca, roku oraz dostêpnych spotkañ.
 * Przekazuje informacje, które maj¹ zostaæ zapisane w bazie/pliku do klas
 * warstw ni¿szych.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
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
	{ "Pon", "Wto", "Œr", "Czw", "Pt", "Sob", "Niedz" };
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
	 * Metoda zwracj¹ca iloœæ dni w miesi¹cu, który jest aktualnie obs³ugiwany.
	 * 
	 * @return zwraca iloœæ dni w miesi¹cu, który aktualnie jest obs³ugiwany w
	 *         kalendarzu.
	 */
	public int getDays()
	{
		Calendar cal = new GregorianCalendar(window.getYear(), window.getMonth() - 1, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Metoda s³u¿¹ca do wype³nienia tablicy danymi wyœwietlanymi w komórkach tabeli
	 * kalendarza (nag³ówek, dni miesi¹ca, nazwy wydarzeñ).
	 * 
	 * @param year
	 *            obecnie obs³ugiwany rok przez kalendarz
	 * @param month
	 *            obecnie obs³ugiwany miesi¹c przez kalendarz
	 * @return tablica wype³niona danymi, które maj¹ zostaæ wyœwietlnone w oknie
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
	 * Metoda zwracaj¹ca rz¹d, w którym znajduje siê obecnie obs³ugiwany dzieñ.
	 * 
	 * @param day
	 *            obs³ugiwany dziêñ
	 * @return nr rzêdu, w którym znajduje siê dany dzieñ
	 */
	public int currentDayRow(int day)
	{
		return currentPosition[0];
	}

	/**
	 * Metoda zwracaj¹ca kolumnê, w której znajduje siê obecnie obs³ugiwany dzieñ.
	 * 
	 * @param day
	 *            obs³ugiwany dziêñ
	 * @return nr kolumny, w której znajduje siê dany dzieñ
	 */
	public int currentDayColumn(int day)
	{
		return currentPosition[1];
	}

	/**
	 * 
	 * @param i
	 *            rz¹d
	 * @param j
	 *            kolumna
	 * @return zwraca wartoœæ przechowywan¹ w tabeli, w rzêdzie i, kolumnie j.
	 */
	public Object getCellValue(int i, int j)
	{
		return data[i][j];
	}

	/**
	 * Metoda ustawiaj¹ca wartoœæ konkretnej pozycji w tabeli.
	 * 
	 * @param value
	 *            wstawiana wartoœæ
	 * @param i
	 *            rz¹d
	 * @param j
	 *            kolumna
	 */
	public void setCellValue(String value, int i, int j)
	{
		data[i][j] = value;
	}

	/**
	 * Metoda wywo³uj¹ca metody zapisuj¹ce nazwê spotkania do bazy.
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
	 * Metoda wywo³uj¹ca metody zapisuj¹ce nazwê spotkania do serializowanego
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
	 * Metoda wywo³uj¹ca metody zapisuj¹ce lokalizacje spotkania do bazy.
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
	 * Metoda wywo³uj¹ca metody zapisuj¹ce nazwê spotkania do serializowanego
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
	 * Metoda wywo³uj¹ca metody zapisuj¹ce datê spotkania do bazy.
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
	 * Metoda wywo³uj¹ca metody zapisuj¹ce datê spotkania do serializowanego
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
	 * Metoda wywo³uj¹ca metody zapisuj¹ce szczegó³y spotkania do bazy.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            szczdegó³y spotkania
	 */
	public void setDetails(int id, String value)
	{
		dbFill.setDetails(id, value);
	}

	/**
	 * Metoda wywo³uj¹ca metody zapisuj¹ce szczegó³y spotkania do serializowanego
	 * obiektu.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            szczegó³y spotkania
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
	 * Metoda wywo³uj¹ca metodê sprawdzaj¹c¹, czy dane ID wystêpuje w bazie.
	 * 
	 * @param id
	 *            id spotkania
	 * @return zwraca id w przypadku znalezienia id w bazie; zwraca -1 w przypadku,
	 *         gdy szukane id nie wystêpuje w bazie
	 */
	public int getId(int id)
	{
		return dbFill.getId(id);
	}

	/**
	 * Metoda wywo³ujaca odpowienie metody wstawiaj¹ce nowy rekord (spotkanie) do
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
	 *            szczegó³y spotkania
	 */
	public void insert(int id, String name, String localization, String date, String details)
	{
		dbFill.insert(id, name, localization, date, details);
	}

	/**
	 * Metoda wywo³uj¹ca metody s³u¿¹ce do usuniêcia spotkania z
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
	 * Metoda wyow³uj¹ca metodê usuwaj¹c¹ spotkanie z listy.
	 * 
	 * @param id
	 *            id spotkania
	 */
	public void deleteMeetingFromList(int id)
	{
		dbFill.deleteMeetingFromList(id);
	}

	/**
	 * Metoda wyow³uj¹ca metody s³u¿¹ce do dodania elementu do listy spotkañ.
	 * 
	 * @param id
	 *            id spotkania
	 * @param value
	 *            ³añcuch znaków opisuj¹cy spotkanie
	 */
	public void AddElementToList(int id, String value)
	{
		dbFill.addToDictionary(id, value);
	}

	/**
	 * Meotda wywo³uj¹ca metodê dodaj¹c¹ nazwê spotkania do listy.
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
	 * Metoda zwracaj¹ca mapê wszystkich spotkañ.
	 * 
	 * @return zwraca mapê wszystkich spotkañ
	 */
	public HashMap<Integer, MeetingObject> getObjectMeetings()
	{
		return meetingObject;
	}

	/**
	 * Metoda zwracaj¹ca wartoœæ liczbow¹ aktualnego trubu pracy aplikacji.
	 * 
	 * @return zwraca wartoœæ liczbow¹ trybu w jakim pracuje aplikcja. 1 - "XML", 2
	 *         - "Baza dancyh"
	 */
	public int getMode()
	{
		return mode;
	}

	/**
	 * Metoda ustawiaj¹ca tryb pracy aplikacji.
	 * 
	 * @param value
	 *            wartoœæ liczbowa ustawianego trybu pracy
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
	 * Metoda zwracaj¹ca obiekt typu FillMeetingData.
	 * @return obiekt typu FilleMeetingData
	 */
	public FillMeetingData getFillMeetingData()
	{
		return dbFill;
	}

	/**
	 * Metoda zapisuj¹ca stan aplikacji.
	 */
	public void saveData()
	{
		Serializer serializer = new Serializer("meeting.xml");
		serializer.serialize(meetingObject);
	}

}
