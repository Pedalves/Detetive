package jogo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	private Deck _deck;
	private List<Card> _keyCards;
	
	private Game(Observer observer, HashMap<Integer, Player> players)
	{
		_players = players;
		_currentPlayer = 0;
		
		_board = new Board();
		
		_gameCells = new HashMap<Integer, Cell>();
		_availableCells = null;
		
		setupBoard();
		setupCards();
		
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
		int xyPlayer[] = {-1, -1, -1};
		
		for(int pos : _gameCells.keySet())
		{
			Cell cell = _gameCells.get(pos);
			
			if(cell.isInside(x, y))
			{
				int temp[];
				if(cell instanceof RoomCell)
				{
					temp = new int[] {((RoomCell)cell).GetPosX(_currentPlayer), ((RoomCell)cell).GetPosY(_currentPlayer), _currentPlayer};	
					
					if(_availableCells != null)
					{
						for(int[] availableCell : _availableCells)
						{
							if(availableCell[0] == cell.getX() && availableCell[1] == cell.getY())
							{
								_availableCells = null;
								xyPlayer = temp;
								
								_players.get(_currentPlayer).getCell().setOcuppied(false);
								
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
				else
				{
					temp = new int[] {cell.getX(), cell.getY(), _currentPlayer};
					
					if(_availableCells != null)
					{
						for(int[] availableCell : _availableCells)
						{
							if(availableCell[0] == temp[0] && availableCell[1] == temp[1])
							{
								_availableCells = null;
								xyPlayer = temp;
								
								_players.get(_currentPlayer).getCell().setOcuppied(false);
								cell.setOcuppied(true);
								
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
		}
		
		return xyPlayer;
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
			int	roomUpperLeft2[] = {250, 100}; 
			int	roomLowerRight2[] = {450, 250};
			RoomCell room2 = new RoomCell(roomUpperLeft2, roomLowerRight2);
			_gameCells.put(i, room2);
			_board.addVertex(room2);
			i++;
			
			//Jardim de inverno
			int	roomUpperLeft3[] = {500, 75}; 
			int	roomLowerRight3[] = {650, 175};
			RoomCell room3 = new RoomCell(roomUpperLeft3, roomLowerRight3);
			_gameCells.put(i, room3);
			_board.addVertex(room3);
			i++;
			
			//Sala de jantar
			int	roomUpperLeft4[] = {50, 300}; 
			int	roomLowerRight4[] = {250, 450};
			RoomCell room4 = new RoomCell(roomUpperLeft4, roomLowerRight4);
			_gameCells.put(i, room4);
			_board.addVertex(room4);
			i++;
			
			//Salão de jogos
			int	roomUpperLeft5[] = {500, 250}; 
			int	roomLowerRight5[] = {650, 375};
			RoomCell room5 = new RoomCell(roomUpperLeft5, roomLowerRight5);
			_gameCells.put(i, room5);
			_board.addVertex(room5);
			i++;
			
			//Biblioteca
			int	roomUpperLeft6[] = {475, 425}; 
			int	roomLowerRight6[] = {650, 500}; 
			RoomCell room6 = new RoomCell(roomUpperLeft6, roomLowerRight6);
			_gameCells.put(i, room6);
			_board.addVertex(room6);
			i++;
			
			//Sala de estar
			int	roomUpperLeft7[] = {50, 525}; 
			int	roomLowerRight7[] = {225, 650};
			RoomCell room7 = new RoomCell(roomUpperLeft7, roomLowerRight7);
			_gameCells.put(i, room7);
			_board.addVertex(room7);
			i++;
			
			//Entrada
			int	roomUpperLeft8[] = {275, 500}; 
			int	roomLowerRight8[] = {425, 650};
			RoomCell room8 = new RoomCell(roomUpperLeft8, roomLowerRight8);
			_gameCells.put(i, room8);
			_board.addVertex(room8);
			i++;
			
			//Escritorio
			int	roomUpperLeft9[] = {475, 575}; 
			int	roomLowerRight9[] = {650, 650};
			RoomCell room9 = new RoomCell(roomUpperLeft9, roomLowerRight9);
			_gameCells.put(i, room9);
			_board.addVertex(room9);
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
	
	private void setupCards()
	{
		_deck = Deck.getInstance();
		
		_deck.shuffleDeck();
		_keyCards = _deck.getKeyCards();
		
		List<ArrayList<Card>> playersCards = _deck.getPlayersCards(_players.size());
		
		int i = 0;
		for(ArrayList<Card> cards : playersCards)
		{
			_players.get(i).setCards(cards);
			i++;
		}
	}

	public List<Card> getCurrentPlayerCards() 
	{
		return _players.get(getCurrentPlayer()).getCards();
	}
	
	public List<String> getCurrentPlayerNotes() 
	{
		return _players.get(getCurrentPlayer()).getNotes();
	}
	
	public String getCurrentPlayerName()
	{
		return _players.get(getCurrentPlayer()).getName();
	}
}
