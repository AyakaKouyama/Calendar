
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Window extends JFrame implements ActionListener, MouseListener
{
	JFrame frame;
	
	JButton next;
	JButton prev;
	JButton nextMonth;
	JButton prevMonth;
	JButton about;
	
	JPanel nextPanel;
	JPanel prevPanel;
	JPanel nextMonthPanel;
	JPanel prevMonthPanel;
	JPanel aboutPanel;
	
	JTextField text1;
	JTable table;
	JPanel panelTable;
	JTableHeader header;
	
	JLabel year;
	JLabel month;
	int currentYear;
	int currentMonth;
	int currentDay;
	
	String meetingDetails = null;
	CalendarLogic calendar;
	

	Window(String title) 
	{
		
		calendar = new CalendarLogic(this);
		frame = new JFrame();
		next = new JButton("Next");
		prev = new JButton("Prev");
		
		nextMonth = new JButton("Next");
		prevMonth = new JButton("Prev");
		about = new JButton("About");
		
		nextPanel = new JPanel();
		prevPanel = new JPanel();
		nextMonthPanel = new JPanel();
		prevMonthPanel = new JPanel();
		aboutPanel = new JPanel();
		
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
		frame.add(panelTable);
		table.addMouseListener(this);

		next.setBounds(350, 50, 80, 30);
		frame.add(nextPanel);
		frame.add(next);
		
		prev.setBounds(150, 50, 80, 30);
		frame.add(prevPanel);
		frame.add(prev);
		
		nextMonth.setBounds(350, 100, 80, 30);
		frame.add(nextMonthPanel);
		frame.add(nextMonth);
		
		prevMonth.setBounds(150, 100, 80, 30);
		frame.add(prevMonthPanel);
		frame.add(prevMonth);
		
		year.setBounds(270, 50, 80, 30);
		year.setFont(new Font("Calibri", Font.BOLD, 20));
		frame.add(year);

		month.setBounds(285, 100, 80, 30);
		month.setFont(new Font("Calibri", Font.BOLD, 20));
		frame.add(month);
		
		about.setBounds(800, 500, 80, 30);
		frame.add(aboutPanel);
		frame.add(about);
		
		
		frame.setVisible(true);
	}
	
	
	
	void setButton(JButton button, JPanel panel, int x, int y, int width, int height)
	{
		panel.setLayout(null);
		button.setBounds(x, y, width, height);
		panel.add(button);
		frame.add(panel);
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
		private TableCellRenderer defaultTableCellRenderer= new JTable().getDefaultRenderer(Object.class);
		
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
        	//return cell;
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
			for(int i = 1; i<13; i++)
			{
				for(int j = 0; j<7; j++)
				{
					table.setValueAt(calendar.fillCalendar()[i][j], i, j);
				}
			}
		}
		
		if(source == about)
		{
			About aboutInfo = new About();
			aboutInfo.show(frame);
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
			InsertMeetingWindow insert = new InsertMeetingWindow(this, calendar, row, column);
			insert.init();
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


	
	
	
}
