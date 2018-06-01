import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AlarmClock implements ActionListener
{
	private JFrame frame;
	private JButton ok;
	private JButton cancel;
	private JLabel dateLabel;
	private JLabel timeLabel;
	private JFormattedTextField date;
	private JFormattedTextField time;
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	private boolean accteped = false;
	private String sDate;
	private String formattedDate;

	private Window window;
	private AlarmClockLogic music;

	AlarmClock(Window window, AlarmClockLogic music)
	{
		this.window = window;
		this.music = music;
		initFrame();
		initComponents();
	}

	private void initFrame()
	{
		frame = new JFrame();
		frame.setTitle("Alarm Clock");
		frame.setIconImage(new ImageIcon("calendar.png").getImage());
		frame.setResizable(false);
		frame.setLayout(null);
		frame.pack();
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
	}

	private void initComponents()
	{
		dateLabel = new JLabel("Data: (dd/mm/yyyy)");
		timeLabel = new JLabel("Godzina: (hh:mm)");

		dateLabel.setBounds(150, 0, 200, 50);
		frame.add(dateLabel);
		date = new JFormattedTextField(dateFormat);
		date.setText((new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()))));
		date.setBounds(100, 40, 200, 40);
		date.setFont(new Font("Calibri", Font.PLAIN, 20));
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.addActionListener(this);
		frame.add(date);

		timeLabel.setBounds(150, 80, 200, 50);
		frame.add(timeLabel);
		time = new JFormattedTextField(timeFormat);
		time.setText((new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()))));
		time.setBounds(100, 120, 200, 40);
		time.setFont(new Font("Calibri", Font.PLAIN, 20));
		time.setHorizontalAlignment(SwingConstants.CENTER);
		time.addActionListener(this);
		frame.add(time);

		ok = new JButton("Ok");
		ok.setBounds(220, 200, 80, 30);
		ok.addActionListener(this);
		frame.add(ok);

		cancel = new JButton("Anuluj");
		cancel.setBounds(100, 200, 80, 30);
		cancel.addActionListener(this);
		frame.add(cancel);
	}

	public void add()
	{
		frame.setVisible(true);
	}

	public void updateDate()
	{
		date.setText((new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()))));
		time.setText((new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()))));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == ok)
		{
			accteped = true;
			AlarmInfoDialog alarmInfo = new AlarmInfoDialog(getDate());

			if (alarmInfo.show(window.getFrame()) == true)
			{
				music.addAlarm(formattedDate);
				music.addAlarmToList(formattedDate);
				frame.dispose();
			}

		}

		if (source == cancel)
		{
			frame.dispose();
		}

	}

	public Calendar getDate()
	{
		if (accteped == true)
		{
			try
			{
				Calendar cal = Calendar.getInstance();
				cal.setTime(format.parse(date.getText() + " " + time.getText()));
			
				SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00.0");
				Calendar formatted = Calendar.getInstance();
				formatted.setTime(format.parse(date.getText() + " " + time.getText()));
				formattedDate = newformat.format(formatted.getTime());
				
				return cal;
			} catch (java.text.ParseException e)
			{
				e.printStackTrace();
			}
		}

		return null;
	}
	
}
