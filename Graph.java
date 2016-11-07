import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.PriorityQueue;

public class Graph {
    private final List<Vertex> listOfVertices = new ArrayList<>();

    /**
     * Adds an edge to the graph.
     * Any vertices will be automatically created if needed. addEdge validates
     * the parameters are valid, like distance must be at least 1, it cannot be
     * an edge from a vertex back to itself, and must not already exist
     * (including with a different distance.
     * @param vertex1Name Name of first vertex
     * @param vertex2Name Name of second vertex
     * @param distance distance between vertices (weight of the edge)
     * @throws IllegalArgumentException if parameters do not satisfy above
     * requirements
     */
    public void addEdge(char vertex1Name, char vertex2Name, int distance) {
        // check distance >= 1
        if(distance < 1) {
            throw new IllegalArgumentException("Distance must be at least one unit");
        }
        
        // check vertex1 != vertex2
        if(vertex1Name == vertex2Name) {
            throw new IllegalArgumentException("Vertices cannot be equal");
        }
        
        // add vertex named by vertex1Name if not already present
        Vertex vertex1 = null;
        for(Vertex v : listOfVertices) {
            if(v.getName() == vertex1Name) {
                vertex1 = v;
                break;
            }
        }
        if(vertex1 == null) {
            vertex1 = new Vertex(vertex1Name);
            listOfVertices.add(vertex1);
        }
        
        // add vertex named by vertex2Name if not already present
        Vertex vertex2 = null;
        for(Vertex v : listOfVertices) {
            if(v.getName() == vertex2Name) {
                vertex2 = v;
                break;
            }
        }
        if(vertex2 == null) {
            vertex2 = new Vertex(vertex2Name);
            listOfVertices.add(vertex2);
        }
        
        // check edge is not already present
        if(vertex1.isAdjacent(vertex2)) {
            throw new IllegalArgumentException("Edge between " + vertex1Name + " and " + vertex2Name + " already exists");
        }

        // add edge
        Edge e = new Edge(vertex1, vertex2, distance);
        vertex1.addEdge(e);
        vertex2.addEdge(e);
    }
    
    /**
     * Calculates the distance of the specified route.
     * @return distance of the specified route
     * @param route to measure
     * @throws IllegalArgumentException if route is invalid or doesn't exist
     */
    public int calculateDistance(String route) {
        if(route.length() < 2)
            throw new IllegalArgumentException("route is too short");
        
        // find first vertex
        Vertex currentVertex = null;
        for(Vertex v : listOfVertices) {
            if(v.getName() == route.charAt(0)) {
                currentVertex = v;
                break;
            }
        }
        if(currentVertex == null) {
            throw new IllegalArgumentException("Vertex " + route.charAt(0) + " not found");
        }
        
        int totalDistance = 0;
        for(int i = 1; i < route.length(); ++i) {
            char nextVertexName = route.charAt(i);
            Edge travelledEdge = currentVertex.getJoiningEdgeByName(nextVertexName);
            
            // check edge between vertices exists, and if not throw exception
            if(travelledEdge == null) {
                throw new IllegalArgumentException("Vertex " + route.charAt(i) + " not found");
            }
            
            // travel edge to next vertex
            totalDistance += travelledEdge.getDistance();
            currentVertex = travelledEdge.getOppositeVertex(currentVertex);
        }
        
        return totalDistance;
    }
    
    /**
     * Finds the shortest route between vertices.
     * It basically uses Dijkstra's algorithm from wikipedia.
     * @return shortest route concatenate with distance of the route
     * @param start name of start vertex
     * @param finish name of fiosh vertex
     * @throws IllegalArgumentException if can't find vertices
     */
    public String findShortestRoute(char start, char finish) {
        PriorityQueue<Vertex> Q = new PriorityQueue<>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex v1, Vertex v2) {
                return Integer.compare(v1.getDistanceFromStart(), v2.getDistanceFromStart());
            }
        });

        Vertex startVert = null;
        for(Vertex v : listOfVertices) {
            if(v.getName() == start) {
                v.setDistanceFromStart(0);
                v.setPrevious(v);
                startVert = v;
            }
            else {
                v.setDistanceFromStart(Integer.MAX_VALUE);
                v.setPrevious(null);
            }
            Q.add(v);
        }
        if(startVert == null) {
            throw new IllegalArgumentException("start vertex is not in graph");
        }

        Vertex finishVert = null;
        while(!Q.isEmpty()) {
            Vertex v = Q.remove();  //  remove first vertex in queue, the shortest path has been found to this vertex
            if(v.getName() == finish) {
                finishVert = v;
                break;  // finish quicker
            }
            for(Edge incidentEdge : v.getIncidentEdges()) {
                Vertex adjacentVertex = incidentEdge.getOppositeVertex(v);
                int alt = v.getDistanceFromStart() + incidentEdge.getDistance();
                if(alt < adjacentVertex.getDistanceFromStart()) {   // if going through this edge shortens the route to adjacentVertex
                    adjacentVertex.setDistanceFromStart(alt);
                    adjacentVertex.setPrevious(v);
                    Q.remove(adjacentVertex);   // increase the priority of adjacent vertices if this vertex shortens their route
                    Q.add(adjacentVertex);
                }
            }
        }
        
        // throw exception if finish vertex not found
        if(finishVert == null) {
            throw new IllegalArgumentException("finish vertex is not in graph");
        }
        
        // build the return value by adding vertex names from finish to start,
        // reversing the string, then concatenating the distance
        StringBuilder sb = new StringBuilder();
        for(Vertex v = finishVert; v != startVert; v = v.getPrevious()) {
            sb.append(v.getName());
        }
        return sb.append(start).reverse().append(finishVert.getDistanceFromStart()).toString();
    }
    
    /**
     * Finds all routes less than maxDistance
     * @param startName name of start vertex
     * @param finishName name of finish vertex
     * @param maxDistance maximum route distance inclusive
     * @return the routes with vertices and total distances in a Map
     * @throws IllegalArgumentException on invalid input
     */
    public Map<String, Integer> findPossibilities(char startName, char finishName, int maxDistance) {
        Map<String, Integer> routes = new TreeMap<>();
        
        // get vertex identified by startName
        Vertex startVertex = null;
        for(Vertex v : listOfVertices) {
            if(v.getName() == startName) {
                startVertex = v;
                break;
            }
        }
        if(startVertex == null) {
            throw new IllegalArgumentException("Vertex " + startName + " is not in graph");
        }
        
        // get vertex identified by finishName
        Vertex finishVertex = null;
        for(Vertex v : listOfVertices) {
            if(v.getName() == finishName) {
                finishVertex = v;
                break;
            }
        }
        if(finishVertex == null) {
            throw new IllegalArgumentException("Vertex " + finishName + " is not in graph");
        }
        
        // search which will do DFS and alter routes whenever it finds a route
        search(routes, startVertex, finishVertex, maxDistance, Character.toString(startName), 0);
        
        /*for(Edge e : startVertex.getIncidentEdges()) {
            if(e.getDistance() <= maxDistance)
                search(routes, e.getOppositeVertex(startVertex), finishVertex, maxDistance, Character.toString(startName) + e.getOppositeVertex(startVertex).getName(), e.getDistance());
        }*/
        
        return routes;
    }
    // recursively search and add to routes upon finding
    private void search(final Map<String, Integer> routes, Vertex current, Vertex finishVertex, int maxDistance, String currentRoute, int currentDistance) {
        if(current == finishVertex) {
            routes.put(currentRoute, currentDistance);
        }
        for(Edge e : current.getIncidentEdges()) {
            if(e.getDistance() + currentDistance <= maxDistance)
                search(routes, e.getOppositeVertex(current), finishVertex, maxDistance, currentRoute + e.getOppositeVertex(current).getName(), e.getDistance() + currentDistance);
        }
    }
}
