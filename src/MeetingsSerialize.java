import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;


public class MeetingsSerialize
{
	CalendarLogic calendar = new CalendarLogic();
	Object data;
	
	MeetingsSerialize()
	{
		data = calendar.getData();
	}
	
	public void SerializeData() 
	{

	}
	public void Desrerialize()
	{
		
	}
	
}
