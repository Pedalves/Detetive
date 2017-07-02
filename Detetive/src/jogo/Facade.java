package jogo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;


public class Facade implements MouseListener
{
	static private Facade _facade;
	
	private Game _game;
	
	private boolean _gameEnded;
			
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
	
	public void setGameView(Observer view)
	{
		_game.addObserver(view);
	}
	
	public void addPlayers(ArrayList<String> players)
	{
		_game = Game.getInstance(players);
	}
	
	public void initializePlayers()
	{
		_game.initializePlayers();
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
		_game.newClickPosition(e.getX(), e.getY(), _game.getCurrentPlayer());		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
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
	
	public void endGame()
	{
		if(_gameEnded)
		{
			_game.resetGame();
			_facade = null;
		}
		else
		{
			_game.newTurn();
		}
	}
	
	public void forceEndGame()
	{
		_game.resetGame();
		_facade = null;
	}
}
