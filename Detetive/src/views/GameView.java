package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

import jogo.Dice;
import jogo.Facade;

@SuppressWarnings("serial")
public class GameView extends View implements Observer
{
	private BufferedImage bgImage;
	private String gameFile;

	private Dice dice;

	private Facade _facade;

	private HashMap<Color, int[]> _pawns;

	private JLabel _currentPlayerName;
	
	public GameView() {
		super();
		
		_pawns = new HashMap<Color, int[]>();
		
		/*************************************************************/
		/***                  Initialize Facade                    ***/
		/*************************************************************/
		
		_facade = Facade.getInstance();
		_facade.setGameView(this);
		_facade.initializePlayers();
		
		/*************************************************************/
		
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
		
		for(Color color : _pawns.keySet())
		{
			Graphics2D g2d = (Graphics2D) g;
			Ellipse2D.Double circle = new Ellipse2D.Double(_pawns.get(color)[0], _pawns.get(color)[1], 23, 23);
			g2d.setPaint(color);
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
			new PlayTestView();
		});
		
		JButton showCardsButton = new JButton("Ver cartas");
		showCardsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		showCardsButton.addActionListener(e -> {
			new PlayerCardsView();
		});
		
		JButton showNotesButton = new JButton("Ver bloco de notas");
		showNotesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		showNotesButton.addActionListener(e -> {
			new PlayerNotesView();
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

	public void updatePlayer(Color color, int[] pos) {
		if(_pawns.containsKey(color))
		{
			_pawns.get(color)[0] = pos[0];
			_pawns.get(color)[1] = pos[1];
		}
		else
		{
			_pawns.put(color, pos);
		}
		
		repaint();
	}
	
	public void endGame()
	{
		observable.changePanel(new IntroView());
	}
	
	@Override
	public void update(Observable o, Object arg) 
	{

	}
}
