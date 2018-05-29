import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;


public class CalendarWindow  implements MouseListener, ActionListener
{
	JTable table;
	JPanel panel;
	Window window;
	CalendarLogic calendar;
	
	JButton next;
	JButton prev;
	JButton nextMonth;
	JButton prevMonth;
	
	JLabel year;
	JLabel month;
	
	int currentYear;
	int currentMonth;
	int currentDay;
	
	
	CalendarWindow(Window window)
	{
		currentYear = Calendar.getInstance().get(Calendar.YEAR);
		currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		
		this.window = window;
		panel = new JPanel();
		panel.setBounds(10, 150, 560, 800);
		window.getFrame().add(panel);
		calendar = new CalendarLogic(this);
		initTable();
		table.addMouseListener(this);
		
		next = new JButton("Next");
		prev = new JButton("Prev");
		nextMonth = new JButton("Next");
		prevMonth = new JButton("Prev");
		
		next.addActionListener(this);
		prev.addActionListener(this);
		nextMonth.addActionListener(this);
		prevMonth.addActionListener(this);
		
		next.setBounds(350, 50, 80, 30);
		window.getFrame().add(next);
		prev.setBounds(150, 50, 80, 30);
		window.getFrame().add(prev);
		nextMonth.setBounds(350, 100, 80, 30);
		window.getFrame().add(nextMonth);
		prevMonth.setBounds(150, 100, 80, 30);
		window.getFrame().add(prevMonth);
		
		year = new JLabel(Integer.toString(currentYear));
		month = new JLabel(Integer.toString(currentMonth));
		
		year.setBounds(270, 50, 80, 30);
		year.setFont(new Font("Calibri", Font.BOLD, 20));
		window.getFrame().add(year);
		month.setBounds(285, 100, 80, 30);
		month.setFont(new Font("Calibri", Font.BOLD, 20));
		window.getFrame().add(month);
	}
	
	void initTable()
	{
	
		table = new JTable(new MyTableModel(this));
		panel.add(table);

		window.getFrame().add(panel);
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
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
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
        	
        	calendar.fillCalendar(currentYear, currentMonth);
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
        		setFont(new Font("Calibri", Font.PLAIN, 15));
        	}
        	setText((value == null) ? "" : value.toString());
            return this;
        }
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
			  InsertMeetingWindow insert = new InsertMeetingWindow(window, calendar, row, column, currentYear, currentMonth, (int)day, window.getOptionWindow().getChoice(), window.getAlarmLogic());
			  insert.init();	
			}
		}	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		
		calendar.fillCalendar(currentYear, currentMonth);
		for(int i = 1; i<13; i++)
		{
			for(int j = 0; j<7; j++)
			{
				table.setValueAt(calendar.getCellValue(i, j), i, j);
			}
		}
	}
	
	public void fillCell(String value, int row, int column)
	{
		table.setValueAt(value, row, column);
	}
	
	
	public void setCurrentMonth(int value)
	{
		currentMonth = value;
	}
	
	public void setCurrentDay(int value)
	{
		currentDay = value;
	}
	
	public void setCurrentYear(int value)
	{
		currentYear = value;
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
			//window.updateCalendar();
		}
	}
	
	public CalendarLogic getCalendar()
	{
		return calendar;
	}
	
	public int getYear()
	{
		return currentYear;
	}
	
	public int getMonth()
	{
		return currentMonth;
	}
	
	public int getCurrentDay()
	{
		return currentDay;
	}
}
