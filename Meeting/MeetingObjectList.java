import java.util.ArrayList;
import java.util.HashMap;

/**
 * Klasa zawieraj�ca list� wszystkich spotka� pobranych z pliku.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
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
	 * Metoda zwracaj�ca map� wszystkich spotka� pobranych z pliku.
	 * @return mapa wszystkich spotka�
	 */
	@SuppressWarnings("unchecked")
	public HashMap<Integer, MeetingObject> deserializeList()
	{
		Serializer serializer = new Serializer("meeting.xml");
		list = (HashMap<Integer, MeetingObject>) serializer.deserialize();
		return list;
	}

	/**
	 * Metoda zwracaj�ca list� wszystkich dost�pnych ID spotka� w pliku.
	 * @return lista wszystkich ID spotka�
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
