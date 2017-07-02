package jogo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

class Player 
{
	private Cell _cell;
	
	private List<Card> _cards;
	private List<String> _notes;
	private String _name;
	
	private Color _color;
	
	private boolean _canWalk;
	private boolean _canGuess;
	private boolean _inGame;
		
	public Player(String name, Color color)
	{
		_name = name;
		
		_color = color;
		
		_notes = new ArrayList<String>();
		
		_canGuess = false;
		_canWalk = true;
		_inGame = true;
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
			if(!_notes.contains(c.GetName()))
			{
				_notes.add(c.GetName());
			}
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
	
	public Color getColor()
	{
		return _color;
	}
}
