package views;

import java.util.Observable;


public class ViewObservable extends Observable
{
	public ViewObservable() 
	{
		super();
	}
	
	void changePanel(Object panel)
	{
		setChanged();
		notifyObservers(panel);
	}
}
