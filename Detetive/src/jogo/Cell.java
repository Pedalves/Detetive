package jogo;

public class Cell
{
	private final int _posLowerLeft[];
	private final int _posUpperRight[];
	private final int _posXCenter;
	private final int _posYCenter;
	
	private boolean _ocuppied;
	private final boolean _isRoom;
	
	public Cell(int[] lowerLeft, int[] upperRight)
	{
		_posLowerLeft = lowerLeft;
		_posUpperRight = upperRight;
		
		_posXCenter = (upperRight[0] - lowerLeft[0])/2;
		_posYCenter = (lowerLeft[1] - upperRight[1])/2;
		
		_isRoom = false;
		_ocuppied = false;
	}
	
	public Cell(int[] lowerLeft, int[] upperRight, boolean isRoom)
	{
		_posLowerLeft = lowerLeft;
		_posUpperRight = upperRight;
		
		_posXCenter = (upperRight[0] - lowerLeft[0])/2;
		_posYCenter = (lowerLeft[1] - upperRight[1])/2;
		
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
		return _posXCenter;
	}

	public int getY()
	{
		return _posYCenter;
	}
	
	public boolean isRoom()
	{
		return _isRoom;
	}
	
	public boolean isInside(int x, int y)
	{
		if(x > _posLowerLeft[0] && x < _posUpperRight[0] && 
		   y < _posLowerLeft[1] && y > _posUpperRight[1])
		{
			return true;
		}
		
		return false;
	}
}
