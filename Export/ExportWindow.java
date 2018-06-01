import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Okno wyboru œcie¿ki pliku do zapisu pliku .csv.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class ExportWindow 
{
	private JFileChooser choose;
	private JFrame frame;
	private ExportLogic logic;
	
	/**
	 * Konstruktor klasy.
	 * @param frame okno rodzic
	 * @param data klasa zwieraj¹ca dane potrzebne do zapisu do pliku
	 */
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
	
	/**
	 * Meotda wywo³uj¹ca metodê zapisu danych o spotkaniach.
	 */
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
