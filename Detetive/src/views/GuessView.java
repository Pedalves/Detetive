package views;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class GuessView extends GenericSelectionView
{	
	public GuessView()
	{
		super();
	}
	
	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		for (Enumeration<AbstractButton> buttons = roomGroup.getElements(); buttons.hasMoreElements();)
		{
            AbstractButton button = buttons.nextElement();

            button.setEnabled(false);
			
			if(button.getText() == _facade.getCurrentPlayerRoom())
			{
				button.setSelected(true);
			}
        }
	}

	@Override
	protected void confirmAction(String[] guess)
	{
		JOptionPane.showMessageDialog(null,_facade.newGuess(guess));		
	}
}
