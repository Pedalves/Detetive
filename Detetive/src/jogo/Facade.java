package jogo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import views.GameView;

public class Facade implements MouseListener, Observer
{
	static private Facade _facade;
	
	private Game _game;
	private GameView _view;
	
	private boolean _gameEnded;
	
	private ArrayList<int[]> _availableCells;
		
	private Facade()
	{
		_gameEnded = false;
	}

	static public Facade getInstance()
	{
		if(_facade == null)
		{
			_facade = new Facade();
		}
		
		return _facade;
	}
	
	public void setGameView(GameView view)
	{
		_view = view;
	}
	
	public void addPlayers(HashMap<Integer, Player> players)
	{
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
		int newPosition[] = _game.newClickPosition(e.getX(), e.getY(), _game.getCurrentPlayer());
		
		updatePlayerPosition(newPosition);
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
	
//	public List<Card> getCurrentPlayerCards()
//	{
//		return _game.getCurrentPlayerCards();
//	}
	
	public List<BufferedImage> getCurrentPlayerCardsImages()
	{
		List<Card> cards = _game.getCurrentPlayerCards();
		List<BufferedImage> images = new ArrayList<BufferedImage>();
		
		for(Card c : cards)
		{
			images.add(c.Image);
		}
		return images;
	}
	
	public List<String> getCurrentPlayerNotes()
	{
		return _game.getCurrentPlayerNotes();
	}
	
	public String getCurrentPlayerName()
	{
		return _game.getCurrentPlayerName();
	}
	
	public String getCurrentPlayerRoom()
	{
		return _game.getCurrentPlayerRoom();
	}
	
	public String newGuess(String[] guess)
	{
		return _game.guessResult(guess);
	}
	
	public String newAccusation(String[] accusation)
	{
		String result = _game.Accusation(accusation);
		
		if(result == "Acertou!")
		{
			_gameEnded = true;
		}
		
		return result;
	}
	
	public void endTurn()
	{
		_game.newTurn();
	}
	
	public void updatePlayerPosition(int[] newPosition)
	{
		if(newPosition[0] != -1)
		{
			_view.updatePlayer(newPosition[0], newPosition[1], newPosition[2]);
		}
	}
	
	public void RepaintGameView()
	{
		_view.repaint();
	}
	
	public void endGame()
	{
		if(_gameEnded)
		{
			_view.endGame();
			_facade = null;
		}
		else
		{
			_game.newTurn();
			RepaintGameView();
		}
	}
	
	public void forceEndGame()
	{
		_view.endGame();
		_facade = null;
	}
}
