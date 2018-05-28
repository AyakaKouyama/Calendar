
public class OptionsContext 
{
	OptionsData data;
	String sound;
	
	OptionsContext()
	{
		data = new OptionsData();
		sound = data.getSound(1);
	}
	
	public String getSound()
	{
		//return sound;
		return data.getSound(1);
	}
	
	public void setSound(int id, String value)
	{
		sound = value;
		data.setSound(id, value);
	}
}
