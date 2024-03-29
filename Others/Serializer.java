import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Klasa serializująca i deserializująca obiekty do/z wkazanego pliku.
 * 
 * @author Sylwia Mieszkowska
 * @author Anna Ciepłucha
 *
 */
public class Serializer
{
	protected String fileName;

	/**
	 * Konstruktor klasy.
	 * 
	 * @param fileName
	 *            nazwa pliku do serializacji/deserializacji
	 */
	public Serializer(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * Metoda serializująca podany obiekiet do pliku fileName.
	 * 
	 * @param object
	 *            obiekt, który ma zostać zserializowany
	 */
	public void serialize(Object object)
	{
		XMLEncoder encoder;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
			encoder.writeObject(object);
			encoder.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Metoda deserializująca obiekt z pliku fileName.
	 * 
	 * @return w przypadku powodzenia zwraca zdeserializowany obiekt, w przeciwnym
	 *         razie zwraca null
	 */
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
