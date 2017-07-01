package jogo;

import java.util.ArrayList;
import java.util.List;

public class Player 
{
	private Cell _cell;
	
	private List<Card> _cards;
	private List<String> _notes;
	private String _name;
	
	public int PosX;
	public int PosY;
	
	private boolean _canWalk;
	private boolean _canGuess;
	private boolean _inGame;
		
	public Player(int x, int y, String name)
	{
		PosX = x;
		PosY = y;
		_name = name;
		
		_notes = new ArrayList<String>();
		
		_canGuess = false;
		_canWalk = true;
		_inGame = true;
	}
	
	public void setXY(int x, int y)
	{
		PosX = x;
		PosY = y;
	}
	
	public Player(Cell cell)
	{		
		_cell = cell;
	}
	
	public void setCell(Cell cell)
	{
		_cell = cell;
	}
	
	public Cell getCell()
	{
		return _cell;
	}
	
	public void setCards(List<Card> cards)
	{
		_cards = cards;
		
		for(Card c : _cards)
		{
			_notes.add(c.GetName());
		}
	}
	
	public List<Card> getCards()
	{
		return _cards;
	}
	
	public void addNote(String card)
	{
		_notes.add(card);
	}
	
	public List<String> getNotes()
	{
		return _notes;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public boolean getCanWalk()
	{
		return _canWalk;
	}
	
	public void setCanWalk(boolean canWalk)
	{
		_canWalk = canWalk;
	}
	
	public boolean getCanGuess()
	{
		return _canGuess;
	}
	
	public void setCanGuess(boolean canGuess)
	{
		_canGuess = canGuess;
	}
	
	public boolean getInGame()
	{
		return _inGame;
	}
	
	public void setInGame(boolean inGame)
	{
		_inGame = inGame;
	}
}
