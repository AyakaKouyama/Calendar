import java.awt.Frame;
import java.util.Calendar;

import javax.swing.JOptionPane;

/**
 * Okno z informacj¹ o czasie jaki pozosta³ do uruchomienia nowo ustanowionego
 * alarmu.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class AlarmInfoDialog
{

	private Calendar date;
	private String message = null;

	/**
	 * Konstruktor klasy.
	 * 
	 * @param date
	 *            obiekt Calendar zawieraj¹cy informacje o dacie alarmu
	 */
	public AlarmInfoDialog(Calendar date)
	{
		this.date = date;
	}

	/**
	 * Metoda s³u¿¹ca do wyœwietlania okna.
	 * @param frame okno rodzic
	 * @return zwraca prawda w przypadku, gdy pomyœlnie ustawiono alarm, fa³sz w przypadku, gdy data alarmu ju¿ minê³a.
	 */
	public boolean show(Frame frame)
	{
		difference();
		if (message != null)
		{
			JOptionPane.showMessageDialog(frame, "Alarm rozpocznie siê za: " + message, "Alarm",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else
		{
			AlarmErrorDialog error = new AlarmErrorDialog();
			error.show(frame);
			return false;
		}
	}

	/**
	 * Metoda obliczaj¹ca czas jaki pozosta³ do uruchomienia alarmu.
	 * @return ³añcuch z wiadomoœci¹ o czasie jaki pozosta³ do alarmu
	 */
	public String difference()
	{

		long diff = date.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();

		if (diff >= 0)
		{
			Calendar diffDate = Calendar.getInstance();
			diffDate.setTimeInMillis(diff);

			int years = diffDate.get(Calendar.YEAR);
			int months = diffDate.get(Calendar.MONTH);
			int days = diffDate.get(Calendar.DATE);
			int hours = diffDate.get(Calendar.HOUR_OF_DAY);
			int minutes = diffDate.get(Calendar.MINUTE);

			minutes += 1;
			if (minutes == 60)
			{
				minutes = 0;
				hours += 1;
			}

			message = (years - 1970) + " lat " + months + " miesiêcy " + (days - 1) + " dni " + (hours - 1) + " godzin "
					+ (minutes) + " minut ";

			if ((years - 1970) == 0)
			{
				message = months + " miesiêcy " + (days - 1) + " dni " + (hours - 1) + " godzin " + minutes + " minut ";
			}
			if (months == 0 && (years - 1970) == 0)
			{
				message = (days - 1) + " dni " + (hours - 1) + " godzin " + minutes + " minut ";
			}
			if ((days - 1) == 0 && months == 0 && (years - 1970) == 0)
			{
				message = (hours - 1) + " godzin " + minutes + " minut ";
			}
			if ((hours - 1) == 0 && (days - 1) == 0 && months == 0 && (years - 1970) == 0)
			{
				message = minutes + " minut ";
			}
		}

		return message;
	}
}
