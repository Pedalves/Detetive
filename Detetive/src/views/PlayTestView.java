package views;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTextField;

public class PlayTestView extends View
{

	@Override
	public void setupUI() 
	{
		JTextField textField = new JTextField();
		textField.setAlignmentX(Component.CENTER_ALIGNMENT);	
		
		JButton setDiceValueButton = new JButton("Setar valor do dado");
		setDiceValueButton.setAlignmentX(Component.CENTER_ALIGNMENT);		
		setDiceValueButton.addActionListener(e -> {
			String text = textField.getText();
			int num;
			try
			{
				num = Integer.parseInt(text);
			}
			catch(NumberFormatException exc)
			{
		        num = 0;
		    }
		});
	}

}
