import java.util.ArrayList;

/**
 * Klasa zawieraj¹ca listê obiektów reprezentuj¹cych alarmy.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
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
	 * Metoda wype³niaj¹ca listê poprzez deserializacjê obiektu z pliku.
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
