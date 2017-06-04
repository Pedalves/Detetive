package views;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import jogo.Facade;

@SuppressWarnings("serial")
public class PlayTestView extends JFrame
{
	private Facade _facade;
	
	public PlayTestView(Component window, Facade facade)
	{
		super();
		
		setSize(200, 100);
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		_facade = facade;
		
		setupWindow();
	}
	
	public void setupWindow() 
	{
		JTextField textField = new JTextField();
		textField.addActionListener(e -> {
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
			_facade.newDiceValue(num);
			setVisible(false);
			dispose();
		});
		
		add(textField);
	}

}
