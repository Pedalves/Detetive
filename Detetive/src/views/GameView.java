package views;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class GameView extends View 
{	
	private BufferedImage bgImage;
	private String gameFile;
	       	
	@Override
	protected void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    g.drawImage(bgImage, 0, 0, null);
	}
	
	public GameView()
	{
		super();
		
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
	public void setupUI() 
	{
		
	}
}
