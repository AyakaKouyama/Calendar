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

/**
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */

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

	/**
	 * Konstruktor klasy; tworzy nowy obiekt oraz inicjalizuje komponenty potrzebne
	 * do wy�wietlania kalendarza (tabela, przyciski do zmiany miesi�ca/roku).
	 * 
	 * @param window
	 *            okno w kt�rym wy�wietlana jest tabela z zawarto�ci� kalendarza
	 * @see #initComponents()
	 * @see #initTable()
	 */
	public CalendarWindow(Window window)
	{
		currentYear = Calendar.getInstance().get(Calendar.YEAR);
		currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		theme = new Theme();
		panel = new JPanel();
		this.window = window;
		calendar = new CalendarLogic(this);

		initTable();
		initComponents();
	}

	/**
	 * Metoda do inicjalizacji przycisk�w s�u�acych do zmiany miesi�ca oraz roku
	 */
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

	/***
	 * 
	 * Metoda do inicjalizacji klaendarza. Tworzy obiekt tabeli oraz ustawia jej
	 * parametry - wymiary, spos�b renderowania kom�rek u�ywaj�c spersonalizowanego
	 * renderera.
	 * 
	 */
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

	/**
	 * Od�wie�a zawarto�� tabeli po zmianie motywu.
	 */
	public void refresh()
	{
		panel.remove(table);
		window.getFrame().remove(panel);
		window.getFrame().repaint();
		table = new JTable(new MyTableModel(this));
		initTable();
		table.addMouseListener(this);
	}

	/**
	 * @param e
	 *            zdarzenie generowane przez naci�ni�cie przycisku myszt Metoda do
	 *            obslugi zdarze� generwoanych przez naci�ni�cie myszy. Po
	 *            dwukrotnym naci�ni�ciu na kom�rk� w tabeli otwierane jest okno
	 *            zawieraj�ce szczeg�y spotkania.
	 */
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

	/**
	 * Metoda do wype�niania kom�rek tabeli odpowiednimi warto�ciami po zmianie
	 * roku/miesi�ca.
	 */
	public void updateCalendar()
	{
		for (int i = 0; i < 13; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				if (i % 2 == 0 && table.getValueAt(i, j) != null)
				{
					calendar.setCellValue(null, i, j);
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

	/**
	 * 
	 * @param value
	 *            warto��, kt�r� ma zosta� wype�niona kom�rka w tabeli
	 * @param row
	 *            rz�d, w kt�rym znajduje si� kom�rka tabeli
	 * @param column
	 *            kolumna, w kt�rej znajduje si� kom�rka tabeli
	 */
	public void fillCell(String value, int row, int column)
	{
		table.setValueAt(value, row, column);
	}

	/**
	 * 
	 * @param value
	 *            warto�� jaka ma zosta� przypisana do pola currentMonth Metoda do
	 *            ustawiania warto�ci pola cuurentMonth (obecny miesi�c w
	 *            kalendarzu)
	 */
	public void setCurrentMonth(int value)
	{
		currentMonth = value;
	}

	/**
	 * 
	 * @param value
	 *            warto�c jaka ma zosta� przypisana do pola currentDay Metoda do
	 *            ustawiania warto�ci pola currentDay (obecny dzie� w kalendarzu)
	 */
	public void setCurrentDay(int value)
	{
		currentDay = value;
	}

	/**
	 * 
	 * @param value
	 *            warto�c jaka ma zosta� przypisana do pola currentYear Metoda do
	 *            ustawiania warto�ci pola currentYear (obecny rok w kalendarzu)
	 */
	public void setCurrentYear(int value)
	{
		currentYear = value;
	}

	/**
	 * @param e
	 *            zdarzenie generowane przez naci�ni�cie na przycisk Metoda do
	 *            obs�ugi przycisk�w do zmiany miesi�ca/roku. Zmienia warto�� pola
	 *            currentYear oraz currentMonth, jednocze�nie zmieniaj�c warto��
	 *            wy�wietlnaego miesi�ca/roku. Aktualizuje zawarto�� kalendarza,
	 *            wywo�uj�c metod� updateCalendar().
	 */
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

	/**
	 * 
	 * @return obiekt CalendarLogic obs�uguj�cy logik� kalendarza
	 */
	public CalendarLogic getCalendar()
	{
		return calendar;
	}

	/**
	 * 
	 * @return warto�� aktualnego roku ustawionego w kalendarzu
	 */
	public int getYear()
	{
		return currentYear;
	}

	/**
	 * 
	 * @return warto�� aktualnego miesi�ca ustawionego w kalendarzu
	 */
	public int getMonth()
	{
		return currentMonth;
	}

	/**
	 * 
	 * @return warto�� aktualnego dnia ustawionego w kalendarzu
	 */
	public int getCurrentDay()
	{
		return currentDay;
	}

	/**
	 * 
	 * @return tryb pracy aplikacji ("XML" lub "Baza danych")
	 */
	public int getMode()
	{
		return window.getOptionWindow().getMode();
	}

	/**
	 * 
	 * @return mapa zawieraj�ca wszystkie spotkania
	 */
	public HashMap<Integer, MeetingObject> getMeeting()
	{
		return calendar.getObjectMeetings();
	}

	/**
	 * 
	 * @return okno kalendarza
	 */
	public CalendarWindow getCalendarWindow()
	{
		return this;
	}

	/**
	 * 
	 * @return obiekt odpowiedzialny za wype�nianie kolekcji zawieraj�cych spotkania
	 *         i manipulowanie nimi
	 */
	public FillMeetingData getFillMeetingData()
	{
		return calendar.getFillMeetingData();
	}

	/**
	 * 
	 * @author Sylwia Mieszkowska
	 * @author Anna Ciep�ucha
	 *
	 */
	private class MyRenderer extends JTextArea implements TableCellRenderer
	{
		private static final long serialVersionUID = 1L;
		private Color[] colors;

		/**
		 * Konstruktor klasy ustawiaj�cy styl zawijania linii w tabeli oraz odczytuj�cy
		 * wybrany motyw.
		 */
		public MyRenderer()
		{
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);

			String themeChoice = window.getOptionWindow().getTheme();
			theme.select(themeChoice);
			colors = theme.getTheme();
		}

		/**
		 * Metoda opisuj�ca spos�b renderowania poszczeg�lnych kom�rek (ich kolor,
		 * zawarto��, czcionk�).
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column)
		{

			if (calendar != null) calendar.fillCalendar(currentYear, currentMonth);

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

	/**
	 * Metoda wywo�uj�ca odpowiednie metody do zapisu stanu kalendarza
	 */
	public void saveData()
	{
		calendar.saveData();
	}
}
