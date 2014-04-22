import java.awt.Graphics2D;


public class LocPoint {
	public int x;
	public int y;
	LocPoint(int X, int Y)
	{
		x = X;
		y = Y;
	}
	
	public int getDistance(LocPoint p)
	{
		return (int) Math.round(Math.sqrt(Math.pow(this.x - p.x,2) + Math.pow(this.y - p.y,2)));
	}
	
	public static int getDistance(LocPoint p1, LocPoint p2)
	{
		return (int) Math.round(Math.sqrt(Math.pow(p1.x - p2.x,2) + Math.pow(p1.y - p2.y,2)));
	}
	
	public boolean equals(LocPoint p)
	{
		if (this.x == p.x && this.y == p.y)
			return true;
		return false;
	}
	
	public void drawPoint(Graphics2D g)
	{
		g.fillOval(x, y, 5, 5);
	}
}
