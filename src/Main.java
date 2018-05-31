import java.util.ArrayList;
import java.util.HashMap;

public class Main
{

	public static void main(String[] args)
	{
		Window window = new Window("Organizer");
		window.show();

		MeetingObject obj = new MeetingObject();
		obj.setDetails("Test");
		obj.setDtae("31.5.2018");
		obj.setLocalization("gdzieœ tam");
		obj.setTime("12:06");
		obj.setName("hejka");
		MeetingObject obj2 = new MeetingObject();
		obj2.setDetails("Test2");
		obj2.setDtae("1.6.2018");
		obj2.setLocalization("gdzieœ");
		obj2.setTime("12:17");
		obj2.setName("naklejka");
		HashMap<Integer, MeetingObject> list = new HashMap<Integer, MeetingObject>();
		list.put(31518, obj);
		list.put(1618, obj2);
		// Serializer s = new Serializer("meeting.xml");
		// s.serialize(list);

		// HashMap<Integer, MeetingObject> res = (HashMap<Integer, MeetingObject>)
		// s.deserialize();
	}

}
