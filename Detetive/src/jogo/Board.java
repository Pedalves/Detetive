package jogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class Board
{
	private HashMap<Cell, LinkedList<Cell>> graph;
	
	public Board()
	{
		graph = new HashMap<Cell, LinkedList<Cell>>();
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
			e.printStackTrace();
		}
		
		return cellsPositions;
	}
	
	private ArrayList<Cell> bfs(int level, Cell rootPosition) throws Exception
	{
		if(!graph.containsKey(rootPosition))
		{
			throw new Exception("Invalid rootPosition");
		}
		
		Queue<Cell> queue = new LinkedList<Cell>();
				
		for(Cell child : getChildren(rootPosition))
		{
			if(!child.getOcuppied())
			{
				queue.add(child);
			}
		}
		
		for(int i = 1; i<level; i++)
		{
			Queue<Cell> tempQueue = new LinkedList<Cell>();

			while(!queue.isEmpty())
			{
				Cell node = queue.remove();
				
				if(node instanceof RoomCell)
				{
					tempQueue.add(node);
				}
				else
				{
					for(Cell child : getChildren(node))
					{
						if(!child.getOcuppied())
						{
							tempQueue.add(child);
						}
					}
				}
			}
			queue = tempQueue;
		}
		ArrayList<Cell> available = new ArrayList<>();
		queue.forEach(available::add);
		
		return available;
	}
	
	public void addVertex(Cell node)
	{
		graph.put(node, new LinkedList<Cell>());
	}
	
	public void addEdge(Cell node1, Cell node2)
	{	
		graph.get(node1).add(node2);
		graph.get(node2).add(node1);
	}

	private LinkedList<Cell> getChildren(Cell node)
	{
		return graph.get(node);
	}
}
