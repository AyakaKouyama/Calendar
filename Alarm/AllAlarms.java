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
 * Okno zawieraj�ce list� wszystkich alarm�w. Umo�liwia usuwanie wybranych alarm�w za pomoc� przycisku "Usu�".
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
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
	 * Konstruktor klasy wyowu�uje metody do inicjalizacji okna, komponent�w oraz wype�nienia listy alarm�w.
	 * @param mode tryb pracy aplikacji ("XML"/"Do bazy")
	 * @param alarms klasa obs�uguj�ca list� alarm�w
	 */
	public AllAlarms(int mode, AlarmList alarms) 
	{
		this.alarms = alarms;
		initFrame();
		initComponents();
		fillList();
	}

	/**
	 * Metoda s�u��ca do inicjalizacji okna. Ustawia parametry takie jak np. rozmiar okna, jego nazwa.
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
	 * Metoda s�u��ca do inicjalizacji komponent�w (listy alarm�w oraz przycisk�w).
	 */
	public void initComponents()
	{
		list = new JList<String>(model);
		list.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollList = new JScrollPane(list);
		scrollList.setBounds(20, 20, 320, 200);
		frame.add(scrollList);

		remove = new JButton("Usu�");
		remove.setBounds(350, 60, 80, 30);
		remove.addActionListener(this);

		ok = new JButton("OK");
		ok.setBounds(350, 20, 80, 30);
		ok.addActionListener(this);

		frame.add(ok);
		frame.add(remove);
	}

	/**
	 * Metoda s�u��ca do wy�wietlania okna.
	 */
	public void show()
	{
		frame.setVisible(true);
	}

	/**
	 * Meotda wype�niaj�ca komponent "list" alarmami pobranymi od klasy AlarmList.
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
	 * Metoda obs�uguj�ca zdarzenia generowane przez naci�ni�cie przycisk�w. Wywo�uje metody s�u��ce do zamkni�cia okna, usuni�cia alarmu.
	 * @param e zdarzenia generwoane przez naci�ni�cie przycisku
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
