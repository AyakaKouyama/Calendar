import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel
{

	Object[][] data = {
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null},
		};

    
    String[] columnNames = {"Pon", "Wto", "Œr", "Czw", "Pt", "Sob", "Niedz"};


    MyTableModel(Window window)
    {
    	int x = 1;
    	
    	for(int i = 0; i<7; i++)
    	{
    		data[0][i] = columnNames[i];
    	}
    	
    	CalendarLogic calendar = new CalendarLogic(window);
    	data = calendar.fillCalendar();
    	for(int i = 0; i<7; i++)
    	{
    		data[0][i] = columnNames[i];
    	}
    }

	@Override
	public int getColumnCount() 
	{
		return columnNames.length;
	}

	@Override
	public int getRowCount()
	{
		return data.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) 
	{
		return data[arg0][arg1];
	}
	
    public void setValueAt(Object value, int row, int col)
    {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
	
    public boolean isCellEditable (int rowIndex, int columnIndex)
    {
    	if(rowIndex == 0)
    	{
    		return false;
    	}
    	if(rowIndex%2 == 1)
    	{
    		return false;
    	}

    	return false;
    }
}
