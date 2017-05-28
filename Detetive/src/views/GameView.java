package views;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import jogo.Dice;
import jogo.Player;


@SuppressWarnings("serial")
public class GameView extends View 
{	
	private BufferedImage bgImage;
	private String gameFile;
	
	private boolean paintDice = false;
	private Dice dice;
	private BufferedImage diceImage;
	
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private Player player5;
	private Player player6;
	       	
	public GameView()
	{
		super();
		
		player1 = new Player("Green", 405, 50);
		player2 = new Player("Mustard", 55, 475);
		player3 = new Player("Peacock", 630, 200);
		player4 = new Player("Plum", 630, 525);
		player5 = new Player("Scarlet", 230, 652);
		player6 = new Player("White", 280, 50);
		
		dice = new Dice();
		
		try
		{
			this.bgImage = ImageIO.read(new File("Images\\Tabuleiro-Clue-A.jpg"));
		}
		catch(IOException e)
		{
			System.out.println("ERRO ao carregar imagem");
		}		
	}
	
	public GameView(String gameFile)
	{
		this();

		this.gameFile = gameFile;		
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    g.drawImage(bgImage, 0, 0, null);
	    
	    //Players
	    if(player1 != null)
	    {
		    g.drawImage(player1.pawnImage, player1.PosX, player1.PosY, null);
		    g.drawImage(player2.pawnImage, player2.PosX, player2.PosY, null);
		    g.drawImage(player3.pawnImage, player3.PosX, player3.PosY, null);
		    g.drawImage(player4.pawnImage, player4.PosX, player4.PosY, null);
		    g.drawImage(player5.pawnImage, player5.PosX, player5.PosY, null);
		    g.drawImage(player6.pawnImage, player6.PosX, player6.PosY, null);
	    }
	    
	    if(dice.PaintDice)
	    {
	    	g.drawImage(dice.DiceImage1, 700, 200, null);
	    	g.drawImage(dice.DiceImage2, 800, 200, null);
	    	paintDice = false;
	    }
	}

	@Override
	public void setupUI() 
	{
		JButton diceButton = new JButton("Rolar dado");
		diceButton.setAlignmentX(Component.CENTER_ALIGNMENT);		
		diceButton.addActionListener(e -> {
			dice.RollDice();
			repaint();
		});
		
		add(diceButton);
	}
}
