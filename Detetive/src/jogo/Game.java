package jogo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
		
		_gameCells = new HashMap<Integer, Cell>();
		
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
	
	public int[] newClickPosition(int x, int y)
	{
		int xy[] = {-1, -1};
		
		for(int pos : _gameCells.keySet())
		{
			Cell cell = _gameCells.get(pos);
			
			if(cell.isInside(x, y))
			{
				int temp[] = {cell.getX(), cell.getY()};
				xy = temp;
			}
		}
		
		return xy;
	}
	
	private void setupBoard()
	{
		BufferedReader br = null;
		FileReader fr = null;

		int i = 0;
		
		try 
		{
			fr = new FileReader("casa.txt");
			br = new BufferedReader(fr);

			String casa;

			while ((casa = br.readLine()) != null)
			{	
				int x = Integer.parseInt(casa.split(" ")[0]);
				int y = Integer.parseInt(casa.split(" ")[1]);
				
				int	upperLeft[] = {x, y}; 
				int	lowerRight[] = {x+25, y+25}; 
				
				Cell cell = new Cell(upperLeft, lowerRight);
				
				_gameCells.put(i, cell);
				i++;
			}

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try
			{
				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
}
