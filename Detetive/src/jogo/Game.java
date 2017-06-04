package jogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Game extends Observable
{
	static private Game _game; 
	
	private HashMap<Integer, Player> _players; 
	private int _currentPlayer;
	
	private Board _board;
	private HashMap<Integer, Cell> _gameCells; 
	
	private Game(Observer observer)
	{
		_players = new HashMap<Integer, Player>();
		_currentPlayer = 0;
		
		_board = new Board();
		
		setupBoard();
		
		addObserver(observer);
	}
	
	static public Game getInstance(Observer observer)
	{
		if(_game == null)
		{
			_game = new Game(observer);
		}
		
		return _game;
	}
	
	public void addPlayers(String players[])
	{		
		for(int i = 0; i < players.length; i++)
		{	
			//_players.put(i, new Player());
		}
	}
	
	public int getCurrentPlayer()
	{
		return _currentPlayer;
	}
	
	public void newClickPosition(int x, int y)
	{
		
	}
	
	private void setupBoard()
	{
		
	}
}
