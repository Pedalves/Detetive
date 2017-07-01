package views;

import javax.swing.JOptionPane;

import jogo.Facade;

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
		Facade.getInstance().endGame();
	}
}