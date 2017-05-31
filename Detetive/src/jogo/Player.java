package jogo;

import java.awt.image.BufferedImage;

public class Player 
{
	private Cell _cell;
	
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
}
