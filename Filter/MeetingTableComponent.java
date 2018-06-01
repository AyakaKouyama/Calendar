import java.awt.Font;
import java.awt.Frame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Klasa zawieraj�ca tabel� wszystkich spotka� (umieszczona w oknie klasy
 * MeetingsFilter).
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class MeetingTableComponent
{
	private Frame frame;
	private JTable table;
	private JScrollPane scrollList;

	private String[] columnNames =
	{ "Nazwa", "Lokalizacja", "Data", "Szczeg�y" };
	private String name, localization, time, details;
	private Object[][] data;
	private Object[][] oryginalData;
	private int size;

	private FillMeetingData meetingData;
	private Window window;
	private MeetingsFilterLogic logic;

	/**
	 * Konstruktor klasy.Wywo�uje metody do inicjalizacji tabeli oraz wype�nienia
	 * ich danymi.
	 * 
	 * @param frame
	 *            okno rodzic
	 * @param window
	 *            okno g��wne
	 * @param meetingData
	 *            klasa operuj�ca na listach zawieraj�cych spotkania
	 */
	MeetingTableComponent(Frame frame, Window window, FillMeetingData meetingData)
	{
		this.frame = frame;
		this.window = window;
		this.meetingData = meetingData;

		size = meetingData.getMap().size();
		data = new Object[meetingData.getMap().size()][4];
		logic = new MeetingsFilterLogic(window.getCalendarWindow().getCalendar().getFillMeetingData());

		fillList();
		oryginalData = data;
		initComponents();

	}

	/**
	 * Metoda do inicjalizacji tabeli. Tworzy now� tabel� i ustawia jej parametry
	 * m.in czcionk�, jej rozmiar, rozmiar tabeli.
	 */
	private void initComponents()
	{
		DefaultTableModel model = new DefaultTableModel(data, columnNames)
		{

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};

		table = new JTable(model);
		table.setFont(new Font("Arial", Font.PLAIN, 16));
		table.setRowHeight(20);
		scrollList = new JScrollPane(table);
		scrollList.setBounds(20, 20, 550, 200);
	}

	/**
	 * Metoda s�u��ca do wy�wietlania okna.
	 */
	public void show()
	{
		frame.add(scrollList);
	}

	/**
	 * Metoda wype�niaj�ca tabel� spotka� danymi.
	 */
	public void fillList()
	{
		meetingData.getAllIDs();
		for (int i = 0; i < size; i++)
		{
			cellValues(meetingData.getAllIDs().get(i));
			getDate(meetingData.getAllIDs().get(i));
			data[i][0] = name;
			data[i][1] = localization;
			data[i][2] = getDate(meetingData.getAllIDs().get(i));
			data[i][3] = details;

		}
	}

	/**
	 * metoda s�u��ca do od�wie�ania stanu tabeli po dokonaniu modyfikacji, np.
	 * przefiltrowaniu danych.
	 */
	public void reFill()
	{
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				table.getModel().setValueAt(data[i][j], i, j);
			}
		}

	}

	/**
	 * Metoda wywo�uj�ca odpowiednie metody s�u��ce do usuni�cia przefiltrowanych
	 * wynik�w.
	 */
	public void remove()
	{
		data = logic.remove(data, meetingData.getMap().size());
		reFill();
		window.getCalendarWindow().updateCalendar();
	}

	/**
	 * Metoda wywo�uj�ca odpowiednie metody s�u��ce do usuni�cia jednego, wbranego
	 * rekordu.
	 */
	public void removeOne()
	{
		data = logic.removeOne(data, table.getSelectedRow(), meetingData.getMap().size());
		reFill();
		window.getCalendarWindow().updateCalendar();
		size--;
	}

	/**
	 * 
	 * Metoda przekazuj�ca informacje o parametrach spotkania wywo�ywana podczas
	 * wype�niania listy.
	 * 
	 * @param id
	 *            id spotkania
	 */
	public void cellValues(int id)
	{
		String meeting = meetingData.getMap().get(id);

		int[] index = new int[5];
		index[0] = meeting.indexOf(':');

		for (int i = 1; i < 5; i++)
		{
			index[i] = meeting.indexOf(':', (index[i - 1] + 1));
		}

		if (index[4] == -1)
		{
			name = meeting.substring(index[0] + 1, index[1] - "Lokalizacja".length() - 1);
			localization = meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1);
			time = meeting.substring(index[2] + 1, index[3] - "Szczeg�y".length() - 1);
			details = meeting.substring(index[3] + 1, meeting.length());
		} else
		{
			name = meeting.substring(index[0] + 1, index[1] - "Lokalizacja".length() - 1);
			localization = meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1);
			time = meeting.substring(index[3] - 2, index[4] - "Szczeg�y".length() - 1);
			details = meeting.substring(index[4] + 1, meeting.length());
		}

	}

	/**
	 * Metoda odczytuj�ca dat� spotkania na podstawie jego id i konwertuj�ca id na
	 * odpowiedni �a�cuch znak�w.
	 * 
	 * @param id
	 *            id spotkania
	 * @return �a�cuch znak�w b�d�cy dat� spotkania
	 */
	public String getDate(int id)
	{
		String code = Integer.toString(id);
		String date = null;
		if (code.length() == 4)
		{
			date = "0" + code.substring(0, 1) + "/" + code.substring(1, 2) + "/20" + code.substring(2, 4);
		} else if (code.length() == 5)
		{
			date = code.substring(0, 2) + "/" + code.substring(2, 3) + "/20" + code.substring(3, 5);
		}

		String result = date + " " + time;
		return result;
	}

	/**
	 * Metoda wywo�uj�ca odpowiednie metody klasy logiki s�u��ce do sortowania
	 * danych.
	 * 
	 * @param value
	 *            warto�� wg kt�rej sortowane maj� by� dane
	 * @param order
	 *            porz�dek malej�cy/rosn�cy
	 */
	public void sort(String value, int order)
	{
		data = logic.sort(data, size, value, order);
		reFill();
	}

	/**
	 * Metoda zwracaj�ca zawarto�� tabeli spotka�.
	 * @return zawarto�� tabeli spotka�
	 */
	public Object[][] getData()
	{
		return data;
	}

	/**
	 * Metoda ustawiaj�ca now� warto�� do obiektu reprezentuj�cego spotkania.
	 * @param obj
	 *            jaka ma zosta� przypisana do pola data zawieraj�cego dane o
	 *            spotkaniach
	 */
	public void setData(Object[][] obj)
	{
		data = obj;
	}

	/**
	 * 
	 * Metoda przywracaj�ca domy�lne ustawienia (usuwa wszystkie filtry).
	 */
	public void resetData()
	{
		data = oryginalData;
	}

}
