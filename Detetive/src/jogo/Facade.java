package jogo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import views.GameView;

public class Facade implements MouseListener, Observer
{
	private Game _game;
	private GameView _view;
	
	public Facade(GameView view)
	{
		_view = view;
		_game = Game.getInstance(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{		
		int newPosition[] = _game.newClickPosition(e.getX(), e.getY());
		
		if(newPosition[0] != -1)
		{
			_view.updatePlayer(newPosition[0], newPosition[1], 0);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		int[] args = (int[])arg;
		
		_view.updatePlayer(args[0], args[1], args[2]);
	}
}
