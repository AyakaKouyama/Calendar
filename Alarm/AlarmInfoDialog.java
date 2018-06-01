import java.awt.Frame;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class AlarmInfoDialog
{

	private Calendar date;
	private String message = null;
	
	public AlarmInfoDialog(Calendar date) 
	{
		this.date = date;
	}

	public boolean show(Frame frame)
	{
		difference();
		if(message != null)
		{
			JOptionPane.showMessageDialog(frame, "Alarm rozpocznie siê za: " + message, "Alarm", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		else
		{
			AlarmErrorDialog error = new AlarmErrorDialog();
			error.show(frame);
			return false;
		}
	}
	
	public String difference()
	{

		 long diff = date.getTimeInMillis() -  Calendar.getInstance().getTimeInMillis();

		 if(diff >= 0)
		 {
			 Calendar diffDate = Calendar.getInstance();
			 diffDate.setTimeInMillis(diff);
			 
			 int years = diffDate.get(Calendar.YEAR);
			 int months = diffDate.get(Calendar.MONTH);
			 int days = diffDate.get(Calendar.DATE);
			 int hours = diffDate.get(Calendar.HOUR_OF_DAY);
			 int minutes = diffDate.get(Calendar.MINUTE);
			 
			 minutes += 1;
			 if(minutes  == 60)
			 {
				 minutes = 0;
				 hours += 1;
			 }
			 
			 message = (years - 1970) + " lat " + months + " miesiêcy " + (days - 1) + " dni " + (hours-1) + " godzin " + (minutes) + " minut ";
			 
			 if((years - 1970) == 0)
			 {
				 message = months + " miesiêcy " + (days - 1) + " dni " + (hours-1) + " godzin " + minutes + " minut ";
			 }
			 if(months == 0 && (years - 1970) == 0)
			 {
				 message = (days - 1) + " dni " + (hours-1) + " godzin " + minutes + " minut ";
			 }
			 if((days - 1) == 0 && months == 0 && (years - 1970) == 0)
			 {
				 message = (hours-1)  + " godzin " + minutes + " minut ";
			 }
			 if((hours-1) == 0 && (days - 1) == 0 && months == 0 && (years - 1970) == 0)
			 {
				 message =  minutes + " minut ";
			 }
		 }
		
		return message;
	}
}
