import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Window implements ActionListener, MouseListener
{
	JFrame frame;
	
	JButton next;
	JButton prev;
	JButton nextMonth;
	JButton prevMonth;
	JButton about;
	JButton alarmClock;
	JButton options;
	JButton allAlarms;
	JButton filter;
	
	JPanel alarmCloclkPanel;
	
	JTextField text1;
	JTable table;
	JPanel panelTable;
	JTableHeader header;
	
	JLabel year;
	JLabel month;
	JLabel dateTime;
	JLabel time;
	
	int currentYear;
	int currentMonth;
	int currentDay;
	
	String meetingDetails = null;
	CalendarLogic calendar;
	MyClock clock;
	Options optionsWidnow;
	CalendarTable table2;
	AlarmClock alarmWindow;
	AlarmClockLogic alarmLogic;
	
	Window(String title) 
	{
		
		
		calendar = new CalendarLogic(this);
		
		alarmLogic = new AlarmClockLogic();
		alarmWindow = new AlarmClock(this, alarmLogic);
		optionsWidnow = new Options(this, alarmLogic);
		
		frame = new JFrame();
		next = new JButton("Next");
		prev = new JButton("Prev");
		
		nextMonth = new JButton("Next");
		prevMonth = new JButton("Prev");
		about = new JButton("About");
		alarmClock = new JButton("Alarm");
		allAlarms = new JButton("Alarmy");
		alarmCloclkPanel = new JPanel();
		
			
		currentYear = Calendar.getInstance().get(Calendar.YEAR);
		currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		year = new JLabel(Integer.toString(currentYear));
		month = new JLabel(Integer.toString(currentMonth));
		

		next.addActionListener(this);
		prev.addActionListener(this);
		nextMonth.addActionListener(this);
		prevMonth.addActionListener(this);
		about.addActionListener(this);
		alarmClock.addActionListener(this);
		allAlarms.addActionListener(this);
		
		panelTable = new JPanel(); 
	    panelTable.setBounds(10, 150, 560, 800);
	    
		frame.getContentPane().setLayout(null);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setSize(1000, 800);
		frame.setLocationRelativeTo(null);
		
		initTable();
		table.addMouseListener(this);
		//table2 = new CalendarTable(this);
		//table2.add();

		next.setBounds(350, 50, 80, 30);
		frame.add(next);
		prev.setBounds(150, 50, 80, 30);
		frame.add(prev);
		nextMonth.setBounds(350, 100, 80, 30);
		frame.add(nextMonth);
		prevMonth.setBounds(150, 100, 80, 30);
		frame.add(prevMonth);
		allAlarms.setBounds(600, 280, 80, 30);
		frame.add(allAlarms);
		
		
		year.setBounds(270, 50, 80, 30);
		year.setFont(new Font("Calibri", Font.BOLD, 20));
		frame.add(year);
		month.setBounds(285, 100, 80, 30);
		month.setFont(new Font("Calibri", Font.BOLD, 20));
		frame.add(month);
		
		about.setBounds(860, 720, 80, 30);
		frame.add(about);
		
		clock = new MyClock(frame);
		
		alarmClock.setBounds(600, 200, 80, 30);
		frame.add(alarmClock);
		
		options = new JButton("Options");
		options.setBounds(700, 720, 80, 30);
		options.addActionListener(this);
		frame.add(options);
		
		filter = new JButton("Filtr");
		filter.setBounds(600, 350, 80, 30);
		filter.addActionListener(this);
		frame.add(filter);
		
	}
	
	public void show()
	{
		frame.setVisible(true);
	}
	
	
	void initTable()
	{
	
		table = new JTable(new MyTableModel(this));
		panelTable.add(table);

		frame.add(panelTable);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		for(int i = 0; i<13; i++)
		{
			if(i%2 == 1)
			{
				table.setRowHeight(i, 30);
			}
			else
			{
				table.setRowHeight(i, 60);
			}
		}
		
		
		TableColumnModel model = table.getColumnModel();
		table.setFont(new Font("Calibri", Font.BOLD, 20));
		JTextField aligment = new JTextField();
		aligment.setHorizontalAlignment(SwingConstants.CENTER);
		MyRenderer renderer = new MyRenderer();

		for(int i = 0; i<7; i++)
		{
			model.getColumn(i).setPreferredWidth(80);
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);		
		}
		
	
	}
	
	private class MyRenderer extends JTextArea implements TableCellRenderer
	{
		Color bacgroundColor = new Color(218, 235, 233);
		
		MyRenderer()
		{
			 setLineWrap(true);
			 setWrapStyleWord(true);
			 setOpaque(true);
			 
		}
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
        {
        	
        	calendar.fillCalendar();
        	if(row == 0)
        	{
        		setBackground(new Color(60, 104, 100));
        	}
        	else if(row == calendar.currentDayRow(currentDay) && column == calendar.currentDayColumn(currentDay) 
        	   && currentYear == Calendar.getInstance().get(Calendar.YEAR) && currentMonth == Calendar.getInstance().get(Calendar.MONTH) + 1)
        	{        	
        		setBackground(new Color(118, 177, 171));
        	}
        	else if(row == calendar.currentDayRow(currentDay) + 1  && column == calendar.currentDayColumn(currentDay)
             	   && currentYear == Calendar.getInstance().get(Calendar.YEAR) && currentMonth == Calendar.getInstance().get(Calendar.MONTH) + 1)
            {        	
             	 setBackground(new Color(118, 177, 171));
            }
        	else
        	{
        		setBackground(bacgroundColor);
        	}
        	
        	if(row == 0 || row%2 == 1)
        	{
        		//setHorizontalAlignment(SwingConstants.LEFT);
                setFont(new Font("Calibri", Font.BOLD, 20));
        	}
        	else
        	{
        		//setHorizontalAlignment(SwingConstants.CENTER);
        		setFont(new Font("Calibri", Font.PLAIN, 10));
        	}
        	setText((value == null) ? "" : value.toString());
            return this;
        }
	}



	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if(source == next)
		{
			year.setText(Integer.toString(currentYear + 1));
			currentYear += 1;
		}
		if(source == prev)
		{
			year.setText(Integer.toString(currentYear - 1));
			currentYear -= 1;
		}
		if(source == nextMonth)
		{
			if((currentMonth + 1) == 13)
			{
				year.setText(Integer.toString(currentYear + 1));
				month.setText("1");
				currentYear += 1;
				currentMonth = 1;
			}
			else
			{			
				month.setText(Integer.toString(currentMonth + 1));
				currentMonth += 1;	
			}

		}
		if(source == prevMonth)
		{
			if((currentMonth - 1) == 0)
			{
				year.setText(Integer.toString(currentYear - 1));
				currentYear -= 1;
				month.setText("12");
				currentMonth = 12;
				
			}
			else
			{
			  month.setText(Integer.toString(currentMonth -1));
			  currentMonth -= 1;
			}

		}
		
		if(source == prevMonth || source == nextMonth || source == next || source == prev)
		{
			updateCalendar();
		}
		
		if(source == about)
		{
			About aboutInfo = new About();
			aboutInfo.show(frame);	
		}
		
		if(source == alarmClock)
		{
			alarmWindow.updateDate();
			alarmWindow.add();
		}
		
		if(source == options)
		{
			optionsWidnow.openWindiow();
		}
		
		if(source == allAlarms)
		{
			AllAlarms alarmList = new AllAlarms();
			alarmList.show();
		}
		
		if(source == filter)
		{
			MeetingsFilter meetingFilter = new MeetingsFilter(this);
			meetingFilter.show();
		}

		
	}
	
	
	public void updateCalendar()
	{
		for(int i = 0; i<13; i++)
		{
			for(int j = 0; j< 7; j++)
			{				
				if(i%2 == 0 && table.getValueAt(i, j) != null)
				{
					calendar.setObject(null, i, j);
				}
			}
		}
		
		
		calendar.fillCalendar();
		for(int i = 1; i<13; i++)
		{
			for(int j = 0; j<7; j++)
			{
				table.setValueAt(calendar.getCellValue(i, j), i, j);
			}
		}
	}
	public int getYear()
	{
		return currentYear;
	}
	
	public int getMonth()
	{
		return currentMonth;
	}
	
	public int getDay()
	{
		return currentDay;
	}



	@Override
	public void mouseClicked(MouseEvent arg0)
	{
	}



	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
	}



	@Override
	public void mouseExited(MouseEvent arg0) 
	{
	}



	@Override
	public void mousePressed(MouseEvent e) 
	{
		Point point = e.getPoint();
		int row = table.rowAtPoint(point);
		int column = table.columnAtPoint(point);
		
		if(e.getClickCount() == 2 && table.getSelectedRow() % 2 == 0 && row != 0)
		{
			Object day = table.getValueAt(row - 1, column);
			if(day != null)
			{
			  InsertMeetingWindow insert = new InsertMeetingWindow(this, calendar, row, column, currentYear, currentMonth, (int)day, optionsWidnow.getChoice(), alarmLogic);
			  insert.init();	
			}
		}	
	}


	public void fillCell(String value, int row, int column)
	{
		table.setValueAt(value, row, column);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
	}

	public Frame getFrame()
	{
		return frame;
	}
	
	public int getCurrentDay()
	{
		return currentDay;
	}
	
	public int getCurrentMonth()
	{
		return currentMonth;
	}
	
	public int getCurrentYear()
	{
		return currentYear;
	}
	
	public String getOptionsChoice()
	{
		return optionsWidnow.getChoice();
	}
	
	public CalendarLogic getCalendar()
	{
		return calendar;
	}
	
	public JTable getTable()
	{
		return table;
	}
}
