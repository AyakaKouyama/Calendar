import java.util.ArrayList;

/**
 * Klasa zawieraj�ca list� obiekt�w reprezentuj�cych alarmy.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
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
	 * Metoda wype�niaj�ca list� poprzez deserializacj� obiektu z pliku.
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
