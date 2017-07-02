package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import jogo.Facade;

@SuppressWarnings("serial")
public class GameView extends View implements Observer
{
	private BufferedImage bgImage;

	private Facade _facade;

	private HashMap<Color, int[]> _pawns;

	private JLabel _currentPlayerName;
	
	private List<int[]> _availableCellsPos;
	
	private BufferedImage[] _diceImages;
	
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
				
		_currentPlayerName = new JLabel("Current Player: " + _facade.getCurrentPlayerName());
		add(_currentPlayerName);

		addMouseListener(_facade);

		try {
			this.bgImage = ImageIO.read(new File("Images\\Tabuleiro-Clue-A.jpg"));
		} catch (IOException e) {
			System.out.println("ERRO ao carregar imagem");
		}
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
				g2d.setPaint(new Color(0.17f,0.64f,0.17f));
				g2d.fillRect(pos[0]+1, pos[1]+1, 23, 23);
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
		if (_diceImages != null) 
		{
			g.drawImage(_diceImages[0], 700, 200, null);
			g.drawImage(_diceImages[1], 800, 200, null);
			_diceImages = null;
		}
		
		_currentPlayerName.setText("Current Player: " + _facade.getCurrentPlayerName());
	}

	@Override
	public void setupUI() 
	{
		JButton diceButton = new JButton("Rolar dado");
		diceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		diceButton.addActionListener(e -> {
			_facade.rollDice();
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
		
		JButton saveButton = new JButton("Salvar jogo");
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveButton.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("clue", "clue");
		    chooser.setFileFilter(filter);

		    if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		    {	
		    	String name = chooser.getSelectedFile().getAbsolutePath();
		    	_facade.saveGame(name);
		    }
		});
		
		add(diceButton);
		add(testWindowButton);
		add(showCardsButton);
		add(showNotesButton);
		add(guessButton);
		add(accusationButton);
		add(turnButton);
		add(Box.createRigidArea(new Dimension(650, 0)));
		add(saveButton);
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
		case 3:
			_diceImages = (BufferedImage[]) args[1];
			break;
		default:
			break;
		}
		
		repaint();
	}
}
