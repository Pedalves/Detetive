package jogo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

//import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

public class Dice 
{
	private BufferedImage _diceImage1;
	private BufferedImage _diceImage2;
	
	private BufferedImage[] diceImages;
	
	public Dice()
	{
		diceImages = new BufferedImage[6];
		
		try
		{
			for(int i = 0; i < 6; i++)
			{
				diceImages[i] = ImageIO.read(new File("Images\\dado" + Integer.toString(i+1) + ".png"));
			}
		}
		catch(IOException e)
		{
			System.out.println("ERRO ao carregar imagem do dado");
		}	
	}
	
	public int RollDice()
	{
		int num1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
		int num2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
		switch (num1){
			case 1:
				_diceImage1 = diceImages[0];
				break;
			case 2:
				_diceImage1 = diceImages[1];
				break;
			case 3:
				_diceImage1 = diceImages[2];
				break;
			case 4:
				_diceImage1 = diceImages[3];
				break;
			case 5:
				_diceImage1 = diceImages[4];
				break;
			case 6:
				_diceImage1 = diceImages[5];
				break;
			default:
				break;
		}
		switch (num2){
			case 1:
				_diceImage2 = diceImages[0];
				break;
			case 2:
				_diceImage2 = diceImages[1];
				break;
			case 3:
				_diceImage2 = diceImages[2];
				break;
			case 4:
				_diceImage2 = diceImages[3];
				break;
			case 5:
				_diceImage2 = diceImages[4];
				break;
			case 6:
				_diceImage2 = diceImages[5];
				break;
			default:
				break;
		}
		return num1 + num2; 
	}
	
	public BufferedImage[] getDiceImages()
	{
		BufferedImage[] imgs = {_diceImage1,_diceImage2};
		return imgs;
	}
}
