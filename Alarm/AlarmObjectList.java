import java.util.ArrayList;

/**
 * Klasa zawierająca listę obiektów reprezentujących alarmy.
 * @author Sylwia Mieszkowska
 * @author Anna Ciepłucha
 *
 */
public class AlarmObjectList
{
	private ArrayList<AlarmObject> alarmXml;

	/**
	 * Konstruktor klasy.
	 */
	AlarmObjectList()
	{
		alarmXml = new ArrayList<AlarmObject>();
	}

	/**
	 * Metoda wypełniająca listę poprzez deserializację obiektu z pliku.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<AlarmObject> deserializeList()
	{
		Serializer serializer = new Serializer("alarm.xml");
		alarmXml = (ArrayList<AlarmObject>) serializer.deserialize();
		return alarmXml;
	}

}
