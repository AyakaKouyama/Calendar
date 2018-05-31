import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MeetingObjectList
{
	HashMap<Integer, MeetingObject> list;
	ArrayList<Integer> allIds;

	MeetingObjectList()
	{
		list = new HashMap<Integer, MeetingObject>();
		allIds = new ArrayList<Integer>();
	}

	public HashMap<Integer, MeetingObject> deserializeList()
	{
		Serializer serializer = new Serializer("meeting.xml");
		list = (HashMap<Integer, MeetingObject>) serializer.deserialize();
		return list;
	}

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
