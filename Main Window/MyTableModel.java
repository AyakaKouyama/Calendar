import javax.swing.table.AbstractTableModel;

/**
 * Klasa definiuj�ca model tabeli.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class MyTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	private Object[][] data =
	{
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null }, };

	private String[] columnNames =
	{ "Pon", "Wto", "�r", "Czw", "Pt", "Sob", "Niedz" };
	private CalendarLogic calendar;

	/**
	 * Konstruktor klasy.
	 * 
	 * @param window
	 *            okno rodzic
	 */
	MyTableModel(CalendarWindow window)
	{
		this.calendar = window.getCalendar();
		if (calendar != null) data = calendar.fillCalendar(window.getYear(), window.getMonth());

		for (int i = 0; i < 7; i++)
		{
			data[0][i] = columnNames[i];
		}
	}

	/**
	 * Metoda zwracj�ca ilo�� kolumn tabeli.
	 */
	@Override
	public int getColumnCount()
	{
		return columnNames.length;
	}

	/**
	 * Metoda zwracj�ca ilo�� rz�d�w tabeli.
	 */
	@Override
	public int getRowCount()
	{
		return data.length;
	}

	/**
	 * Metoda zwracaj�ca zawarto�� kom�rki na konkretnej pozycji
	 */
	@Override
	public Object getValueAt(int arg0, int arg1)
	{
		return data[arg0][arg1];
	}

	/**
	 * Metoda ustawiaj�ca warto�� kom�rki na konkretnej pozycji.
	 */
	public void setValueAt(Object value, int row, int col)
	{
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	/**
	 * Metoda okre�laj�ca czy dana kom�rka tabeli jest edytowalna.
	 * @return true w przypadku, gdy mo�na edytowa� kom�rk�, w przeciwnym wypadku false
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}
}
