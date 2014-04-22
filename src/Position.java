
public class Position {
	public int x;
	public int y;
	Position(int X, int Y)
	{
		x = X;
		y = Y;
	}
	
	public int getDistance(Position p)
	{
		return (int) Math.round(Math.sqrt(Math.pow(this.x - p.x,2) + Math.pow(this.y - p.y,2)));
	}
	
	public static int getDistance(Position p1, Position p2)
	{
		return (int) Math.round(Math.sqrt(Math.pow(p1.x - p2.x,2) + Math.pow(p1.y - p2.y,2)));
	}
	
	public boolean equals(Position p)
	{
		if (this.x == p.x && this.y == p.y)
			return true;
		return false;
	}
}
