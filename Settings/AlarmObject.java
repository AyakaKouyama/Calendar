
/**
 * Klasa reprezentuj�ca alarm. U�ywana do p�zniejszej serializacji do pliku XML.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class AlarmObject
{
	public String date;
	
	/**
	 * Metoda zwracaj�ca �a�cuch reprezentuj�cy dat� alarmu.
	 * @return zwraca �ancuch reprezentuj�cy dat� alarmu.
	 */
	public String getDate()
	{
		return date;
	}
	
	/**
	 * Metoda ustawiaj�ca dat� alarmu.
	 * @param value �a�cuch reprezentuj�cy dat� alarmu
	 */
	public void setDate(String value)
	{
		date = value;
	}
}
