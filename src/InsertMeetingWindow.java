import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class InsertMeetingWindow implements ActionListener
{
	JFrame frame;
	JLabel detalisLabel;
	JLabel nameLabel;
	JLabel localizationLabel;
	JLabel dateLabel;
	
	JTextField details;
	JTextField name;
	JTextField localization;
	JTextField date;
	
	JButton accept;
	JPanel accetpPanel;
	String stringDetails = null;
	String stringLocalization = null;
	String stringDate = null;
	String stringName = null;
	boolean done = false;
	Window window;
	CalendarLogic mcalenadar;
	int column;
	int row;
	
	JRadioButton choice;

	InsertMeetingWindow(Window window, CalendarLogic calendar, int column, int row)
	{
		this.window = window;
		this.column = column;
		this.row = row;
		this.mcalenadar = calendar;
				
		frame = new JFrame();
		detalisLabel = new JLabel("Szczegó³y");
		nameLabel = new JLabel("Nazwa");
		localizationLabel = new JLabel("Lokalizacja");
		dateLabel = new JLabel("Data");
		details = new JTextField(20);
		accept = new JButton("OK");
		accetpPanel = new JPanel();
		choice = new JRadioButton();
		
		name = new JTextField(20);
		localization = new JTextField(20);
		date = new JTextField(20);
	
		initFillTable();
		
	}
	
	public void initFillTable()
	{
		String meeting = mcalenadar.getCellValue(column, row);
		
		if(meeting != null)
		{
			int[] index = new int[4];
			index[0] = meeting.indexOf(':');
			System.out.println(index[0]);
			for(int i = 1; i<4; i++)
			{
				index[i] = meeting.indexOf(':', (index[i-1] + 1));
				System.out.println("i" + index[i]);
			}
			
		  name.setText(meeting.substring(index[0] + 1 , index[1] - "Lokalizacja".length() - 1));
		  localization.setText(meeting.substring(index[1] + 1, index[2] - "Data".length() - 1));
		  date.setText(meeting.substring(index[2] + 1, index[3] - "Szczegó³y".length() - 1));
		  details.setText(meeting.substring(index[3] + 1, meeting.length()));
		  
		  System.out.println(localization.getText());
		}
		
		
	}
	
	public void init()
	{
		frame.setTitle("Meeting");
		frame.setResizable(false);
		frame.setLayout(null);
		frame.pack();
		frame.setSize(400, 300);
		
		frame.add(detalisLabel);
		frame.setLocationRelativeTo(null);
		
		
		
		name.setBounds(200, 20, 150, 30);
		localization.setBounds(200, 60, 150, 30);
		date.setBounds(200, 100, 150, 30);
		details.setBounds(200, 140, 150, 30);
		
		nameLabel.setBounds(100, 20, 150, 30);
		localizationLabel.setBounds(100, 60, 150, 30);
		dateLabel.setBounds(100, 100, 150, 30);
		detalisLabel.setBounds(100, 140, 150, 30);
		
		frame.add(nameLabel);
		frame.add(localizationLabel);
		frame.add(dateLabel);
		frame.add(localization);
		frame.add(date);
		frame.add(name);
		frame.add(detalisLabel);
		frame.add(details);
		accept.setBounds(100, 200, 80, 30);
		accetpPanel.add(accept);
		frame.add(accept);
		choice.setBounds(100, 150, 80, 30);
		//frame.add(choice);
		accept.addActionListener(this);
		details.addActionListener(this);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if(source == accept)
		{
			done = true;
			stringDetails = details.getText();
			stringName = name.getText();
			stringLocalization = localization.getText();
			stringDate = date.getText();
			
			fill(column, row);
			fillData(column, row);
			System.out.println(row);
			System.out.println(column);
			
			frame.dispose();
		}
	}
	
	public void fill(int row, int column)
	{
		window.fillCell("Nazwa:" + stringName + "\nLokalizacja:" + stringLocalization + "\nData:" + stringDate + "\nSzczegó³y:" + stringDetails, row, column);
	}
	
	public void fillData(int row, int column)
	{
		mcalenadar.setCellValue("Nazwa:" + stringName + "\nLokalizacja:" + stringLocalization + "\nData: " + stringDate + "\nSzczegó³y:" + stringDetails, row, column);
	}
	
	public String getDetails()
	{
		return stringDetails;
	}
	
	public boolean getDone()
	{
		return done;
	}
}
