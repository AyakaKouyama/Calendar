import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class MeetingsFilterLogic
{
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/M/yyyy HH:mm");
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/M/yyyy HH:mm");
	private FillMeetingData db;

	MeetingsFilterLogic(FillMeetingData db)
	{
		this.db = db;
	}

	public Object[][] sort(Object[][] data, int size, String choice, int order)
	{
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (data[i][j] == null)
				{
					data[i][j] = "";
				}
			}
		}

		if (choice == "Nazwa")
		{
			Arrays.sort(data,
					(a, b) -> order * a[0].toString().compareTo(b[0].toString()));
		}
		if (choice == "Lokazlizacja")
		{
			Arrays.sort(data,
					(a, b) -> (order * a[1].toString().compareTo(b[1].toString())));
		}
		if (choice == "Data")
		{

			Arrays.sort(data,
					(a, b) -> (!a[2].toString().equals("") && !b[2].toString().equals("")) ? order * LocalDateTime
							.parse(a[2].toString(), format).compareTo(LocalDateTime.parse(b[2].toString(), format))
							: 0);

		}

		if (choice == "Szczegó³y")
		{
			Arrays.sort(data,
					(a, b) -> (order * a[3].toString().compareTo(b[3].toString())));
		}

		return data;

	}

	public Object[][] find(Object[][] data, String choice, int size, String text)
	{
		Object[][] temp = new Object[size][4];
		int column = -1;
		if (choice == "Nazwa")
		{
			column = 0;
		}
		if (choice == "Lokazlizacja")
		{
			column = 1;
		}

		if (choice == "Data")
		{
			column = 2;
		}

		if (choice == "Szczegó³y")
		{
			column = 3;
		}

		int x = 0;
		for (int i = 0; i < size; i++)
		{
			if (data[i][column] != null)
			{

				if (data[i][column].toString().equals(text) == true)
				{
					temp[x][0] = data[i][0];
					temp[x][1] = data[i][1];
					temp[x][2] = data[i][2];
					temp[x][3] = data[i][3];
					x++;
				}
			}

		}
		return temp;
	}

	public Object[][] findOlderThan(Object[][] data, int size, String text)
	{
		Object[][] temp = new Object[size][4];

		int x = 0;
		for (int i = 0; i < size; i++)
		{
			if (data[i][2] != null)
			{
				if (LocalDateTime.parse(data[i][2].toString(), dateFormat)
						.compareTo(LocalDateTime.parse(text + " 00:00", dateFormat)) < 0)
				{
					temp[x][0] = data[i][0];
					temp[x][1] = data[i][1];
					temp[x][2] = data[i][2];
					temp[x][3] = data[i][3];
					x++;
				}
			}
		}
		return temp;
	}

	public Object[][] remove(Object[][] data, int size)
	{
		Object[][] temp = new Object[size][4];
		int x = 0;
		for (int i = 0; i < size; i++)
		{
			String cellValue = (String) data[i][2];
			if (cellValue != null && !cellValue.equals(""))
			{
				String sId = cellValue.substring(0, 2) + cellValue.substring(3, 4) + cellValue.subSequence(7, 9);
				int id = Integer.parseInt(sId);

				if (db.getMap().get(id) != null)
				{
					db.deleteMeeting(id);
					db.deleteMeetingFromList(id);
					temp[x][0] = "";
					temp[x][1] = "";
					temp[x][2] = "";
					temp[x][3] = "";

					x++;
				} else
				{
					temp[x][0] = data[i][1];
					temp[x][1] = data[i][1];
					temp[x][2] = data[i][2];
					temp[x][3] = data[i][3];
					x++;
				}
			}

		}

		return temp;
	}

	public Object[][] removeOne(Object[][] data, int row, int size)
	{
		Object[][] temp = new Object[size][4];

		for (int i = 0; i < size; i++)
		{
			String cellValue = (String) data[i][2];
			if (i == row)
			{
				data[i][0] = "";
				data[i][1] = "";
				data[i][2] = "";
				data[i][3] = "";
				String sId = cellValue.substring(0, 2) + cellValue.substring(3, 4) + cellValue.subSequence(7, 9);
				int id = Integer.parseInt(sId);
				db.deleteMeeting(id);
				db.deleteMeetingFromList(id);
			}
		}

		int x = 0;
		for (int i = 0; i < size; i++)
		{
			if (data[i][0].equals("") == false || data[i][1].equals("") == false || data[i][2].equals("") == false
					|| data[i][3].equals("") == false)
			{
				for (int j = 0; j < 4; j++)
				{
					temp[x][j] = data[i][j];
				}

				x++;
			}
		}

		return temp;
	}

}
