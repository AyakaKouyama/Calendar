import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

/**
 * Okno wyœwietlaj¹ce pasek postêpu po³¹czenia z baz¹ danych.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep³ucha
 *
 */
public class ProgressBar
{
	JFrame frame;
	JProgressBar progressBar;
	
	/**
	 * Konstruktor klasy. Inicjalizuje komponenty okna oraz paska postêpu.
	 */
	ProgressBar()
	{
		frame = new JFrame();
		
		frame.setLocationRelativeTo(null);
        frame.setTitle("Progress");
	    frame.setIconImage(new ImageIcon("calendar.png").getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	    
		frame.pack();
     	frame.setSize(320, 90);
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(20, 20, 100, 40);
		Border border = BorderFactory.createTitledBorder("Próba nawi¹zania po³¹czenia z baz¹ danych..");
		 progressBar.setBorder(border);
		
		frame.add(progressBar);
		
	}
	
	/**
	 * Metoda wyœwietlaj¹ca okno/
	 */
	public void show()
	{
		frame.setVisible(true);
	}
	
	/**
	 * Metoda zamykaj¹ca okno.
	 */
	public void close()
	{
		frame.dispose();
	}
}
