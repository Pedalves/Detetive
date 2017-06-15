package jogo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card
{
	public enum CardType
	{
		Weapon,
		Room,
		Suspect
	}
	
	private final String _name;
	private final CardType _type;

	public BufferedImage Image;
	
	public Card(String name, CardType type)
	{
		_name = name;
		_type = type;
		
		try
		{
			for(int i = 0; i < 6; i++)
			{
				Image = ImageIO.read(new File("Images\\" + _name + ".jpg"));
			}
		}
		catch(IOException e)
		{
			System.out.printf("ERRO ao carregar imagem da carta %s", _name);
		}	
	}
	
	public String GetName()
	{
		return _name;
	}
	
	public CardType GetType()
	{
		return _type;
	}
}
