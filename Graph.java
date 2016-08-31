import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Graph
{
   private final List<Path> paths = new ArrayList<>();
    
    public void addRoute(char vertex1, char vertex2, int distance)
    {
        // check vertex1 != vertex2
        if(vertex1 == vertex2)
        {
            throw new IllegalArgumentException("Vertices cannot be equal");
        }

        // make vertex1 the first vertex alphabetically
        if(vertex1 > vertex2)
        {
            char temp = vertex1;
            vertex1 = vertex2;
            vertex2 = temp;
        }

        // check route is not present
        for(Path p : paths)
        {
            if(p.getVertex1() == vertex1 && p.getVertex2() == vertex2)
                throw new IllegalStateException("Route already exists");
        }

        // add route to list
        paths.add(new Path(vertex1, vertex2, distance));
    }
    
    private int distanceBetweenVertices(char v1, char v2)
    {
        if(v1 > v2)
        {
            char temp = v1;
            v1 = v2;
            v2 = temp;
        }
        for(int i = 0; i < paths.size(); ++i)
        {
            if(paths.get(i).getVertex1() == v1 && paths.get(i).getVertex2() == v2)
                return paths.get(i).getDistance();
        }

        return -1;
    }

    private Set<Path> getEdgesOnVertex(char v)
    {
        Set<Path> edgesOnVertex = new HashSet<>();
        for(Path p : paths)
        {
            if(p.getVertex1() == v || p.getVertex2() == v)
                edgesOnVertex.add(p);
        }
        return edgesOnVertex;
    }
    
    public int calculateDistance(String route)
    {
        if(route.length() < 2)
            throw new IllegalArgumentException("route is too short");
        
        int totalDistance = 0;
        char currentVertex = route.charAt(0);

        for(int i = 1; i < route.length(); ++i)
        {
            char nextVertex = route.charAt(i);
            int edgeDistance = this.distanceBetweenVertices(currentVertex, nextVertex);

            if(edgeDistance == -1)  // no edge between vertices
            {
                throw new IllegalArgumentException(
                    String.format("No edge between %s and %s",
                        currentVertex, nextVertex));
            }
            else
            {
                totalDistance += edgeDistance;
                currentVertex = nextVertex;
            }
        }

        return totalDistance;
    }

    public List<Map.Entry<String, Integer>> findPossibilities(String route)
    {
        List<Map.Entry<String, Integer>> listOfRoutes = new ArrayList<>();
        
        return listOfRoutes;
    }
}
