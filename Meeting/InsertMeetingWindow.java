import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class InsertMeetingWindow implements ActionListener, ItemListener
{
	JFrame frame;
	JLabel detalisLabel;
	JLabel nameLabel;
	JLabel localizationLabel;
	JLabel dateLabel;
	
	JTextField details;
	JTextField name;
	JTextField localization;
	JTextField time;
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	JButton accept;
	JButton alarm;
	JButton deleteMeeting;
	
	String stringDetails = null;
	String stringLocalization = null;
	String stringDate = null;
	String stringName = null;
	
	Choice remindChoice;
	boolean done = false;
	Window window;
	CalendarLogic mcalenadar;
	int column;
	int row;
	
	int day;
	int month;
	int year;
	
	int reminderTime;
	
	boolean accepted = false;
	
	JRadioButton choice;

	AlarmClockLogic alarmClock;
	AlarmInfoDialog alarmInfo;
	
	InsertMeetingWindow(Window window, CalendarLogic calendar, int column, int row, int year, int month, int day, String soundName, AlarmClockLogic alarmClock)
	{
		this.window = window;
		this.column = column;
		this.row = row;
		this.mcalenadar = calendar;
				
		this.day = day;
		this.year = year;
		this.month = month;
		
		this.alarmClock = alarmClock;
		frame = new JFrame();
		detalisLabel = new JLabel("Szczegó³y");
		nameLabel = new JLabel("Nazwa");
		localizationLabel = new JLabel("Lokalizacja");
		dateLabel = new JLabel("Data");
		details = new JTextField(20);
		accept = new JButton("OK");
		alarm = new JButton("Alarm");
		deleteMeeting = new JButton("Usuñ spotkanie");
		
		remindChoice = new Choice();
		
		name = new JTextField(20);
		localization = new JTextField(20);
		time = new JFormattedTextField(timeFormat);
		time.setText((new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()))));
	
		initFillTable();
		
		
	}
	
	public void initFillTable()
	{
		String meeting = (String)mcalenadar.getCellValue(column, row);
		
		if(meeting != null && meeting != "")
		{
			int[] index = new int[5];
			index[0] = meeting.indexOf(':');
			System.out.println(index[0]);
			for(int i = 1; i<5; i++)
			{
				index[i] = meeting.indexOf(':', (index[i-1] + 1));
				System.out.println("i" + index[i]);
			}
			
			System.out.println("asdf");
		  if(index[4] == -1)
		  {
			  name.setText(meeting.substring(index[0] + 1 , index[1] - "Lokalizacja".length() - 1));
			  localization.setText(meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1));
			  time.setText(meeting.substring(index[2] + 1, index[3] - "Szczegó³y".length() - 1));
			  details.setText(meeting.substring(index[3] + 1, meeting.length()));
		  }
		  else
		  {
			  name.setText(meeting.substring(index[0] + 1 , index[1] - "Lokalizacja".length() - 1));
			  localization.setText(meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1));
			  time.setText(meeting.substring(index[3] - 2 , index[4] - "Szczegó³y".length() - 1));
			  details.setText(meeting.substring(index[4] + 1, meeting.length()));
		  }
  
		}
		
		
	}
	
	public void init()
	{
		frame.setTitle("Meeting");
		frame.setResizable(false);
		frame.setLayout(null);
		frame.pack();
		frame.setSize(400, 300);
		
		
		frame.setLocationRelativeTo(null);
		
		remindChoice.setBounds(300, 70, 80, 30);
		remindChoice.add("0 min");
		remindChoice.add("5 min");
		remindChoice.add("15 min");
		remindChoice.add("30 min");
		remindChoice.add("1 godz");
		remindChoice.add("12 godz");
		remindChoice.add("1 dzieñ");
		remindChoice.select(2);
		remindChoice.addItemListener(this);
		reminderTime = 15;
		
		alarm.setBounds(300, 20, 80, 30);
		alarm.addActionListener(this);
		frame.add(alarm);
		
		name.setBounds(100, 20, 150, 30);
		localization.setBounds(100, 60, 150, 30);
		time.setBounds(100, 100, 150, 30);
		details.setBounds(100, 140, 150, 30);
		
		nameLabel.setBounds(20, 20, 150, 30);
		localizationLabel.setBounds(20, 60, 150, 30);
		dateLabel.setBounds(20, 100, 150, 30);
		detalisLabel.setBounds(20, 140, 150, 30);
		
		deleteMeeting.setBounds(300, 120, 80, 30);
		deleteMeeting.addActionListener(this);
		
		frame.add(deleteMeeting);
		frame.add(detalisLabel);
		frame.add(remindChoice);
		frame.add(nameLabel);
		frame.add(localizationLabel);
		frame.add(dateLabel);
		frame.add(localization);
		frame.add(time);
		frame.add(name);
		frame.add(detalisLabel);
		frame.add(details);
		accept.setBounds(150, 200, 80, 30);
		frame.add(accept);
		
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
			stringDate = time.getText();
			
			fill(column, row);
			fillData(column, row);
			
			accepted = false;
			frame.dispose();
		}
		
		if(source == alarm)
		{
			accepted = true;
			alarmInfo = new AlarmInfoDialog(window.getFrame(), getDate());
			
			alarmInfo.show();
		}
		
		if(source == deleteMeeting)
		{
			String sId = Integer.toString(day) + Integer.toString(month) + Integer.toString(year % 1000);
			int id = Integer.parseInt(sId);
			if(mcalenadar.getId(id) != -1)
			{
				name.setText("");
				localization.setText("");
				time.setText("");
				details.setText("");
				
				mcalenadar.deleteMeeting(id);
				fill(column, row);
				fillData(column, row);
			}
			
			
			// DELETE ALARM
			
		}
	}
	
	public void fill(int row, int column)
	{
		if(!name.getText().equals("") || !localization.getText().equals("") || !time.getText().equals("") || !details.getText().equals(""))
		{
			window.fillCell("Nazwa:" + stringName + "\nLokalizacja:" + stringLocalization + "\nGodzina:" + stringDate + "\nSzczegó³y:" + stringDetails, row, column);
		}
		else
		{
			window.fillCell("", row, column);
		}
	}
	
	public void fillData(int row, int column)
	{
		String sId = Integer.toString(day) + Integer.toString(month) + Integer.toString(year % 1000);
		int id = Integer.parseInt(sId);
		if(!name.getText().equals("") || !localization.getText().equals("") || !time.getText().equals("") || !details.getText().equals(""))
		{
			String value = "Nazwa:" + stringName + "\nLokalizacja:" + stringLocalization + "\nGodzina: " + stringDate + "\nSzczegó³y:" + stringDetails;
			mcalenadar.setCellValue(value, row, column);
			

			String date = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day) + " " + stringDate;
			
			mcalenadar.AddElementToList(id, value);
			if(mcalenadar.getId(id) != -1)
			{
				mcalenadar.setName(id, stringName);
				mcalenadar.setLocalization(id, stringLocalization);
				mcalenadar.setDate(id, date);
				mcalenadar.setDetails(id, stringDetails);
			}
			else
			{
				mcalenadar.insert(id, stringName, stringLocalization, date, stringDetails);
			}	
		}
		else
		{
			mcalenadar.deleteMeetingFromList(id);
		}
	}
	
	public String getDetails()
	{
		return stringDetails;
	}
	
	public boolean getDone()
	{
		return done;
	}
	
	public Calendar getDate()
	{
		if(accepted == true)
		{
			String sTime = time.getText();
			
			String minutes = sTime.substring(3);
			String hour = sTime.substring(0, 2);
			
			int h = Integer.parseInt(hour);
			int min = Integer.parseInt(minutes);
			int d = day;
			if(reminderTime < 60)
			{
				min -= reminderTime;
				
				if(min < 0)
				{
					min = 60 + min;
					h -= 1;
				}
				
			}
			else if(reminderTime >= 60)
			{
				if(reminderTime == 60)
				{
					h -= 1;
					
					if(h < 0)
					{
						d -= 1;
						h = 24 + h;
					}
				}
				if(reminderTime == 60 * 12)
				{
					h -= 12;
					
					if(h < 0)
					{
						d -= 1;
						h = 24 + h;
					}
				}
				if(reminderTime == 60 * 24)
				{
					d -= 1;
				}
			}
			
			
			sTime = Integer.toString(h) + ":" + Integer.toString(min);
			
			String sDate = Integer.toString(d) + "/" +  Integer.toString(month) + "/" + Integer.toString(year);
			Calendar cal = Calendar.getInstance();
			
			
			String ssDate =  Integer.toString(year) + "-" +  Integer.toString(month) + "-" + Integer.toString(day) + " " + sTime;
			System.out.println(ssDate);
		    alarmClock.addAlarm(ssDate);
		    alarmClock.addAlarmToList(ssDate);
			try 
			{
				cal.setTime(format.parse(sDate + " " + sTime));
				System.out.println(cal.getTime());
				return cal;
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getSource();
		
		if(source == remindChoice)
		{
			if(remindChoice.getSelectedItem() == "0 min")
			{
				reminderTime = 0;
			}
			if(remindChoice.getSelectedItem() == "5 min")
			{
				reminderTime = 5;
			}
			if(remindChoice.getSelectedItem() == "15 min")
			{
				reminderTime = 15;
			}
			if(remindChoice.getSelectedItem() == "30 min")
			{
				reminderTime = 30;
			}
			if(remindChoice.getSelectedItem() == "1 godz")
			{
				reminderTime = 60;
			}
			if(remindChoice.getSelectedItem() == "12 godz")
			{
				reminderTime = 60 * 12;
			}
			if(remindChoice.getSelectedItem() == "1 dzieñ")
			{
				reminderTime = 60 * 24;
			}
		}
		
	}
}
