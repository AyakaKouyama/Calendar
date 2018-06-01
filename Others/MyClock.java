import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Klasa zawieraj¹ca komponenty aktualizuj¹ce siê co sekundê, reprezentuj¹ce zegar oraz aktualn¹ datê systemow¹.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class MyClock implements ActionListener
{
	private Timer timer;
	private int delay = 1000;
	private JLabel dateLabel;
	private JLabel currentDate;

	/**
	 * Konstruktor klasy.
	 * @param frame okno rodzic
	 */
	MyClock(Frame frame)
	{
		timer = new Timer(delay, this);
		timer.start();
		initComponents();
		frame.add(dateLabel);
		frame.add(currentDate);
	}
	
	/**
	 * Metoda inicjalizuj¹ca komponenty - napisy zawieraj¹ce datê i czas.
	 */
	private void initComponents()
	{
		dateLabel = new JLabel(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
		dateLabel.setBounds(610, 40, 200, 100);
		dateLabel.setFont(new Font("Arial", Font.BOLD, 45));

		currentDate = new JLabel(new SimpleDateFormat("EEE, d MMM yyyy").format(new Date(System.currentTimeMillis())));
		currentDate.setBounds(580, 10, 300, 50);
		currentDate.setFont(new Font("Arial", Font.BOLD, 30));

	}

	/**
	 * Metoda aktualizuj¹ca napisy co sekundê na podstawie timera.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String date = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
		String scuurentDate = new SimpleDateFormat("EEE, d MMM yyyy").format(new Date(System.currentTimeMillis()));
		dateLabel.setText(date);
		currentDate.setText(scuurentDate);
	}

}
