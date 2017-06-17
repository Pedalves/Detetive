package views;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jogo.Facade;

@SuppressWarnings("serial")
public class PlayerNotesView extends JFrame
{
	private Facade _facade;
	
	public PlayerNotesView(Facade facade)
	{
		super();
		
		setSize(200, 600);
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		_facade = facade;
		
		setupNotes();
	}
	
	private void setupNotes()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		List<String> notes = _facade.getCurrentPlayerNotes();
		
		String[] weapons = {"Cano","Castical","ChaveInglesa","Corda","Faca","Revolver"};
		String[] rooms = {"Biblioteca","Cozinha","Entrada","Escritorio","JardimInverno","SalaDeEstar","SalaDeJantar","SalaDeMusica","SalaoDeJogos"};
		String[] suspects = {"Green","Mustard","Peacock","Plum","Scarlet","White"};
		
		panel.add(new JLabel("Armas"));
		panel.add(new JLabel(" "));
		for(String s : weapons)
		{
			JLabel label;
			if(notes.contains(s))
			{
				label = new JLabel(s + " (X)");
			}
			else
			{
				label = new JLabel(s);
			}
			panel.add(label);
		}
		
		panel.add(new JLabel(" "));
		panel.add(new JLabel("Comodos"));
		panel.add(new JLabel(" "));
		for(String s : rooms)
		{
			JLabel label;
			if(notes.contains(s))
			{
				label = new JLabel(s + " (X)");
			}
			else
			{
				label = new JLabel(s);
			}
			panel.add(label);
		}
		
		panel.add(new JLabel(" "));
		panel.add(new JLabel("Suspeitos"));
		panel.add(new JLabel(" "));
		for(String s : suspects)
		{
			JLabel label;
			if(notes.contains(s))
			{
				label = new JLabel(s + " (X)");
			}
			else
			{
				label = new JLabel(s);
			}
			panel.add(label);
		}
		
		add(panel);
	}
}
