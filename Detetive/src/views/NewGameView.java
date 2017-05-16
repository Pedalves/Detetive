package views;
	
import javax.swing.JButton;

import java.awt.Component;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import views.ViewObservable;

public class NewGameView extends JPanel 
{	
	private static final long serialVersionUID = 1L;

	private final Observer mainWindow;
	private final ViewObservable observable;
	
	public NewGameView(Observer mainWindow)
	{
		super();
		
		this.observable = new ViewObservable();
		this.observable.addObserver(mainWindow);
		this.mainWindow = mainWindow;
		
		setupUI();
	}

	public void setupUI()
	{
		
	}
}