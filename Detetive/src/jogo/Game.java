package jogo;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

class Game extends Observable
{
	static private Game _game; 
	
	private HashMap<Integer, Player> _players; 
	private int _currentPlayer;
	
	private Board _board;
	private HashMap<Integer, Cell> _gameCells; 
	
	private ArrayList<int[]> _availableCells;
	
	private Deck _deck;
	private List<Card> _keyCards;

	private Dice _dice;
	
	private Game(ArrayList<String> players)
	{	
		_board = new Board();
		_dice = new Dice();
		
		_gameCells = new HashMap<Integer, Cell>();
		_availableCells = null;
		
		setupBoard();
	
		_players = new HashMap<Integer, Player>();
		
		int i = 0;
		
		for(String gamePlayer:players)
		{
			Player temp = null;
			
			switch(gamePlayer)
			{
			case "green":
				temp = new Player(400, 50, "Green", Color.green);
				temp.setCell(_gameCells.get(183));
				break;
			case "mustard":
				temp = new Player(50, 475, "Mustard", Color.yellow);
				temp.setCell(_gameCells.get(185));
				break;
			case "peacock":
				temp = new Player(625, 200, "Peacock", Color.blue);
				temp.setCell(_gameCells.get(184));
				break;
			case "plum":
				temp = new Player(625, 525, "Plum", Color.magenta);
				temp.setCell(_gameCells.get(186));
				break;
			case "scarlet":
				temp = new Player(225, 650, "Scarlet", Color.red);
				temp.setCell(_gameCells.get(187));
				break;
			case "white":
				temp = new Player(275, 50, "White", Color.white);
				temp.setCell(_gameCells.get(182));
				break;
			}
			
			_players.put(i, temp);
			i++;
		}
		
		_currentPlayer = 0;
		
		setupCards();
	}
	
	private Game(String savedGame)
	{	
		_board = new Board();
		_dice = new Dice();
		
		_gameCells = new HashMap<Integer, Cell>();
		_availableCells = null;
		
		setupBoard();
	
		_players = new HashMap<Integer, Player>();		
		_deck = Deck.getInstance();
		load(savedGame);
	}
	
	static public Game getInstance(ArrayList<String> players)
	{
		if(_game == null)
		{
			_game = new Game(players);
		}
		
		return _game;
	}
	
	static public Game getInstance(String savedGame)
	{
		if(_game == null)
		{
			_game = new Game(savedGame);
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
	
	public int getCurrentPlayer()
	{
		return _currentPlayer;
	}
	
	public void initializePlayers()
	{
		for(int i = 0; i < _players.size(); i++)
		{
			int tempPos[] = {_players.get(i).getX(), _players.get(i).getY()};
			
			/*************************************************************/
			/***                  Notify View                          ***/
			/*************************************************************/
			
			Object args[] = {(Object) 1, (Object)_players.get(i).getColor(), (Object)tempPos}; 
			
			setChanged();
			notifyObservers(args);
		}
	}
	
	public void newTurn()
	{
		if(_currentPlayer == _players.size() - 1)
		{
			_currentPlayer = 0;
		}
		else
		{
			_currentPlayer++;
		}
		
		int cont = 0;
		
		while(!_players.get(_currentPlayer).getInGame())
		{			
			if(cont == _players.size())
			{
				Facade.getInstance().forceEndGame();
				return;
			}
			cont++;
			
			if(_currentPlayer == _players.size() - 1)
			{
				_currentPlayer = 0;
			}
			else
			{
				_currentPlayer++;
			}
		}
		
		if(_players.get(getCurrentPlayer()).getCell() instanceof RoomCell)
		{
			_players.get(_currentPlayer).setCanGuess(true); 
		}
		_players.get(_currentPlayer).setCanWalk(true);
		
		/*************************************************************/
		/***                  Notify View                          ***/
		/*************************************************************/
		
		Object args[] = {(Object) 5}; 
		
		setChanged();
		notifyObservers(args);
	}
	
	public void newClickPosition(int x, int y, int player)
	{
		int xyPlayer[] = {-1, -1};
		
		if(!_players.get(player).getCanWalk())
		{
			return;
		}
		
		for(int pos : _gameCells.keySet())
		{
			Cell cell = _gameCells.get(pos);
			
			if(cell.isInside(x, y))
			{
				int temp[];
				if(cell instanceof RoomCell)
				{
					temp = new int[] {((RoomCell)cell).GetPosX(player), ((RoomCell)cell).GetPosY(player)};	
					
					if(_availableCells != null)
					{
						for(int[] availableCell : _availableCells)
						{
							if(availableCell[0] == cell.getX() && availableCell[1] == cell.getY())
							{
								_availableCells = null;
								xyPlayer = temp;
								
								_players.get(player).getCell().setOcuppied(false);
								
								_players.get(player).setCell(cell);
								_players.get(player).setCanWalk(false);
								_players.get(player).setCanGuess(true);
								
								break;
							}
						}
					}
				}
				else
				{
					temp = new int[] {cell.getX(), cell.getY()};
					
					if(_availableCells != null)
					{
						for(int[] availableCell : _availableCells)
						{
							if(availableCell[0] == temp[0] && availableCell[1] == temp[1])
							{
								_availableCells = null;
								xyPlayer = temp;
								
								_players.get(player).getCell().setOcuppied(false);
								cell.setOcuppied(true);
								
								_players.get(player).setCell(cell);
								_players.get(player).setCanGuess(false);
								_players.get(player).setCanWalk(false);
								
								break;
							}
						}
					}
				}				
			}
		}
		
		if(xyPlayer[0] != -1)
		{	
			/*************************************************************/
			/***                  Notify View                          ***/
			/*************************************************************/
			
			Object args[] = {(Object) 1, (Object)_players.get(player).getColor(), (Object)xyPlayer}; 
			
			setChanged();
			notifyObservers(args);
		}		
	}
	
	public void setDiceValue(int val)
	{
		if(!_players.get(_currentPlayer).getCanWalk())
		{
			return;
		}
		
		if(val == 0)
		{
			val = _dice.RollDice();
		}
		_availableCells = _board.getAvailableCells(val, _players.get(_currentPlayer).getCell());
			
		/*************************************************************/
		/***                  Notify View                          ***/
		/*************************************************************/
		
		Object cellInfos[] = {(Object) 2, (Object)_availableCells.clone()}; 
		
		setChanged();
		notifyObservers((Object)cellInfos);
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
			
			/*************************************************************/
			/***                  Montando quartos                     ***/
			/*************************************************************/
			
			//Cozinha
			int	roomUpperLeft[] = {50, 50}; 
			int	roomLowerRight[] = {200, 200}; 
			RoomCell room = new RoomCell(roomUpperLeft, roomLowerRight, "Cozinha");
			_gameCells.put(i, room);
			_board.addVertex(room);
			i++;
			
			//Sala de musica
			int	roomUpperLeft2[] = {250, 100}; 
			int	roomLowerRight2[] = {450, 250};
			RoomCell room2 = new RoomCell(roomUpperLeft2, roomLowerRight2, "Sala De Musica");
			_gameCells.put(i, room2);
			_board.addVertex(room2);
			i++;
			
			//Jardim de inverno
			int	roomUpperLeft3[] = {500, 75}; 
			int	roomLowerRight3[] = {650, 175};
			RoomCell room3 = new RoomCell(roomUpperLeft3, roomLowerRight3, "Jardim De Inverno");
			_gameCells.put(i, room3);
			_board.addVertex(room3);
			i++;
			
			//Sala de jantar
			int	roomUpperLeft4[] = {50, 300}; 
			int	roomLowerRight4[] = {250, 450};
			RoomCell room4 = new RoomCell(roomUpperLeft4, roomLowerRight4, "Sala De Jantar");
			_gameCells.put(i, room4);
			_board.addVertex(room4);
			i++;
			
			//Salão de jogos
			int	roomUpperLeft5[] = {500, 250}; 
			int	roomLowerRight5[] = {650, 375};
			RoomCell room5 = new RoomCell(roomUpperLeft5, roomLowerRight5, "Salão De Jogos");
			_gameCells.put(i, room5);
			_board.addVertex(room5);
			i++;
			
			//Biblioteca
			int	roomUpperLeft6[] = {475, 425}; 
			int	roomLowerRight6[] = {650, 500}; 
			RoomCell room6 = new RoomCell(roomUpperLeft6, roomLowerRight6, "Biblioteca");
			_gameCells.put(i, room6);
			_board.addVertex(room6);
			i++;
			
			//Sala de estar
			int	roomUpperLeft7[] = {50, 525}; 
			int	roomLowerRight7[] = {225, 650};
			RoomCell room7 = new RoomCell(roomUpperLeft7, roomLowerRight7, "Sala De Estar");
			_gameCells.put(i, room7);
			_board.addVertex(room7);
			i++;
			
			//Entrada
			int	roomUpperLeft8[] = {275, 500}; 
			int	roomLowerRight8[] = {425, 650};
			RoomCell room8 = new RoomCell(roomUpperLeft8, roomLowerRight8, "Entrada");
			_gameCells.put(i, room8);
			_board.addVertex(room8);
			i++;
			
			//Escritorio
			int	roomUpperLeft9[] = {475, 575}; 
			int	roomLowerRight9[] = {650, 650};
			RoomCell room9 = new RoomCell(roomUpperLeft9, roomLowerRight9, "Escritorio");
			_gameCells.put(i, room9);
			_board.addVertex(room9);
			i++;
			
			/*************************************************************/

			
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
	
	public Color getCurrentPlayerColor()
	{
		return _players.get(getCurrentPlayer()).getColor();
	}
	
	public String getCurrentPlayerRoom()
	{
		if(_players.get(getCurrentPlayer()).getCell() instanceof RoomCell)
		{
			return ((RoomCell)_players.get(getCurrentPlayer()).getCell()).getName(); 
		}
		return null;
	}
	
	public String guessResult(String[] guess)
	{
		if(!_players.get(getCurrentPlayer()).getCanGuess())
		{
			return "Apenas 1 palpite por turno é permitido";
		}
		
		for(int player: _players.keySet())
		{
			if(guess[0] == _players.get(player).getName())
			{				
				_players.get(player).setCanWalk(true);
				int[] temp = {_players.get(getCurrentPlayer()).getCell().getX(), _players.get(getCurrentPlayer()).getCell().getY()};
				ArrayList<int[]> cellsPositions = new ArrayList<int[]>();
				cellsPositions.add(temp);
				_availableCells = cellsPositions;
				
				newClickPosition(_players.get(getCurrentPlayer()).getCell().getX()+1, _players.get(getCurrentPlayer()).getCell().getY()+1, player);
				break;
			}
		}
		
		_players.get(getCurrentPlayer()).setCanGuess(false);
		int i = _currentPlayer == _players.size() - 1 ? 0 : _currentPlayer + 1;
		
		while(i != _currentPlayer)
		{
			List<Card> cards = _players.get(i).getCards();
			
			for(Card c : cards)
			{
				if(guess[0] == c.GetName())
				{
					_players.get(_currentPlayer).addNote(c.GetName());
					return "Jogador " + _players.get(i).getName() + " possui a carta " + c.GetName();
				}
				else if(guess[1] == c.GetName())
				{
					_players.get(_currentPlayer).addNote(c.GetName());
					return "Jogador " + _players.get(i).getName() + " possui a carta " + c.GetName();
				}
				else if(guess[2] == c.GetName())
				{
					_players.get(_currentPlayer).addNote(c.GetName());
					return "Jogador " + _players.get(i).getName() + " possui a carta " + c.GetName();
				}
			}
			
			i = i == _players.size() - 1 ? 0 : i + 1;
		}
		
		return "Nenhum jogador possui as cartas do palpite";
	}
	
	public String Accusation(String[] accusation)
	{
		int cont = 3;
		String keyCards = "";
		
		for(Card c : _keyCards)
		{
			keyCards += c.GetName() + " ";
			if(accusation[0] == c.GetName())
			{
				cont--;
			}
			else if(accusation[1] == c.GetName())
			{
				cont--;
			}
			else if(accusation[2] == c.GetName())
			{
				cont--;
			}
		}
		
		if(cont > 0)
		{			
			_players.get(_currentPlayer).setInGame(false);
			
			return "Perdeu. Cartas certas: " + keyCards;
		}
		
		return "Acertou!";
	}
	
	public void resetGame()
	{
		_deck.resetDeck();
		
		/*************************************************************/
		/***                  Notify View                          ***/
		/*************************************************************/
		
		Object args[] = {(Object) 0}; 
		
		setChanged();
		notifyObservers(args);
		
		_game = null;
	}
	
	@Override
	public void addObserver(Observer o)
	{
		_dice.addObserver(o);
		super.addObserver(o);
	}
	
	public void save(String file)
	{
		try {
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			
			// Salva cartas chave
			for(Card c : _keyCards)
			{
				writer.println(c.GetName());
			}
			
			// Salva qual o jogador corrente
			writer.println(Integer.toString(_currentPlayer));
			
			// Salva jogadores
			for(int i = 0; i<_players.size(); i++)
			{
				// Salva nome
				writer.println(_players.get(i).getName());
				
				// Salva cor
				writer.println(Integer.toString(_players.get(i).getColor().getRGB()));
				
				// Salva coordenadas
				writer.println(Integer.toString(_players.get(i).getX()));
				writer.println(Integer.toString(_players.get(i).getY()));
				
				// Salva bloco de notas
				String notes = "";
				
				for(String note : _players.get(i).getNotes())
				{
					notes += note + ";";
				}
				
				writer.println(notes);
				
				// Salva cartas
				String cards = "";
				
				for(Card c : _players.get(i).getCards())
				{
					cards += c.GetName() + ";";
				}
				
				writer.println(cards);
				
				// Salva se pode andar
				writer.println(Boolean.toString(_players.get(i).getCanWalk()));
				
				// Salva se pode dar palpite
				writer.println(Boolean.toString(_players.get(i).getCanGuess()));
				
				// Salva se esta no jogo
				writer.println(Boolean.toString(_players.get(i).getInGame()));
			}	
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void load(String file)
	{
		BufferedReader br = null;
		FileReader fr = null;
		
		BufferedReader br2 = null;
		FileReader fr2 = null;
		
		try 
		{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			/* -keyCards
			 * -currentPlayer 
			 * -Player:
			 * - Posição
			 * - Bloca de notas 
			 * - Cartas
			 * - Can Walk 
			 * - Can Guess 
			 * - InGame
			 * */
			
			/*************************************************************/
			/***                  Montando keyCards                    ***/
			/*************************************************************/
			
			List<Card> cards = new ArrayList<Card>();
			for(int cont = 0; cont < 3; cont++)
			{
				String keyCard = br.readLine();
				cards.add(_deck.getCardByName(keyCard));
			}
			_keyCards = cards;
			
			/*************************************************************/
			
			/*************************************************************/
			/***                  Pegando currentPlayer                ***/
			/*************************************************************/
			
			_currentPlayer = Integer.parseInt(br.readLine());
			
			/*************************************************************/

			/*************************************************************/
			/***                  Montando players                     ***/
			/*************************************************************/
			int i = 0;
			
			String playersInfo;

			while ((playersInfo = br.readLine()) != null)
			{	
				String name = playersInfo;
				
				Player temp = null;
				
				//Pegando cor
//				Color color;
//				try {
//					Field field = Class.forName("java.awt.Color").getField(params[1].toLowerCase());
//				    color = (Color)field.get(null);
//				} catch (Exception e) {
//				    color = null; // Not defined
//				}
				Color color = new Color(Integer.parseInt(br.readLine()));
				int posX = Integer.parseInt(br.readLine());
				int posY = Integer.parseInt(br.readLine());
				
				temp = new Player(posX, posY, name, color);
				_players.put(i, temp);
				i++;
				
				//Bloco de notas
				String notes[] = br.readLine().split(";");
				for(String note : notes)
				{
					if(note.length() > 3)
					{
						temp.addNote(note);	
					}	
				}
				
				//Cartas
				String playerCardsNames[] = br.readLine().split(";");
				List<Card> playersCards = new ArrayList<Card>();
				
				for(String card : playerCardsNames)
				{
					if(card.length() > 3)
					{
						playersCards.add(_deck.getCardByName(card));
					}
				}
				temp.setCards(playersCards);
				
				temp.setCanWalk(Boolean.parseBoolean(br.readLine()));
				temp.setCanGuess(Boolean.parseBoolean(br.readLine()));
				temp.setInGame(Boolean.parseBoolean(br.readLine()));
			}
			
			/*************************************************************/
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
