package jogo;

import java.util.ArrayList;

import util.Grafo;

public class Board extends Grafo<Cell>
{
	public Board()
	{
		super();
	}
	
	public ArrayList<int[]>getAvailableCells(int level, Cell rootPosition)
	{
		ArrayList<int[]> cellsPositions = new ArrayList<int[]>();
		
		try
		{
			ArrayList<Cell> availableCells = bfs(level, rootPosition);
			availableCells.forEach(cell ->
			{
				int temp[] = {cell.getX(), cell.getY()}; 
				cellsPositions.add(temp);
			});
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cellsPositions;
	}
}
