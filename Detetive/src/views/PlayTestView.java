package views;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jogo.Facade;

@SuppressWarnings("serial")
public class PlayTestView extends JFrame
{
	private Facade _facade;
	
	public PlayTestView()
	{
		super();
		
		setSize(200, 100);
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		_facade = Facade.getInstance();
		
		setupWindow();
	}
	
	public void setupWindow() 
	{
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("Não utilizar valores acima de 12");
		panel.add(label);
		
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
		textField.setPreferredSize(new Dimension(50,30));
		
		panel.add(textField);
		
		add(panel);
	}

}
