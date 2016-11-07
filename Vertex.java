import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class describing a vertex/node in a graph
 */
public class Vertex {
    private final char name;
    private final List<Edge> incident;

    // used for Dijkstra's algorithm
    private int distanceFromStart;
    private Vertex previous;

    /**
     * Constructs Vertex with the specified name
     *
     * @param name name for the Vertex
     */
    public Vertex(char name) {
        this.name = name;
        this.incident = new ArrayList<>();
    }
    
    /**
     * Get the name of this vertex
     *
     * @return name of vertex
     */
    public char getName() {
        return name;
    }
    
    /**
     * Adds an edge to the vertex
     * @param newEdge the edge is added to this vertex's list of incident edges
     * @throws IllegalArgumentException if edge from this to the other is already in the list
     */
    public void addEdge(Edge newEdge) {
        for(Edge e : incident) {
            if(e.getOppositeVertex(this) == newEdge.getOppositeVertex(this)) {
                throw new IllegalArgumentException("Edge already exists");
            }
        }
        incident.add(newEdge);
    }

    /**
     * Used for Dijkstra's algorithm
     * @return distance from the first vertex
     */
    public int getDistanceFromStart() {
        return distanceFromStart;
    }
    
    /**
     * Used for Dijkstra's algorithm
     * @param distance currently best-known distance from first vertex
     */
    public void setDistanceFromStart(int distance) {
        this.distanceFromStart = distance;
    }

    /**
     * Used for Dijkstra's algorithm
     * @return previous vertex in best route to first vertex
     */
    public Vertex getPrevious() {
        return previous;
    }
    
    /**
     * Used for Dijkstra's algorithm
     * @param previous vertex in best route to first vertex
     */
    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    /**
     * Returns a list of edge incident to this vertex.
     * It uses Collections.unmodifiableList to get a view of the list, not the original list.
     * @return an unmodifiable view  of the incident edge list
     */
    public List<Edge> getIncidentEdges() {
        return Collections.unmodifiableList(incident);
    }
    
    /**
     * Gets the adjacency status of this Vertex and another
     * @param other Vertex to check adjacency to
     * @return whether or not this is adjacent to other
     */
    public boolean isAdjacent(Vertex other) {
        boolean adjacent = false;
        for(Edge e : incident) {
            if(e.getOppositeVertex(this) == other) {    // reference comparison is intensional
                adjacent = true;
                break;
            }
        }
        return adjacent;
    }
    
    /**
     * Returns the edge connecting this vertex and the vertex named by adjacent or null if they are not adjacent
     */
    public Edge getJoiningEdgeByName(char adjacent) {
        for(Edge e : incident) {
            if(e.getOppositeVertex(this).name == adjacent) {
                return e;
            }
        }
        return null;
    }
}
