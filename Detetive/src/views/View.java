package views;

import javax.swing.JPanel;

import views.ViewObservable;

@SuppressWarnings("serial")
public abstract class View extends JPanel 
{
	protected static ViewObservable observable;
	
	public View()
	{
		super();

		if(observable == null)
		{
			observable = new ViewObservable();	
		}
		
		setupUI();
	}
	
	public abstract void setupUI();
}
