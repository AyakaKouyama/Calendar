import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class MeetingsFilter implements ActionListener
{
	private JFrame frame;
	private JButton ok;
	private JButton up;
	private JButton down;
	private JButton remove;
	private JButton findOlder;
	private JButton removeSlelected;
	private JButton removeOne;
	private JButton findField;
	private JLabel findL;
	private JLabel olderL;
	private JLabel sortL;
	private JTextField find;
	private JTextField olderThan;
	private Choice findChoice;
	private Choice choice;

	private int order = 1;

	private MeetingTableComponent table;
	private MeetingsFilterLogic logic;
	private Window window;
	private FillMeetingData meetingData;

	/**
	 * Kontruktor klasy inicjalizuje komponety oraz okno.
	 * 
	 * @param window
	 *            okno rodzic
	 */
	public MeetingsFilter(Window window)
	{
		this.window = window;
		logic = new MeetingsFilterLogic(window.getCalendarWindow().getCalendar().getFillMeetingData());
		meetingData = window.getCalendarWindow().getCalendar().getFillMeetingData();
		initFrame();
		initComponents();
	}

	/**
	 * Metoda do inicjalizacji okna. Ustawia parametry takie jak jego rozmiar oraz
	 * nazwa.
	 */
	private void initFrame()
	{
		frame = new JFrame();
		frame.setTitle("Spotkania");
		frame.setIconImage(new ImageIcon("calendar.png").getImage());
		frame.setResizable(false);
		frame.setLayout(null);
		frame.pack();
		frame.setSize(600, 430);
	}

	/**
	 * Metoda s³u¿aca do inicjalizacji komponentów (przycisków, pól tekstowych, list
	 * wyboru).
	 */
	private void initComponents()
	{

		table = new MeetingTableComponent(frame, window, meetingData);
		table.show();

		ok = new JButton("Ok");
		setButton(ok, 490, 350, 80, 30);

		remove = new JButton("Usuñ filtry");
		setButton(remove, 350, 350, 100, 30);

		removeSlelected = new JButton("Usuñ wyniki");
		setButton(removeSlelected, 195, 350, 120, 30);

		findL = new JLabel("Szukaj:");
		findL.setBounds(20, 230, 80, 30);
		findL.setFont(new Font("Arial", Font.PLAIN, 15));
		frame.add(findL);

		olderL = new JLabel("Wydarzenia strasze ni¿:");
		olderL.setBounds(20, 260, 200, 30);
		olderL.setFont(new Font("Arial", Font.PLAIN, 15));
		frame.add(olderL);

		sortL = new JLabel("Sortuj wg:");
		sortL.setBounds(20, 290, 100, 30);
		sortL.setFont(new Font("Arial", Font.PLAIN, 15));
		frame.add(sortL);

		up = new JButton("\u25b2");
		setButton(up, 250, 295, 80, 20);
		down = new JButton("\u25bc");
		setButton(down, 340, 295, 80, 20);
		choice = new Choice();
		setChoice(choice, 120, 295, 100, 30);
		findChoice = new Choice();
		setChoice(findChoice, 220, 235, 80, 30);
		findOlder = new JButton("Szukaj");
		setButton(findOlder, 340, 265, 80, 20);
		removeOne = new JButton("Usuñ zaznaczone");
		setButton(removeOne, 20, 350, 140, 30);
		findField = new JButton("Szukaj");
		setButton(findField, 340, 235, 80, 20);

		find = new JTextField(100);
		find.setBounds(100, 235, 100, 20);
		find.addActionListener(this);
		frame.add(find);

		olderThan = new JTextField(20);
		olderThan.setBounds(200, 265, 100, 20);
		olderThan.setText((new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()))));
		olderThan.addActionListener(this);
		frame.add(olderThan);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Metoda s³u¿ca do wyœwietlania okna.
	 */
	public void show()
	{
		frame.setVisible(true);
	}

	/**
	 * Metoda s³u¿¹ca do ustawiania parametrów przycisku.
	 * 
	 * @param button
	 *            obiekt przycisku, który ma byæ zmodyfikowany
	 * @param x
	 *            pozycja x przycisku
	 * @param y
	 *            pozycja y przycisku
	 * @param width
	 *            szerokoœæ przycisku
	 * @param height
	 *            wysokoœæ przycisku
	 */
	public void setButton(JButton button, int x, int y, int width, int height)
	{
		button.setBounds(x, y, width, height);
		button.addActionListener(this);
		frame.add(button);

	}

	/**
	 * Metoda s³u¿¹ca do ustawiania parametrów listy wyboru.
	 * 
	 * @param choice
	 *            obiekt listy, który ma byæ zmodyfikowany
	 * @param x
	 *            pozycja x listy
	 * @param y
	 *            pozycja y listy
	 * @param width
	 *            szerokoœæ przycisku
	 * @param height
	 *            wysokoœæ przycisku
	 */
	public void setChoice(Choice choice, int x, int y, int width, int height)
	{
		choice.add("Nazwa");
		choice.add("Lokazlizacja");
		choice.add("Data");
		choice.add("Szczegó³y");

		choice.select(2);
		choice.setBounds(x, y, width, height);
		frame.add(choice);

	}

	/**
	 * Metoda do obs³ugi zdarzeñ generowanych przez wciœniêcie przycisku. Wywo³uje
	 * odpowiednie metody s³u¿¹ce do sortowania, usuwania, filtrowania spotkañ.
	 * 
	 * @param e
	 *            zdarzenia generowane przez naciœniêcie przycisku
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == ok)
		{
			frame.dispose();
		}

		if (source == up)
		{
			order = 1;
			table.sort(choice.getSelectedItem(), order);
		}
		if (source == down)
		{
			order = -1;
			table.sort(choice.getSelectedItem(), order);
		}
		if (source == remove)
		{
			table.resetData();
			table.reFill();

		}
		if (source == findOlder)
		{
			table.setData(logic.findOlderThan(table.getData(), meetingData.getMap().size(), olderThan.getText()));
			table.reFill();
		}
		if (source == removeSlelected)
		{
			RemoveWarning warning = new RemoveWarning();
			warning.show(frame);
			if (warning.getAnswer() == true)
			{
				table.remove();
			}

		}
		if (source == removeOne)
		{
			table.removeOne();
		}
		if (source == findField)
		{
			table.resetData();
			table.setData(logic.find(table.getData(), findChoice.getSelectedItem(), meetingData.getMap().size(),
					find.getText()));
			table.reFill();
		}
	}

}
