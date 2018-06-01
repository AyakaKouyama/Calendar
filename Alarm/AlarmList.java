import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa zawieraj¹ca listê wszystkich alarmów pobranych z bazy danych i
 * manipuluj¹ca na tych danych.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class AlarmList
{
	private ArrayList<String> alarm;
	private ArrayList<AlarmObject> alarmXml;
	private AlarmTable db;
	private ProgressBar bar;
	private int mode;
	private boolean connectionFailed = false;

	/**
	 * Konstruktor klasy.Inicjalizuje obiekiety i wype³nia listê.
	 * 
	 * @param mode
	 *            tryb pracy aplikacji
	 * @param showBar
	 *            wartoœæ determinuj¹ca czy ma zostaæ pokazany pasek postêpu
	 *            po³¹czenia z baz¹
	 */
	AlarmList(int mode, boolean showBar)
	{
		this.mode = mode;
		alarm = new ArrayList<String>();
		AlarmObjectList alarmsXml = new AlarmObjectList();
		bar = new ProgressBar();
		alarmXml = alarmsXml.deserializeList();

		if (showBar == true) bar.show();

		try
		{
			db = new AlarmTable();
		} catch (ClassNotFoundException | SQLException e)
		{
			mode = 2;
			connectionFailed = true;
			bar.close();
			fill();
		}
		bar.close();
		fill();
	}

	/**
	 * Metoda wype³niaj¹ca listê danymi z bazy/pliku XML (w zale¿noœci od trybu).
	 */
	public void fill()
	{
		mode = connectionFailed == true ? 2 : mode;
		if (mode == 1)
		{
			ArrayList<Integer> ids = db.getAllIds();

			for (int i = 0; i < ids.size(); i++)
			{
				alarm.add(db.getDate(ids.get(i)));
			}
		} else
		{
			if (alarmXml != null)
			{
				for (int i = 0; i < alarmXml.size(); i++)
				{
					alarm.add(alarmXml.get(i).getDate());
				}
			} else
			{
				alarm = new ArrayList<String>();
				alarmXml = new ArrayList<AlarmObject>();
			}
		}

	}

	/**
	 * Metoda dodaj¹ca nowy alarm do listy.
	 * 
	 * @param value
	 *            ³añcuch znaków reprezentuj¹cy datê alarmu
	 */
	public void addAlarm(String value)
	{
		alarm.add(value);
	}

	/**
	 * Metoda zwracaj¹ca listê wszystkich alarmów.
	 */
	public ArrayList<String> getList()
	{
		return alarm;
	}

	/**
	 * Metoda wywo³uj¹ca odpowiednie metody wstawiaj¹ce dane do bazy/obiektu do
	 * serialzacji (w zale¿noœci od trybu).
	 * 
	 * @param value
	 *            ³añcuch znaków reprezentuj¹cy datê alarmu.
	 */
	public void addAlarmToDB(String value)
	{
		if (mode == 1)
		{
			ArrayList<Integer> ids = db.getAllIds();
			db.insertDate(ids.size() + 1, value);
		} else
		{
			AlarmObject newAlarm = new AlarmObject();
			newAlarm.setDate(value);
			alarmXml.add(newAlarm);
		}

	}

	/**
	 * Metoda aktualizuj¹ca wartoœci w bazie/obiekcie do serializacji (w zale¿noœci
	 * od trybu).
	 * 
	 * @param id
	 *            ID alarmu
	 * @param value
	 *            ³añcuch znaków reprezentuj¹cy datê alarmu
	 */
	public void update(int id, String value)
	{
		if (mode == 1)
			db.setDate(id, value);
		else alarmXml.get(id).setDate(value);
	}

	/**
	 * Metoda aktualizuj¹ca wartoœci w liœcie alarmów.
	 * 
	 * @param id
	 *            ID alarmu
	 * @param value
	 *            ³añcuch znaków reprezentuj¹cy datê alarmu
	 */
	public void updateList(int id, String value)
	{
		alarm.set(id, value);
	}

	/**
	 * Metoda usuwaj¹ca alarm reprezentowany przez podany ³añcuch znaków z
	 * bazy/obiektu do serializacji (w zaleznoœci od trybu).
	 * 
	 * @param value
	 *            ³añcuch znaków reprezentuj¹cy datê alarmu
	 */
	public void removeAlarm(String value)
	{
		if (mode == 1)
			db.remove(value);
		else
		{
			for (int i = 0; i < alarmXml.size(); i++)
			{
				if (alarmXml.get(i).getDate().equals(value))
				{
					alarmXml.remove(i);
					break;
				}
			}
		}

		for (int i = 0; i < alarm.size(); i++)
		{
			if (alarm.get(i).equals(value))
			{
				alarm.remove(i);
				break;
			}
		}
	}

	/**
	 * Metoda zwracaj¹ca domyœlny URL bazy.
	 * 
	 * @return
	 */
	public String getDefaultUrl()
	{
		return db.getDefaultUrl();
	}

	/**
	 * Metoda zwracaj¹ca obecny URL bazy.
	 * 
	 * @return URL bazy
	 */
	public String getUrl()
	{
		return db.getUrl();
	}

	/**
	 * Metoda ustawiaj¹ca now¹ wartoœæ URL bazy
	 * @param value nowy URL bazy
	 * @throws ClassNotFoundException
	 *             <a href=
	 *             "https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html">https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html</a>
	 * @throws SQLException
	 *             <a href=
	 *             "https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html">https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html</a>
	 */
	public void setUrl(String value) throws ClassNotFoundException, SQLException
	{
		db.setUrl(value);
	}

	/**
	 * Metoda zwracj¹ca wszystkie alarmy dostêpne w obiekcie do serializacji.
	 * @return lista wszystkich alarmów
	 */
	public ArrayList<AlarmObject> getAlarmXml()
	{
		return alarmXml;
	}

	/**
	 * Metoda zwracaj¹ca status po³¹czenia z baz¹.
	 * @return zwraca true w przypadku pomyœlnego po³¹czenia z baz¹ danych, false w przeciwnym wypadku.
	 */
	public boolean getConnectionStatus()
	{
		return connectionFailed;
	}

	/**
	 * Metoda zapisuj¹ca stan listy alarmów.
	 */
	public void saveData()
	{
		Serializer serialzier2 = new Serializer("alarm.xml");
		serialzier2.serialize(alarmXml);
	}
}
