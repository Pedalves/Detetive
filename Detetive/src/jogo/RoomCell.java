package jogo;

import java.util.HashMap;

public class RoomCell extends Cell
{
	private HashMap<Integer, Integer[]> _posPlayers; 

	private String _name;
	
	public RoomCell(int[] upperLeft, int[] lowerRight, String name) 
	{
		super(upperLeft, lowerRight);
		
		_name = name;
		
		_posPlayers =  new HashMap<Integer, Integer[]>(); 
		
		for(int i = 0; i < 6; i++)
		{
			Integer[] pos = new Integer[2];
			pos[0] = upperLeft[0] + i*25;
			pos[1] = ((lowerRight[1]-upperLeft[1])/2) + upperLeft[1];
			_posPlayers.put(i, pos);
		}
	}
	
	public int GetPosX(int numPlayer)
	{
		return _posPlayers.get(numPlayer)[0];
	}
	
	public int GetPosY(int numPlayer)
	{
		return _posPlayers.get(numPlayer)[1];
	}
	
	public String getName()
	{
		return _name;
	}
}
