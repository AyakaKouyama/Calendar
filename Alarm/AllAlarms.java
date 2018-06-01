import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class AllAlarms implements ActionListener
{
	private JFrame frame;
	private JList<String> list;
	private JScrollPane scrollList;
	private JButton remove;
	private JButton ok;

	private DefaultListModel<String> model = new DefaultListModel<>();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat newformat = new SimpleDateFormat("dd.MM.yyyy    HH:mm");

	private AlarmList alarms;

	AllAlarms(int mode, AlarmList alarms) throws ClassNotFoundException, SQLException
	{

		this.alarms = alarms;
		initFrame();
		initComponents();
		fillList();

	}

	private void initFrame()
	{
		frame = new JFrame();
		frame.setTitle("Alarmy");
		frame.setIconImage(new ImageIcon("calendar.png").getImage());
		frame.setResizable(false);
		frame.setLayout(null);

		frame.pack();
		frame.setSize(450, 300);
		frame.setLocationRelativeTo(null);
	}

	public void initComponents()
	{
		list = new JList<String>(model);
		list.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollList = new JScrollPane(list);
		scrollList.setBounds(20, 20, 320, 200);
		frame.add(scrollList);

		remove = new JButton("Usuñ");
		remove.setBounds(350, 60, 80, 30);
		remove.addActionListener(this);

		ok = new JButton("OK");
		ok.setBounds(350, 20, 80, 30);
		ok.addActionListener(this);

		frame.add(ok);
		frame.add(remove);
	}

	public void show()
	{
		frame.setVisible(true);
	}

	public void fillList()
	{
		//alarms.fill();
		int size = alarms.getList().size();
		for (int i = 0; i < size; i++)
		{
			model.addElement(alarms.getList().get(i));
		}
	}

	public String parseDate(String date)
	{

		Calendar cal = Calendar.getInstance();
		try
		{
			cal.setTime(format.parse(date));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		return newformat.format(cal.getTime());

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == remove)
		{
			alarms.removeAlarm(list.getSelectedValue());
			model.removeElement(list.getSelectedValue());
		}
		if (source == ok)
		{
			frame.dispose();
		}

	}
}
