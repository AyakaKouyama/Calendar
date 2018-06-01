import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

/**
 * Okno wy�wietlaj�ce pasek post�pu po��czenia z baz� danych.
 * @author Sylwia Mieszkowska
 * @author Anna Ciep�ucha
 *
 */
public class ProgressBar
{
	JFrame frame;
	JProgressBar progressBar;
	
	/**
	 * Konstruktor klasy. Inicjalizuje komponenty okna oraz paska post�pu.
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
		Border border = BorderFactory.createTitledBorder("Pr�ba nawi�zania po��czenia z baz� danych..");
		 progressBar.setBorder(border);
		
		frame.add(progressBar);
		
	}
	
	/**
	 * Metoda wy�wietlaj�ca okno/
	 */
	public void show()
	{
		frame.setVisible(true);
	}
	
	/**
	 * Metoda zamykaj�ca okno.
	 */
	public void close()
	{
		frame.dispose();
	}
}
