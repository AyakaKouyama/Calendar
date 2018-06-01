import java.util.ArrayList;
import java.util.HashMap;

/**
 * Klasa zawieraj¹ca listê wszystkich spotkañ pobranych z pliku.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class MeetingObjectList
{
	private HashMap<Integer, MeetingObject> list;
	private ArrayList<Integer> allIds;

	/**
	 * Konstruktor klasy.
	 */
	MeetingObjectList()
	{
		list = new HashMap<Integer, MeetingObject>();
		allIds = new ArrayList<Integer>();
	}

	/**
	 * Metoda zwracaj¹ca mapê wszystkich spotkañ pobranych z pliku.
	 * @return mapa wszystkich spotkañ
	 */
	@SuppressWarnings("unchecked")
	public HashMap<Integer, MeetingObject> deserializeList()
	{
		Serializer serializer = new Serializer("meeting.xml");
		list = (HashMap<Integer, MeetingObject>) serializer.deserialize();
		return list;
	}

	/**
	 * Metoda zwracaj¹ca listê wszystkich dostêpnych ID spotkañ w pliku.
	 * @return lista wszystkich ID spotkañ
	 */
	public ArrayList<Integer> getAllIds()
	{
		if (deserializeList() != null)
		{
			for (int key : deserializeList().keySet())
			{
				allIds.add(key);
			}

			return allIds;
		}
		return null;
	}

}
