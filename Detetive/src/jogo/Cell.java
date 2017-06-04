package jogo;

public class Cell
{
	private final int _posUpperLeft[];
	private final int _posLowerRight[];
	
	private boolean _ocuppied;
	private final boolean _isRoom;
	
	public Cell(int[] upperLeft, int[] lowerRight)
	{	
		_posUpperLeft = upperLeft;
		_posLowerRight = lowerRight;
		
		_isRoom = false;
		_ocuppied = false;		
	}
	
	public Cell(int[] lowerLeft, int[] upperRight, boolean isRoom)
	{
		_posUpperLeft = lowerLeft;
		_posLowerRight = upperRight;
		
		_isRoom = isRoom;
		
		_ocuppied = false;
	}
	
	public void setOcuppied(boolean ocuppied)
	{
		_ocuppied = ocuppied;
	}
	
	public boolean getOcuppied()
	{
		return _ocuppied;
	}
	
	public int getX()
	{
		return _posUpperLeft[0];
	}

	public int getY()
	{
		return _posUpperLeft[1];
	}
	
	public boolean isRoom()
	{
		return _isRoom;
	}
	
	public boolean isInside(int x, int y)
	{
		if(x > _posUpperLeft[0] && x < _posLowerRight[0] && 
		   y > _posUpperLeft[1] && y < _posLowerRight[1])
		{
			return true;
		}
		
		return false;
	}
}
