import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa zawieraj�ca list� wszystkich alarm�w pobranych z bazy danych i
 * manipuluj�ca na tych danych.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
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
	 * Konstruktor klasy.Inicjalizuje obiekiety i wype�nia list�.
	 * 
	 * @param mode
	 *            tryb pracy aplikacji
	 * @param showBar
	 *            warto�� determinuj�ca czy ma zosta� pokazany pasek post�pu
	 *            po��czenia z baz�
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
	 * Metoda wype�niaj�ca list� danymi z bazy/pliku XML (w zale�no�ci od trybu).
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
	 * Metoda dodaj�ca nowy alarm do listy.
	 * 
	 * @param value
	 *            �a�cuch znak�w reprezentuj�cy dat� alarmu
	 */
	public void addAlarm(String value)
	{
		alarm.add(value);
	}

	/**
	 * Metoda zwracaj�ca list� wszystkich alarm�w.
	 */
	public ArrayList<String> getList()
	{
		return alarm;
	}

	/**
	 * Metoda wywo�uj�ca odpowiednie metody wstawiaj�ce dane do bazy/obiektu do
	 * serialzacji (w zale�no�ci od trybu).
	 * 
	 * @param value
	 *            �a�cuch znak�w reprezentuj�cy dat� alarmu.
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
	 * Metoda aktualizuj�ca warto�ci w bazie/obiekcie do serializacji (w zale�no�ci
	 * od trybu).
	 * 
	 * @param id
	 *            ID alarmu
	 * @param value
	 *            �a�cuch znak�w reprezentuj�cy dat� alarmu
	 */
	public void update(int id, String value)
	{
		if (mode == 1)
			db.setDate(id, value);
		else alarmXml.get(id).setDate(value);
	}

	/**
	 * Metoda aktualizuj�ca warto�ci w li�cie alarm�w.
	 * 
	 * @param id
	 *            ID alarmu
	 * @param value
	 *            �a�cuch znak�w reprezentuj�cy dat� alarmu
	 */
	public void updateList(int id, String value)
	{
		alarm.set(id, value);
	}

	/**
	 * Metoda usuwaj�ca alarm reprezentowany przez podany �a�cuch znak�w z
	 * bazy/obiektu do serializacji (w zalezno�ci od trybu).
	 * 
	 * @param value
	 *            �a�cuch znak�w reprezentuj�cy dat� alarmu
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
	 * Metoda zwracaj�ca domy�lny URL bazy.
	 * 
	 * @return
	 */
	public String getDefaultUrl()
	{
		return db.getDefaultUrl();
	}

	/**
	 * Metoda zwracaj�ca obecny URL bazy.
	 * 
	 * @return URL bazy
	 */
	public String getUrl()
	{
		return db.getUrl();
	}

	/**
	 * Metoda ustawiaj�ca now� warto�� URL bazy
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
	 * Metoda zwracj�ca wszystkie alarmy dost�pne w obiekcie do serializacji.
	 * @return lista wszystkich alarm�w
	 */
	public ArrayList<AlarmObject> getAlarmXml()
	{
		return alarmXml;
	}

	/**
	 * Metoda zwracaj�ca status po��czenia z baz�.
	 * @return zwraca true w przypadku pomy�lnego po��czenia z baz� danych, false w przeciwnym wypadku.
	 */
	public boolean getConnectionStatus()
	{
		return connectionFailed;
	}

	/**
	 * Metoda zapisuj�ca stan listy alarm�w.
	 */
	public void saveData()
	{
		Serializer serialzier2 = new Serializer("alarm.xml");
		serialzier2.serialize(alarmXml);
	}
}
