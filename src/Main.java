
public class Main {

	public static void main(String[] args) 
	{
		Window window = new Window("Organizer");
		DataBase db = new DataBase();
		db.connectToDataBase();
		db.setName(13518, "Hejka");
		db.setDate(13518, "2018-05-13 18:00");

		
	}

}
