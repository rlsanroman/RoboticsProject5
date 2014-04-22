import java.util.ArrayList;


public class Vertex {
	ArrayList<Edge> inList;
	ArrayList<Edge> outList;
	Vertex parent;
	
	int distance;
	Point id;
	
	Vertex(Point sid)
	{
		distance = Integer.MAX_VALUE;
		id = sid;
	}
	
	void changeParent(Vertex p)
	{
		parent = p;
	}
	
	int getWeightToParent(Vertex p)
	{
		for(Edge e : inList)
		{
			if(e.eVertP.equals(parent))
				return e.weight;
		}
		return -1;
	}
	
	int getDist() { return distance; }
	
}
