package jogo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import views.GameView;

public class Facade implements MouseListener, Observer
{
	private Game _game;
	private GameView _view;
	
	private ArrayList<int[]> _availableCells;
		
	public Facade(GameView view, HashMap<Integer, Player> players)
	{
		_view = view;
		_game = Game.getInstance(this, players);
	}

	public void newDiceValue(int val)
	{
		_game.setDiceValue(val);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println(e.getX());
//		System.out.println(e.getY());
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
			_view.updatePlayer(newPosition[0], newPosition[1], newPosition[2]);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) 
	{
		Object[] args = (Object[])arg;
		
		switch((int)args[0])
		{
		// Update AvailableCells
		case 1:
			_availableCells = new ArrayList<>();
			((ArrayList<int[]>)args[1]).forEach(_availableCells::add);
			break;
		}
		
		//_view.updatePlayer(args[0], args[1], args[2]);
	}
	
	public List<Card> getCurrentPlayerCards()
	{
		return _game.getCurrentPlayerCards();
	}
}
