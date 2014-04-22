import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Vertex implements Comparable<Vertex>
{
    public final Point point;
    public ArrayList<Edge> adjacencies = new ArrayList<Edge>();
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(Point argName) { point = argName; }
    public String toString() { return "(" + point.x + "," + point.y + ")"; }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}

class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}

public class Dijkstra
{
    public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);
		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args)
    {	
        Vertex v0 = new Vertex(new Point (100,100));
        Vertex v1 = new Vertex(new Point(50,50));
        Vertex v2 = new Vertex(new Point(20,20));

        v0.adjacencies.add(new Edge(v1,Point.getDistance(v0.point,v1.point)));
		v1.adjacencies.add(new Edge(v0,Point.getDistance(v1.point,v0.point)));
	
	Vertex[] vertices = { v0, v1};
        computePaths(v0);
        for (Vertex v : vertices)
	{
	    System.out.println("Distance to " + v + ": " + v.minDistance);
	    List<Vertex> path = getShortestPathTo(v);
	    System.out.println("Path: " + path);
	}
    }
}