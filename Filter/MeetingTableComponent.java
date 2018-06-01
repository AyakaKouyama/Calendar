import java.awt.Font;
import java.awt.Frame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Klasa zawieraj¹ca tabelê wszystkich spotkañ (umieszczona w oknie klasy
 * MeetingsFilter).
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class MeetingTableComponent
{
	private Frame frame;
	private JTable table;
	private JScrollPane scrollList;

	private String[] columnNames =
	{ "Nazwa", "Lokalizacja", "Data", "Szczegó³y" };
	private String name, localization, time, details;
	private Object[][] data;
	private Object[][] oryginalData;
	private int size;

	private FillMeetingData meetingData;
	private Window window;
	private MeetingsFilterLogic logic;

	/**
	 * Konstruktor klasy.Wywo³uje metody do inicjalizacji tabeli oraz wype³nienia
	 * ich danymi.
	 * 
	 * @param frame
	 *            okno rodzic
	 * @param window
	 *            okno g³ówne
	 * @param meetingData
	 *            klasa operuj¹ca na listach zawieraj¹cych spotkania
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
	 * Metoda do inicjalizacji tabeli. Tworzy now¹ tabelê i ustawia jej parametry
	 * m.in czcionkê, jej rozmiar, rozmiar tabeli.
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
	 * Metoda s³u¿¹ca do wyœwietlania okna.
	 */
	public void show()
	{
		frame.add(scrollList);
	}

	/**
	 * Metoda wype³niaj¹ca tabelê spotkañ danymi.
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
	 * metoda s³u¿¹ca do odœwie¿ania stanu tabeli po dokonaniu modyfikacji, np.
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
	 * Metoda wywo³uj¹ca odpowiednie metody s³u¿¹ce do usuniêcia przefiltrowanych
	 * wyników.
	 */
	public void remove()
	{
		data = logic.remove(data, meetingData.getMap().size());
		reFill();
		window.getCalendarWindow().updateCalendar();
	}

	/**
	 * Metoda wywo³uj¹ca odpowiednie metody s³u¿¹ce do usuniêcia jednego, wbranego
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
	 * Metoda przekazuj¹ca informacje o parametrach spotkania wywo³ywana podczas
	 * wype³niania listy.
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
			time = meeting.substring(index[2] + 1, index[3] - "Szczegó³y".length() - 1);
			details = meeting.substring(index[3] + 1, meeting.length());
		} else
		{
			name = meeting.substring(index[0] + 1, index[1] - "Lokalizacja".length() - 1);
			localization = meeting.substring(index[1] + 1, index[2] - "Godzina".length() - 1);
			time = meeting.substring(index[3] - 2, index[4] - "Szczegó³y".length() - 1);
			details = meeting.substring(index[4] + 1, meeting.length());
		}

	}

	/**
	 * Metoda odczytuj¹ca datê spotkania na podstawie jego id i konwertuj¹ca id na
	 * odpowiedni ³añcuch znaków.
	 * 
	 * @param id
	 *            id spotkania
	 * @return ³añcuch znaków bêd¹cy dat¹ spotkania
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
	 * Metoda wywo³uj¹ca odpowiednie metody klasy logiki s³u¿¹ce do sortowania
	 * danych.
	 * 
	 * @param value
	 *            wartoœæ wg której sortowane maj¹ byæ dane
	 * @param order
	 *            porz¹dek malej¹cy/rosn¹cy
	 */
	public void sort(String value, int order)
	{
		data = logic.sort(data, size, value, order);
		reFill();
	}

	/**
	 * Metoda zwracaj¹ca zawartoœæ tabeli spotkañ.
	 * @return zawartoœæ tabeli spotkañ
	 */
	public Object[][] getData()
	{
		return data;
	}

	/**
	 * Metoda ustawiaj¹ca now¹ wartoœæ do obiektu reprezentuj¹cego spotkania.
	 * @param obj
	 *            jaka ma zostaæ przypisana do pola data zawieraj¹cego dane o
	 *            spotkaniach
	 */
	public void setData(Object[][] obj)
	{
		data = obj;
	}

	/**
	 * 
	 * Metoda przywracaj¹ca domyœlne ustawienia (usuwa wszystkie filtry).
	 */
	public void resetData()
	{
		data = oryginalData;
	}

}
