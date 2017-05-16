package views;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import views.IntroView;

public class MainWindow implements Observer
{
	JFrame window;
	
	@Override
	public void update(Observable o, Object arg)
	{
		window.setContentPane((JPanel)arg);
		window.validate();
	}
	
	public MainWindow() 
	{				
		window = new JFrame("Detetive");
		
		window.setSize(966, 740);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		MainWindow w = new MainWindow();
		@SuppressWarnings("unused")
		IntroView panel = new IntroView(w);	
	}

}
