package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

import jogo.Dice;
import jogo.Facade;
import jogo.Player;

@SuppressWarnings("serial")
public class GameView extends View 
{
	private BufferedImage bgImage;
	private String gameFile;

	private Dice dice;

	private Facade _facade;

	private HashMap<Integer, Player> players;
	private HashMap<Integer, Color> _playersColors;

	private JLabel _currentPlayerName;
	
	public GameView(ArrayList<String> gamePlayers) {
		super();
		
		players = new HashMap<Integer, Player>();
		_playersColors = new HashMap<Integer, Color>();
		
		int i = 0;
		for(String gamePlayer:gamePlayers)
		{
			Player temp = null;
			
			switch(gamePlayer)
			{
			case "green":
				temp = new Player(400, 50, "Green");
				_playersColors.put(i, Color.green);
				break;
			case "mustard":
				temp = new Player(50, 475, "Mustard");
				_playersColors.put(i, Color.yellow);
				break;
			case "peacock":
				temp = new Player(625, 200, "Peacock");
				_playersColors.put(i, Color.blue);
				break;
			case "plum":
				temp = new Player(625, 525, "Plum");
				_playersColors.put(i, Color.magenta);
				break;
			case "scarlet":
				temp = new Player(225, 650, "Scarlet");
				_playersColors.put(i, Color.red);
				break;
			case "white":
				temp = new Player(275, 50, "White");
				_playersColors.put(i, Color.white);
				break;
			}
			
			players.put(i, temp);
			i++;
		}
		

		_facade = Facade.getInstance(this, players);
		
		dice = new Dice();
		
		_currentPlayerName = new JLabel("Current Player: " + _facade.getCurrentPlayerName());
		add(_currentPlayerName);

		addMouseListener(_facade);

		try {
			this.bgImage = ImageIO.read(new File("Images\\Tabuleiro-Clue-A.jpg"));
		} catch (IOException e) {
			System.out.println("ERRO ao carregar imagem");
		}
	}

	public GameView(String gameFile) {
		//this();

		this.gameFile = gameFile;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, null);
		
		for(int player : players.keySet())
		{
			Graphics2D g2d = (Graphics2D) g;
			Ellipse2D.Double circle = new Ellipse2D.Double(players.get(player).PosX, players.get(player).PosY, 23, 23);
			g2d.setPaint(_playersColors.get(player));
			g2d.fill(circle);
		}
		if (dice.PaintDice) {
			g.drawImage(dice.DiceImage1, 700, 200, null);
			g.drawImage(dice.DiceImage2, 800, 200, null);
		}
		
		_currentPlayerName.setText("Current Player: " + _facade.getCurrentPlayerName());
	}

	@Override
	public void setupUI() {
		JButton diceButton = new JButton("Rolar dado");
		diceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		diceButton.addActionListener(e -> {
			_facade.newDiceValue(dice.RollDice());
			repaint();
		});

		JButton testWindowButton = new JButton("Escolher valor do dado");
		testWindowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		testWindowButton.addActionListener(e -> {
			new PlayTestView(_facade);
		});
		
		JButton showCardsButton = new JButton("Ver cartas");
		showCardsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		showCardsButton.addActionListener(e -> {
			new PlayerCardsView(_facade);
		});
		
		JButton showNotesButton = new JButton("Ver bloco de notas");
		showNotesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		showNotesButton.addActionListener(e -> {
			new PlayerNotesView(_facade);
		});
		
		JButton guessButton = new JButton("Fazer palpite");
		guessButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		guessButton.addActionListener(e -> {
			new GuessView();
		});
		
		JButton accusationButton = new JButton("Fazer acusacao");
		accusationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		accusationButton.addActionListener(e -> {
			new AccusationView();
		});

		JButton turnButton = new JButton("Fim do turno");
		turnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		turnButton.addActionListener(e -> {
			_facade.endTurn();
			_currentPlayerName.setText("Current Player: " + _facade.getCurrentPlayerName());
		});
		
		add(diceButton);
		add(testWindowButton);
		add(showCardsButton);
		add(showNotesButton);
		add(guessButton);
		add(accusationButton);
		add(turnButton);
	}

	public void updatePlayer(int x, int y, int player) {
		players.get(player).setXY(x, y);

		repaint();
	}
}
