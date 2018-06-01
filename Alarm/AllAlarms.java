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

/**
 * 
 * Okno zawieraj¹ce listê wszystkich alarmów. Umo¿liwia usuwanie wybranych alarmów za pomoc¹ przycisku "Usuñ".
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class AllAlarms implements ActionListener
{
	private JFrame frame;
	private JList<String> list;
	private JScrollPane scrollList;
	private JButton remove;
	private JButton ok;

	private DefaultListModel<String> model = new DefaultListModel<>();
	private AlarmList alarms;

	/**
	 * Konstruktor klasy wyowu³uje metody do inicjalizacji okna, komponentów oraz wype³nienia listy alarmów.
	 * @param mode tryb pracy aplikacji ("XML"/"Do bazy")
	 * @param alarms klasa obs³uguj¹ca listê alarmów
	 */
	public AllAlarms(int mode, AlarmList alarms) 
	{
		this.alarms = alarms;
		initFrame();
		initComponents();
		fillList();
	}

	/**
	 * Metoda s³u¿¹ca do inicjalizacji okna. Ustawia parametry takie jak np. rozmiar okna, jego nazwa.
	 */
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

	/**
	 * Metoda s³u¿¹ca do inicjalizacji komponentów (listy alarmów oraz przycisków).
	 */
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

	/**
	 * Metoda s³u¿¹ca do wyœwietlania okna.
	 */
	public void show()
	{
		frame.setVisible(true);
	}

	/**
	 * Meotda wype³niaj¹ca komponent "list" alarmami pobranymi od klasy AlarmList.
	 */
	public void fillList()
	{
		int size = alarms.getList().size();
		for (int i = 0; i < size; i++)
		{
			model.addElement(alarms.getList().get(i));
		}
	}

	
	/**
	 * Metoda obs³uguj¹ca zdarzenia generowane przez naciœniêcie przycisków. Wywo³uje metody s³u¿¹ce do zamkniêcia okna, usuniêcia alarmu.
	 * @param e zdarzenia generwoane przez naciœniêcie przycisku
	 */
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
