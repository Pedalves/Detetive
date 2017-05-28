package jogo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player 
{
	public int PosX;
	public int PosY;
	//public float Casa;
	
	public String Name;
	public BufferedImage pawnImage;
	
	public Player(String name, int posX, int posY)
	{		
		Name = name;
		PosX = posX;
		PosY = posY;
		
		try
		{
			pawnImage = ImageIO.read(new File("Images\\" + Name + ".png"));
		}
		catch(IOException e)
		{
			System.out.println("ERRO ao carregar imagem do peao");
		}		
	}
}
