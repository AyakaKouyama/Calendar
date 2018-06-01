import java.util.ArrayList;

public class AlarmObjectList
{
	ArrayList<AlarmObject> alarmXml;
	
	AlarmObjectList()
	{
		alarmXml = new ArrayList<AlarmObject>();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<AlarmObject> deserializeList()
	{
		Serializer serializer = new Serializer("alarm.xml");
		alarmXml = (ArrayList<AlarmObject>) serializer.deserialize();
		return alarmXml;
	}

}
