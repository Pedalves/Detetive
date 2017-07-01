package views;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class AccusationView  extends GenericSelectionView
{	
	public AccusationView()
	{
		super();
	}
	
	@Override
	protected void confirmAction(String[] accusation)
	{
		JOptionPane.showMessageDialog(null,_facade.newAccusation(accusation));		
	}
}