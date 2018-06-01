
/**
 * Klasa reprezentuj¹ca alarm. U¿ywana do pózniejszej serializacji do pliku XML.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class AlarmObject
{
	public String date;
	
	/**
	 * Metoda zwracaj¹ca ³añcuch reprezentuj¹cy datê alarmu.
	 * @return zwraca ³ancuch reprezentuj¹cy datê alarmu.
	 */
	public String getDate()
	{
		return date;
	}
	
	/**
	 * Metoda ustawiaj¹ca datê alarmu.
	 * @param value ³añcuch reprezentuj¹cy datê alarmu
	 */
	public void setDate(String value)
	{
		date = value;
	}
}
