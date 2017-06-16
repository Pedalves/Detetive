package jogo;

import java.util.List;

public class Player 
{
	private Cell _cell;
	
	private List<Card> _cards;
	
	public int PosX;
	public int PosY;
		
	public Player(int x, int y)
	{
		PosX = x;
		PosY = y;
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
	}
}
