import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Serializer
{
	String fileName;

	Serializer(String fileName)
	{
		this.fileName = fileName;
	}

	public void serialize(Object settings)
	{

		XMLEncoder encoder;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
			encoder.writeObject(settings);
			encoder.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	public Object deserialize()
	{
		FileInputStream fis;
		Object decodedSettings = null;
		try
		{
			fis = new FileInputStream(fileName);
			XMLDecoder decoder = new XMLDecoder(fis);
			decodedSettings = decoder.readObject();
			decoder.close();
			fis.close();
		} catch (FileNotFoundException e)
		{
			return null;
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return decodedSettings;
	}
}
