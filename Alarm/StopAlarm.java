import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Okno zatrzymania alarmu. Pojawia siê w momencie uruchomienia alarmu. Posiada
 * mo¿liwoœc wy³¹czenia alramu lub ustawienia drzemki.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class StopAlarm implements ActionListener, ItemListener
{
	private JFrame frame;
	private JButton stop;
	private JButton nap;
	private Choice choice;

	private int napTime;
	private boolean napVlaue = false;

	private AlarmClockLogic alarmClock;

	StopAlarm(AlarmClockLogic alarmClock)
	{
		this.alarmClock = alarmClock;
		napTime = 5;
		initFrame();
		initComponents();
	}

	private void initFrame()
	{
		frame = new JFrame();
		frame.setTitle("Meeting");
		frame.setResizable(false);
		frame.setLayout(null);
		frame.pack();
		frame.setSize(250, 200);
		frame.setLocationRelativeTo(null);
	}

	private void initComponents()
	{
		stop = new JButton("Stop");
		stop.setBounds(60, 30, 120, 30);
		stop.addActionListener(this);

		nap = new JButton("Drzemka");
		nap.setBounds(60, 70, 120, 30);
		nap.addActionListener(this);

		choice = new Choice();
		choice.setBounds(70, 110, 100, 30);
		choice.addItemListener(this);
		choice.add("5 min");
		choice.add("1 min");
		choice.add("10 min");
		choice.add("15 min");
		choice.add("30 min");

		frame.add(choice);
		frame.add(nap);
		frame.add(stop);
	}

	public void show()
	{
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == stop)
		{
			alarmClock.stopMusic();
			napVlaue = false;
			frame.dispose();
		}
		if (source == nap)
		{

			alarmClock.stopMusic();
			napVlaue = true;
			frame.dispose();
		}

	}

	public boolean getNapValue()
	{
		return napVlaue;
	}

	public void setNapValue(boolean value)
	{
		napVlaue = value;
	}

	public int getNapTime()
	{
		return napTime;
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getSource();

		if (source == choice)
		{
			if (choice.getSelectedItem() == "1 min")
			{
				napTime = 1;
			}
			if (choice.getSelectedItem() == "5 min")
			{
				napTime = 5;
			}
			if (choice.getSelectedItem() == "10 min")
			{
				napTime = 10;
			}
			if (choice.getSelectedItem() == "15 min")
			{
				napTime = 15;
			}
			if (choice.getSelectedItem() == "30 min")
			{
				napTime = 30;
			}
		}
	}

}
