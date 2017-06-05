package jogo;

import java.util.HashMap;

public class RoomCell extends Cell
{
	private HashMap<Integer, Integer[]> _posPlayers; 

	public RoomCell(int[] upperLeft, int[] lowerRight) 
	{
		super(upperLeft, lowerRight);
		
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
		System.out.println("ENTROU NO POSX ROOM");
		return _posPlayers.get(numPlayer)[0];
	}
	
	public int GetPosY(int numPlayer)
	{
		System.out.println("ENTROU NO POSY ROOM");
		return _posPlayers.get(numPlayer)[1];
	}
}
