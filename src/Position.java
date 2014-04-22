import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;


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
	
	public String toString()
	{
		return "(" + this.x + "," + this.y + ")";
	}
	
	public boolean equals(Position p)
	{
		if (this.x == p.x && this.y == p.y)
			return true;
		return false;
	}
	
	public ArrayList<Position> findLeftRightPoints(Vector<Position> positions)
	{
		int closestLeftX = Integer.MAX_VALUE;
		int closestRightX = Integer.MAX_VALUE;
		Position closestLeft = null;
		Position closestRight = null;
		ArrayList<Position> neighbors = new ArrayList<Position>();
		for(Position pos : positions)
		{
			if(this.x == pos.x) //same line
				continue;
			if(pos.x > 500 || pos.y > 500)
				continue;
			int dist = Math.abs(pos.x - this.x);
			if(pos.x < this.x) //temp is to the left
			{
				if(dist <= closestLeftX)
				{
					closestLeftX = dist;
					closestLeft = pos;
					neighbors.add(closestLeft);
				}
			}
			else if(pos.x > this.x) //temp is to the right
			{
				if(dist <= closestRightX)
				{
					closestRightX = dist;
					closestRight = pos;
					neighbors.add(closestRight);
				}
			}
		}
		/*
		if(closestRight != null)
			neighbors.add(closestRight);
		if(closestLeft != null)
		neighbors.add(closestLeft);
		for(Position pos : positions)
		{
			if(closestRight != null)
				if(pos.x == closestRight.x && !pos.equals(closestRight))
				{
					neighbors.add(pos);
				}
			if(closestLeft != null)
				if(pos.x == closestLeft.x && !pos.equals(closestLeft))
				{
					neighbors.add(pos);
				}
		}*/
		return neighbors;
	}
	
	public boolean equals(Point p)
	{
		if(this.x == p.x && this.y == p.y)
			return true;
		return false;
	}
	public Position findClosest(ArrayList<Position> positions)
	{
		Position minDist = null;
		int min = Integer.MAX_VALUE;
		for(Position pos : positions)
		{
			if(pos.equals(this))
				continue;
			int distance = getDistance(this,pos);
			if(this.x != pos.x) //make sure its not the same line
				if(distance < min)
				{
					minDist = pos;
					min = distance;
				}
		}
		return minDist;
	}
}
