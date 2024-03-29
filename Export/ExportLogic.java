import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Klasa realizuj�ca logik� obs�ugi eksportu plik�w.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 */
public class ExportLogic 
{
	private String header = "\"Temat\",\"Lokalizacja\",\"Godzina rozpocz�cia\",\"Data rozpocz�cia\",\"Opis\"";
	private FillMeetingData data;
	private String[] values;
	private ArrayList<Integer> allIds;
	private Map<Integer, String> meetings;
	
	/**
	 * Konstruktor klasy.
	 * @param data obiekt zawieraj�cy i manipuluj�cy list� spotka�
	 * @throws ClassNotFoundException {@link}
	 * @throws SQLException {@link}
	 */
	public ExportLogic(FillMeetingData data) throws ClassNotFoundException, SQLException
	{
		this.data = data;
		values = new String[5];
		allIds = data.getAllIDs();
		meetings = data.getMap();
		
	}
	
	/**
	 * Metoda zapisuj�ca spotkania do pliku w formacie .csv.
	 * @param fileName nazwa pliku 
	 * @throws IOException {@link}
	 */
	public void save(File fileName) throws IOException
	{
		try(FileWriter file = new FileWriter(fileName + ".csv"))
		{
			file.write(header + "\n");
			for(int i = 0; i<allIds.size(); i++)
			{
				getMeeting(allIds.get(i));
				file.write("\"" + values[0] + "\",\""  + values[1] + "\",\"" + values[2] + "\",\"" + values[4] + "\",\"" + values[3] + "\"\n");
			}
		}
	}
	

	/**
	 * Metoda wywo�ywana w motodzie save() odczytuj�ca dane (nazwa, lokalizacja, data, szczeg�y) dotycz�ce spotkania o podanym ID.
	 * @param id ID spotkania
	 */
	public void getMeeting(int id)
	{
		String meeting = meetings.get(id);
		
		int[] index = new int[5];
		index[0] = meeting.indexOf(':');
		for(int i = 1; i<5; i++)
		{
			index[i] = meeting.indexOf(':', (index[i-1] + 1));
		}
			
			
		  if(index[4] == -1)
		  {
			  values[0] = meeting.substring(index[0] + 1 , index[1] - "Lokalizacja".length() - 1);
			  values[1] = meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1);
			  values[2] = meeting.substring(index[2] + 1, index[3] - "Szczeg�y".length() - 1);
			  values[3] = meeting.substring(index[3] + 1, meeting.length());
		  }
		  else
		  {
			 values[0] = meeting.substring(index[0] + 1 , index[1] - "Lokalizacja".length() - 1);
			 values[1] = meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1);
			 values[2] = meeting.substring(index[3] - 2 , index[4] - "Szczeg�y".length() - 1);
			 values[3] = meeting.substring(index[4] + 1, meeting.length());
		  }
		  
		 
		  String sId = Integer.toString(id);
		  if(sId.length() == 4)
		  {
			   values[4] = sId.substring(0, 1) + "." + sId.substring(1, 2) + ".20" + sId.substring(2, 4);
		  }
		  else
		  {
			  values[4] = sId.substring(0, 2) + "." + sId.substring(2, 3) + ".20" + sId.substring(3, 5);
		  }
		 
	}
	
}
