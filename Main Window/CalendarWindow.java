import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class CalendarWindow extends MouseAdapter implements ActionListener
{
	private JButton next;
	private JButton prev;
	private JButton nextMonth;
	private JButton prevMonth;
	private JTable table;
	private JPanel panel;
	private JLabel year;
	private JLabel month;

	private int currentYear;
	private int currentMonth;
	private int currentDay;

	private Theme theme;
	private Window window;
	private CalendarLogic calendar;

	CalendarWindow(Window window)
	{
		currentYear = Calendar.getInstance().get(Calendar.YEAR);
		currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		theme = new Theme();
		panel = new JPanel();
		this.window = window;

		try
		{
			calendar = new CalendarLogic(this);
		} catch (ClassNotFoundException | SQLException e)
		{
			ConnectionError error = new ConnectionError();
			error.show(window.getFrame());
		}

		initTable();
		initComponents();
	}

	private void initComponents()
	{
		panel.setBounds(20, 100, 560, 800);
		window.getFrame().add(panel);

		next = new JButton("\u25b6");
		prev = new JButton("\u25c0");
		nextMonth = new JButton("\u25b6");
		prevMonth = new JButton("\u25c0");

		table.addMouseListener(this);
		next.addActionListener(this);
		prev.addActionListener(this);
		nextMonth.addActionListener(this);
		prevMonth.addActionListener(this);

		next.setBounds(375, 25, 50, 20);
		window.getFrame().add(next);
		prev.setBounds(175, 25, 50, 20);
		window.getFrame().add(prev);
		nextMonth.setBounds(375, 60, 50, 20);
		window.getFrame().add(nextMonth);
		prevMonth.setBounds(175, 60, 50, 20);
		window.getFrame().add(prevMonth);

		year = new JLabel(Integer.toString(currentYear));
		month = new JLabel(Integer.toString(currentMonth));

		year.setBounds(280, 20, 80, 30);
		year.setFont(new Font("Arial", Font.BOLD, 20));
		window.getFrame().add(year);
		month.setBounds(298, 55, 80, 30);
		month.setFont(new Font("Arial", Font.BOLD, 20));
		window.getFrame().add(month);
	}

	void initTable()
	{
		table = new JTable(new MyTableModel(this));
		panel.add(table);
		window.getFrame().add(panel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 0; i < 13; i++)
		{
			if (i % 2 == 1)
			{
				table.setRowHeight(i, 30);
			} else
			{
				table.setRowHeight(i, 60);
			}
		}

		TableColumnModel model = table.getColumnModel();
		MyRenderer renderer = new MyRenderer();

		for (int i = 0; i < 7; i++)
		{
			model.getColumn(i).setPreferredWidth(80);
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

	}

	public void refresh()
	{
		panel.remove(table);
		window.getFrame().remove(panel);
		window.getFrame().repaint();
		table = new JTable(new MyTableModel(this));
		initTable();
		table.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		Point point = e.getPoint();
		int row = table.rowAtPoint(point);
		int column = table.columnAtPoint(point);

		if (e.getClickCount() == 2 && table.getSelectedRow() % 2 == 0 && row != 0)
		{
			Object day = table.getValueAt(row - 1, column);
			if (day != null)
			{
				InsertMeetingWindow insert = new InsertMeetingWindow(window, calendar, row, column, currentYear,
						currentMonth, (int) day, window.getOptionWindow().getChoice(), window.getAlarmLogic());
				insert.show();
			}
		}
	}

	public void updateCalendar()
	{
		for (int i = 0; i < 13; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				if (i % 2 == 0 && table.getValueAt(i, j) != null)
				{
					calendar.setObject(null, i, j);
				}
			}
		}

		calendar.fillCalendar(currentYear, currentMonth);
		for (int i = 1; i < 13; i++)
		{
			for (int j = 0; j < 7; j++)
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

		if (source == next)
		{
			year.setText(Integer.toString(currentYear + 1));
			currentYear += 1;
		}
		if (source == prev)
		{
			year.setText(Integer.toString(currentYear - 1));
			currentYear -= 1;
		}
		if (source == nextMonth)
		{
			if ((currentMonth + 1) == 13)
			{
				year.setText(Integer.toString(currentYear + 1));
				month.setText("1");
				currentYear += 1;
				currentMonth = 1;
			} else
			{
				month.setText(Integer.toString(currentMonth + 1));
				currentMonth += 1;
			}

		}
		if (source == prevMonth)
		{
			if ((currentMonth - 1) == 0)
			{
				year.setText(Integer.toString(currentYear - 1));
				currentYear -= 1;
				month.setText("12");
				currentMonth = 12;

			} else
			{
				month.setText(Integer.toString(currentMonth - 1));
				currentMonth -= 1;
			}

		}

		if (source == prevMonth || source == nextMonth || source == next || source == prev)
		{
			updateCalendar();
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

	public int getMode()
	{
		return window.getOptionWindow().getMode();
	}

	public HashMap<Integer, MeetingObject> getMeeting()
	{
		return calendar.getObjectMeetings();
	}

	public CalendarWindow getCalendarWindow()
	{
		return this;
	}

	public FillMeetingData getFillMeetingData()
	{
		return calendar.getFillMeetingData();
	}

	private class MyRenderer extends JTextArea implements TableCellRenderer
	{
		private static final long serialVersionUID = 1L;
		private Color[] colors;

		MyRenderer()
		{
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);

			String themeChoice = window.getOptionWindow().getTheme();
			theme.select(themeChoice);
			colors = theme.getTheme();
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column)
		{

			calendar.fillCalendar(currentYear, currentMonth);

			if (row == 0)
			{
				setBackground(colors[0]);
				setBackground(colors[0]);
			} else if (row == calendar.currentDayRow(currentDay) && column == calendar.currentDayColumn(currentDay)
					&& currentYear == Calendar.getInstance().get(Calendar.YEAR)
					&& currentMonth == Calendar.getInstance().get(Calendar.MONTH) + 1)
			{
				setBackground(colors[1]);
				setBackground(colors[1]);
			} else if (row == calendar.currentDayRow(currentDay) + 1 && column == calendar.currentDayColumn(currentDay)
					&& currentYear == Calendar.getInstance().get(Calendar.YEAR)
					&& currentMonth == Calendar.getInstance().get(Calendar.MONTH) + 1)
			{
				setBackground(colors[2]);
				setBackground(colors[2]);
			} else
			{
				setBackground(colors[3]);
				setBackground(colors[3]);
			}

			if (row == 0 || row % 2 == 1)
			{
				setFont(new Font("Calibri", Font.BOLD, 20));
			} else
			{
				setFont(new Font("Calibri", Font.PLAIN, 15));
			}

			if (hasFocus == true && row % 2 == 0 && row != 0)
			{
				setBackground(colors[2]);
			}
			setText((value == null) ? "" : value.toString());

			return this;
		}
	}
}
