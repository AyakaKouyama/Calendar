import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SerializeSettings 
{

	public void serialize(ChosenSettings settings)
	{
	
		XMLEncoder encoder;
		try 
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("settings.xml")));
			encoder.writeObject(settings);
		    encoder.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		      
	}
	
	public ChosenSettings deserialize()
	{
		FileInputStream fis;
		ChosenSettings decodedSettings = null;
		try 
		{
			fis = new FileInputStream("settings.xml");
		    XMLDecoder decoder = new XMLDecoder(fis);
	        decodedSettings = (ChosenSettings) decoder.readObject();
	        decoder.close();
		    fis.close();       
		} 
		catch (FileNotFoundException e)
		{
			return null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	
	   	 return decodedSettings; 	
	}
}
