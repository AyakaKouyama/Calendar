
public class OptionsContext
{

	OptionsData data;

	OptionsContext()
	{
		data = new OptionsData();
	}

	public String getSound()
	{
		return data.getSound(1);
	}

	public String getTheme()
	{
		return data.getTheme(1);
	}

	public void setSound(int id, String value)
	{
		data.setSound(id, value);
	}

	public void setTheme(int id, String value)
	{
		data.setTheme(id, value);
	}

}