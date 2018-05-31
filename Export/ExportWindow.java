import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ExportWindow 
{
	JFileChooser choose;
	JFrame frame;
	ExportLogic logic;
	
	ExportWindow(JFrame frame)
	{
		choose = new JFileChooser();
			try
			{
				logic = new ExportLogic();
			} catch (ClassNotFoundException | SQLException e)
			{
				ConnectionError error = new ConnectionError();
				error.show(frame);
			}
		
		this.frame = frame;
	}
	
	public void saveCSV() 
	{
		int returnValue = choose.showSaveDialog(frame);
		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			try 
			{
				logic.save(choose.getSelectedFile());
				FileSaveSuccess success = new FileSaveSuccess();
				success.show(frame);
			} 
			catch (IOException e)
			{
				FileSaveError error = new FileSaveError();
				error.show(frame);
				e.printStackTrace();
			}
		}
	}
}
