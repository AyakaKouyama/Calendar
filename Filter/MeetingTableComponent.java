import java.awt.Font;
import java.awt.Frame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MeetingTableComponent
{
	Frame frame;
	JTable table;
	JScrollPane scrollList;

	Object[][] data;
	Object[][] oryginalData;
	
    String[] columnNames = {"Nazwa", "Lokalizacja", "Data", "Szczeg�y"};
    String name, localization, time, details;
    
    FillMeetingData meetingData;
    Window window;
    MeetingsFilterLogic logic;
    int size;
	MeetingTableComponent(Frame frame, Window window, FillMeetingData meetingData)
	{
		this.frame = frame;
		this.window = window;
	    this.meetingData = meetingData;
	    size = meetingData.getMap().size();
		data = new Object[meetingData.getMap().size()][4];
		logic = new MeetingsFilterLogic(window.getCalendaeWindow().getCalendar().getDB());
		fillList();
		oryginalData = data;
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames) { 
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		table= new JTable(model);
		table.setFont(new Font("Arial", Font.PLAIN, 16));
		table.setRowHeight(20);
		scrollList = new JScrollPane(table);
		scrollList.setBounds(20, 20, 550, 200);
		
		
	}
	
	public void show()
	{
		frame.add(scrollList);
	}
	

	public void fillList()
	{
		meetingData.getAllIDs();
		for(int i = 0; i<size; i++)
		{
			cellValues(meetingData.getAllIDs().get(i));
			getDate(meetingData.getAllIDs().get(i));
			data[i][0] = name;
		    data[i][1] = localization;
		    data[i][2] = getDate(meetingData.getAllIDs().get(i));
	    	data[i][3] = details;
	    			
		}
	}
	
	public void reFill()
	{
		for(int i = 0; i<size; i++)
		{
			for(int j = 0; j<4; j++)
			{
				table.getModel().setValueAt(data[i][j], i, j);
			}
		}
		
	}

	public void remove()
	{
		data = logic.remove(data, meetingData.getMap().size());
		reFill();
		window.getCalendaeWindow().updateCalendar();
	}
	
	public void removeOne()
	{
		System.out.println(meetingData.getMap().size());
		data = logic.removeOne(data, table.getSelectedRow(), meetingData.getMap().size());
		reFill();
		window.getCalendaeWindow().updateCalendar();
		size--;
	}
	
	public void cellValues(int id)
	{
		String meeting = meetingData.getMap().get(id);

		int[] index = new int[5];
		index[0] = meeting.indexOf(':');
		System.out.println(index[0]);
		for(int i = 1; i<5; i++)
		{
			index[i] = meeting.indexOf(':', (index[i-1] + 1));
		}
		

	  if(index[4] == -1)
	  {
		  name = meeting.substring(index[0] + 1 , index[1] - "Lokalizacja".length() - 1);
		  localization = meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1);
		  time = meeting.substring(index[2] + 1, index[3] - "Szczeg�y".length() - 1);
		  details = meeting.substring(index[3] + 1, meeting.length());
	  }
	  else
	  {
		  name = meeting.substring(index[0] + 1 , index[1] - "Lokalizacja".length() - 1);
		  localization = meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1);
		  time = meeting.substring(index[3] - 2 , index[4] - "Szczeg�y".length() - 1);
		  details = meeting.substring(index[4] + 1, meeting.length());
	  }
  
	}
	
	public String getDate(int id)
	{
		String code = Integer.toString(id);
		String date = null;
		if(code.length() == 4)
		{
			date = "0" + code.substring(0, 1) + "/" + code.substring(1, 2) + "/20" + code.substring(2, 4);
		}
		else if(code.length() == 5)
		{
			date = code.substring(0, 2) + "/" + code.substring(2, 3) + "/20" + code.substring(3, 5);
		}
		
		String result  = date + " " + time;
		return result;
	}



	public void sort(String value, int order)
	{
		data = logic.sort(data, value, order);
		reFill();
	}
	
	
	public Object[][] getData()
	{
		return data;
	}
	
	public void setData(Object[][] obj)
	{
		data = obj;
	}
	
	public void resetData()
	{
		data = oryginalData;
	}
	
	
}
