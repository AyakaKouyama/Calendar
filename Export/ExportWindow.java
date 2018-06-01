import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ExportWindow 
{
	private JFileChooser choose;
	private JFrame frame;
	private ExportLogic logic;
	
	ExportWindow(JFrame frame, FillMeetingData data)
	{
		choose = new JFileChooser();
			try
			{
				logic = new ExportLogic(data);
			} catch (ClassNotFoundException | SQLException e)
			{
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
