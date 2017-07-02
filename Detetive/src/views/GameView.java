package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.color.ColorSpace;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
	
	private List<int[]> _availableCellsPos;
	
	public GameView() 
	{
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

	public GameView(String gameFile) 
	{
		//this();

		this.gameFile = gameFile;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, null);
		Graphics2D g2d = (Graphics2D) g;

		if(_availableCellsPos != null)
		{
			for(int[] pos : _availableCellsPos)
			{
				//Rectangle rect = new Rectangle(pos[0], pos[1], 23, 23);
//				float thickness = 1.5f;
//				Stroke oldStroke = g2d.getStroke();
//				g2d.setStroke(new BasicStroke(thickness));
				g2d.setPaint(new Color(0.17f,0.64f,0.17f));
				g2d.fillRect(pos[0]+1, pos[1]+1, 23, 23);
//				g2d.setStroke(oldStroke);
			}
			_availableCellsPos = null;
		}
		for(Color color : _pawns.keySet())
		{
			Ellipse2D.Double circle = new Ellipse2D.Double(_pawns.get(color)[0], _pawns.get(color)[1], 23, 23);
			g2d.setPaint(color);
			g2d.fill(circle);
			Ellipse2D.Double border = new Ellipse2D.Double(_pawns.get(color)[0], _pawns.get(color)[1], 23, 23);
			g2d.setPaint(Color.BLACK);
			g2d.draw(border);
		}
		if (dice.PaintDice) {
			g.drawImage(dice.DiceImage1, 700, 200, null);
			g.drawImage(dice.DiceImage2, 800, 200, null);
		}
		
		_currentPlayerName.setText("Current Player: " + _facade.getCurrentPlayerName());
	}

	@Override
	public void setupUI() 
	{
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

	private void updatePlayer(Color color, int[] pos) 
	{
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
	
	private void endGame()
	{
		observable.changePanel(new IntroView());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) 
	{
		Object args[] = (Object[])arg;
		
		switch((int)args[0])
		{
		case 0:
			endGame();
			break;
		case 1:
			updatePlayer((Color)args[1], (int[])args[2]);
			break;
		case 2:
			_availableCellsPos = (List<int[]>) args[1];
			break;
		default:
			break;
		}
		
		repaint();
	}
}
