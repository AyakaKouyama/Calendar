import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class CalendarTable implements ActionListener, MouseListener
{
	JTable table;
	JPanel panelTable;
	Window window;
	CalendarLogic calendar;
	
	int currentDay;
	int currentMonth;
	int currentYear;
	
	CalendarTable(Window window)
	{
		this.window = window;
		calendar = new CalendarLogic(window);
		
		currentDay = window.getCurrentDay();
		currentMonth = window.getCurrentMonth();
		currentYear = window.getCurrentYear();
		
		panelTable = new JPanel(); 
	    panelTable.setBounds(10, 150, 560, 800);
	    
		initTable();
		table.addMouseListener(this);
		JLabel label = new JLabel("sdf");
		label.setBounds(200, 200, 200, 200);
		window.getFrame().add(label);
	}

	public void add()
	{
		window.getFrame().add(table);
	}
	
	void initTable()
	{
	
		table = new JTable(new MyTableModel(window));
		panelTable.add(table);

		window.getFrame().add(panelTable);
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
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
			// InsertMeetingWindow insert = new InsertMeetingWindow(window, calendar, row, column, currentYear, currentMonth, (int)day, window.getOptionsChoice(), alarmLogic);
			 // insert.init();	
			}
		}	
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
