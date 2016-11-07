/**
 * Immutable class describing an edge between two vertices
 */
public class Edge {
    private final Vertex v1;
    private final Vertex v2;
    private final int distance;

    /**
     * Constructs Edge object from two vertices and the distance between them
     *
     * @param v1 vertex 1
     * @param v2 vertex 2
     * @param distance distance between v1 and v2, effectively the weight of the edge
     */
    public Edge(Vertex v1, Vertex v2, int distance) {
        this.v1 = v1;
        this.v2 = v2;
        this.distance = distance;
    }
    
    /**
     * Get distance between v1 and v2
     *
     * @return distance between v1 and v2
     */
    public int getDistance() {
        return this.distance;
    }

    /**
     * Get the other vertex on this edge
     *
     * @param v Known vertex
     * @return other vertex on this edge
     * @throws IllegalArgumentException if v is not on this edge
     */
    public Vertex getOppositeVertex(Vertex v) {
        if(v == this.v1)
            return this.v2;
        else if(v == this.v2)
            return this.v1;
        else
            throw new IllegalArgumentException("Vertex is not on this Edge");
    }
}
