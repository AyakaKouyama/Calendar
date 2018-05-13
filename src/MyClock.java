import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.Timer;

public class MyClock implements ActionListener
{
	Timer timer;
	int delay = 1000;
	JLabel dateLabel;
	JLabel currentDate;
	
	MyClock(Frame frame)
	{
		timer = new Timer(delay, this);
		timer.start();
		dateLabel = new JLabel(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
		dateLabel.setBounds(750, 65, 200, 100);
		dateLabel.setFont(new Font("Calibri", Font.BOLD, 35));
		
		currentDate = new JLabel(new SimpleDateFormat("EEE, d MMM yyyy").format(new Date(System.currentTimeMillis())));
		currentDate.setBounds(700, 20, 300, 100);
		currentDate.setFont(new Font("Calibri", Font.BOLD, 30));

		frame.add(dateLabel);
		frame.add(currentDate);
	}
	

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			  String date = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
			  String scuurentDate = new SimpleDateFormat("EEE, d MMM yyyy").format(new Date(System.currentTimeMillis()));
			  dateLabel.setText(date);
			  currentDate.setText(scuurentDate);
		}
		

}
