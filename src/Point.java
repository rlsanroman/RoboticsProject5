
public class Point {
	public int x;
	public int y;
	Point(int X, int Y)
	{
		x = X;
		y = Y;
	}
	
	public int getDistance(Point p)
	{
		return (int) Math.round(Math.sqrt(Math.pow(this.x - p.x,2) + Math.pow(this.y - p.y,2)));
	}
	
	public static int getDistance(Point p1, Point p2)
	{
		return (int) Math.round(Math.sqrt(Math.pow(p1.x - p2.x,2) + Math.pow(p1.y - p2.y,2)));
	}
	
	public boolean equals(Point p)
	{
		if (this.x == p.x && this.y == p.y)
			return true;
		return false;
	}
}
