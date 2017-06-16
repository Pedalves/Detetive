package views;

import javax.swing.JFrame;

import jogo.Facade;

@SuppressWarnings("serial")
public class PlayerNotesView extends JFrame
{
	private Facade _facade;
	
	public PlayerNotesView(Facade facade)
	{
		super();
		
		setSize(200, 100);
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		_facade = facade;
		
		setupNotes();
	}
	
	private void setupNotes()
	{
		
	}
}
