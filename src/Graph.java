import java.util.ArrayList;


public class Graph {
	ArrayList<Vertex> vertList;
	ArrayList<Edge> edgeList;
	
	void resetDijkstras()
	{
		for(Vertex v : vertList)
		{
			v.changeParent(null);
			v.distance = Integer.MAX_VALUE;
		}
	}
	
	void addVertex(Vertex p)
	{
		vertList.add(p);
	}
	
	void addEdge(Vertex s, Vertex e, int weight)
	{
		Edge newedge = new Edge(s,e,weight);
		edgeList.add(newedge);
		s.outList.add(newedge);
		e.inList.add(newedge);
	}
	
	boolean adjacentCheck(Vertex source, Vertex end)
	{
		boolean found = false;
		for(Edge e : source.outList)
		{
			if(e.eVertP.id.equals(end.id))
				found = true;
		}
		for(Edge e : source.inList)
		{
			if(e.eVertP.id.equals(end.id))
					found = true;
		}
		return found;
	}
}
