import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class ProgressBar
{
	JFrame frame;
	JProgressBar progressBar;
	
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
	
	public void show()
	{
		frame.setVisible(true);
	}
	
	public void close()
	{
		frame.dispose();
	}
}
