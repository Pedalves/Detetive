package views;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import jogo.Facade;

@SuppressWarnings("serial")
abstract public class GenericSelectionView extends JFrame
{
	protected Facade _facade;
	protected JButton _confirmButton;
	protected String[] rooms = {"Biblioteca","Cozinha","Entrada","Escritorio","Jardim De Inverno","Sala De Estar","Sala De Jantar","Sala De Musica","Salao De Jogos"};
	protected ButtonGroup roomGroup;
	
	public GenericSelectionView()
	{
		super();
		
		setSize(900, 950);
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		_facade = Facade.getInstance();
		
		setupUI();
	}
	
	protected void setupUI()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		roomGroup = new ButtonGroup();
		
		panel.add(new JLabel("Comodo:"));
		panel.add(new JLabel(" "));
		for(String room : rooms)
		{
			JRadioButton button = new JRadioButton(room);
			
			roomGroup.add(button);
			
			panel.add(button);
		}
		
		ButtonGroup weaponGroup = new ButtonGroup();
		String[] weapons = {"Cano","Castical","ChaveInglesa","Corda","Faca","Revolver"};
		
		panel.add(new JLabel(" "));
		panel.add(new JLabel("Armas:"));
		panel.add(new JLabel(" "));
		for(String weapon : weapons)
		{
			JRadioButton button = new JRadioButton(weapon);
			
			weaponGroup.add(button);
			panel.add(button);
		}	
		
		ButtonGroup suspectGroup = new ButtonGroup();
		String[] suspects = {"Green","Mustard","Peacock","Plum","Scarlet","White"};
		
		panel.add(new JLabel(" "));
		panel.add(new JLabel("Suspeito:"));
		panel.add(new JLabel(" "));
		for(String suspect : suspects)
		{
			JRadioButton button = new JRadioButton(suspect);
			
			suspectGroup.add(button);
			panel.add(button);
		}
		
		
		_confirmButton = new JButton("Confirmar");
		_confirmButton.addActionListener(e -> {
			String guess[] = {"", "", ""};
			
			if(suspectGroup.getSelection() != null)
			{
				for (Enumeration<AbstractButton> buttons = suspectGroup.getElements(); buttons.hasMoreElements();) 
				{
		            AbstractButton button = buttons.nextElement();

		            if (button.isSelected()) {
		                guess[0] = button.getText();
		            }
		        }
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Todos os campos precisam ser preenchidos");
				return;
			}
			
			if(weaponGroup.getSelection() != null)
			{
				for (Enumeration<AbstractButton> buttons = weaponGroup.getElements(); buttons.hasMoreElements();) 
				{
		            AbstractButton button = buttons.nextElement();

		            if (button.isSelected()) {
		                guess[1] = button.getText();
		            }
		        }			
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Todos os campos precisam ser preenchidos");
				return;
			}
			
			if(roomGroup.getSelection() != null)
			{
				for (Enumeration<AbstractButton> buttons = roomGroup.getElements(); buttons.hasMoreElements();) 
				{
		            AbstractButton button = buttons.nextElement();

		            if (button.isSelected()) {
		                guess[2] = button.getText();
		            }
		        }
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Todos os campos precisam ser preenchidos");
				return;
			}
			
			confirmAction(guess);
		});
		
		panel.add(_confirmButton);
		setContentPane(panel);
	}
	
	protected abstract void confirmAction(String[] guess);
}
