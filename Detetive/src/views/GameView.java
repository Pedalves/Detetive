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

import javax.imageio.ImageIO;
import javax.swing.JButton;

import jogo.Dice;
import jogo.Facade;
import jogo.Player;

@SuppressWarnings("serial")
public class GameView extends View 
{
	private BufferedImage bgImage;
	private String gameFile;

	private boolean paintDice = false;
	private Dice dice;
	private BufferedImage diceImage;

	private Facade _facade;

	private HashMap<Integer, Player> players;

	public GameView() {
		super();
		
		players = new HashMap<Integer, Player>();
		players.put(0, new Player(405, 50));

		_facade = new Facade(this, players);
		
		dice = new Dice();

		addMouseListener(_facade);

		try {
			this.bgImage = ImageIO.read(new File("Images\\Tabuleiro-Clue-A.jpg"));
		} catch (IOException e) {
			System.out.println("ERRO ao carregar imagem");
		}
	}

	public GameView(String gameFile) {
		this();

		this.gameFile = gameFile;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, null);

		Graphics2D g2d = (Graphics2D) g;
		Ellipse2D.Double circle = new Ellipse2D.Double(players.get(0).PosX, players.get(0).PosY, 23, 23);
		g2d.setPaint(Color.green);
		g2d.fill(circle);

		if (dice.PaintDice) {
			g.drawImage(dice.DiceImage1, 700, 200, null);
			g.drawImage(dice.DiceImage2, 800, 200, null);
			paintDice = false;
		}
	}

	@Override
	public void setupUI() {
		JButton diceButton = new JButton("Rolar dado");
		diceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		diceButton.addActionListener(e -> {
			dice.RollDice();
			repaint();
		});

		JButton testWindowButton = new JButton("Escolher valor do dado");
		testWindowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		testWindowButton.addActionListener(e -> {
			new PlayTestView();
		});

		add(diceButton);
		add(testWindowButton);
	}

	public void updatePlayer(int x, int y, int player) {
		players.get(player).setXY(x, y);

		repaint();
	}
}
