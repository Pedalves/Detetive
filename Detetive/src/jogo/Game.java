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
	
	private ArrayList<int[]> _availableCells;
	
	private Game(Observer observer, HashMap<Integer, Player> players)
	{
		_players = players;
		_currentPlayer = 0;
		
		_board = new Board();
		
		_gameCells = new HashMap<Integer, Cell>();
		_availableCells = null;
		
		setupBoard();
		
		addObserver(observer);
		
		for(int player : _players.keySet())
		{
			switch(_players.get(player).PosX)
			{
			case 400:
				_players.get(player).setCell(_gameCells.get(183));
				break;
			case 50:
				_players.get(player).setCell(_gameCells.get(185));
				break;
			case 625:
				if(_players.get(player).PosY == 200)
				{
					_players.get(player).setCell(_gameCells.get(184));
					break;
				}
				else
				{
					_players.get(player).setCell(_gameCells.get(186));
					break;
				}
			case 225:
				_players.get(player).setCell(_gameCells.get(187));
				break;
			case 275:
				_players.get(player).setCell(_gameCells.get(182));
				break;
			}
		}
	}
	
	static public Game getInstance(Observer observer, HashMap<Integer, Player> players)
	{
		if(_game == null)
		{
			_game = new Game(observer, players);
		}
		
		return _game;
	}
	
	static public Game getInstance()
	{
		if(_game == null)
		{
			_game = null;
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
				
				if(_availableCells != null)
				{
					for(int[] availableCell : _availableCells)
					{
						if(availableCell[0] == temp[0] && availableCell[1] == temp[1])
						{
							_availableCells = null;
							xy = temp;
							_players.get(_currentPlayer).setCell(cell);
							
							if(_currentPlayer == _players.size() - 1)
								_currentPlayer = 0;
							else
								_currentPlayer++;
							break;
						}
					}
				}
			}
		}
		
		return xy;
	}
	
	public void setDiceValue(int val)
	{
		_availableCells = _board.getAvailableCells(val, _players.get(_currentPlayer).getCell());
		
		Object infos[] = {(Object) 1, (Object)_availableCells.clone()}; 
		
		setChanged();
		notifyObservers((Object)infos);
	}
	
	private void setupBoard()
	{
		BufferedReader br = null;
		FileReader fr = null;
		
		BufferedReader br2 = null;
		FileReader fr2 = null;

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
				
				_board.addVertex(cell);
				_gameCells.put(i, cell);
				i++;
			}
			
			//Montando quartos
			//Cozinha
			int	roomUpperLeft[] = {50, 50}; 
			int	roomLowerRight[] = {200, 200}; 
			RoomCell room = new RoomCell(roomUpperLeft, roomLowerRight);
			_gameCells.put(i, room);
			_board.addVertex(room);
			i++;
			
			//Sala de musica
			roomUpperLeft[0] = 250;
			roomUpperLeft[1] = 100;
			roomLowerRight[0] = 450;
			roomLowerRight[1] = 250; 
			room = new RoomCell(roomUpperLeft, roomLowerRight);
			_gameCells.put(i, room);
			_board.addVertex(room);
			i++;
			
			//Jardim de inverno
			roomUpperLeft[0] = 500;
			roomUpperLeft[1] = 75;
			roomLowerRight[0] = 650;
			roomLowerRight[1] = 175; 
			room = new RoomCell(roomUpperLeft, roomLowerRight);
			_gameCells.put(i, room);
			_board.addVertex(room);
			i++;
			
			//Sala de jantar
			roomUpperLeft[0] = 50;
			roomUpperLeft[1] = 300;
			roomLowerRight[0] = 250;
			roomLowerRight[1] = 450; 
			room = new RoomCell(roomUpperLeft, roomLowerRight);
			_gameCells.put(i, room);
			_board.addVertex(room);
			i++;
			
			//Salão de jogos
			roomUpperLeft[0] = 500;
			roomUpperLeft[1] = 250;
			roomLowerRight[0] = 650;
			roomLowerRight[1] = 375; 
			room = new RoomCell(roomUpperLeft, roomLowerRight);
			_gameCells.put(i, room);
			_board.addVertex(room);
			i++;
			
			//Biblioteca
			roomUpperLeft[0] = 475;
			roomUpperLeft[1] = 425;
			roomLowerRight[0] = 650;
			roomLowerRight[1] = 500; 
			room = new RoomCell(roomUpperLeft, roomLowerRight);
			_gameCells.put(i, room);
			_board.addVertex(room);
			i++;
			
			//Sala de estar
			roomUpperLeft[0] = 50;
			roomUpperLeft[1] = 525;
			roomLowerRight[0] = 225;
			roomLowerRight[1] = 650; 
			room = new RoomCell(roomUpperLeft, roomLowerRight);
			_gameCells.put(i, room);
			_board.addVertex(room);
			i++;
			
			//Entrada
			roomUpperLeft[0] = 275;
			roomUpperLeft[1] = 500;
			roomLowerRight[0] = 425;
			roomLowerRight[1] = 650; 
			room = new RoomCell(roomUpperLeft, roomLowerRight);
			_gameCells.put(i, room);
			_board.addVertex(room);
			i++;
			
			//Escritorio
			roomUpperLeft[0] = 475;
			roomUpperLeft[1] = 575;
			roomLowerRight[0] = 650;
			roomLowerRight[1] = 650; 
			room = new RoomCell(roomUpperLeft, roomLowerRight);
			_gameCells.put(i, room);
			_board.addVertex(room);
			i++;
			
			fr2 = new FileReader("casa ligacao.txt");
			br2 = new BufferedReader(fr2);

			String arestasString;
			i = 0;
			
			while ((arestasString = br2.readLine()) != null)
			{	
				String arestasList[] = arestasString.split(" ");
					
				for(String aresta : arestasList)
				{
					if(Integer.parseInt(aresta)!= -1)
						_board.addEdge(_gameCells.get(i), _gameCells.get(Integer.parseInt(aresta)));
				}
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
				
				if (br2 != null)
					br2.close();

				if (fr2 != null)
					fr2.close();

			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
}
