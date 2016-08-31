public class Path
{
    private final char vert1, vert2;
    private final int distance;
    
    public Path(char vert1, char vert2, int distance) {
        this.vert1 = vert1;
        this.vert2 = vert2;
        this.distance = distance;
    }
    
    public int getDistance() {
        return distance;
    }
    
    public char getVertex1() {
        return vert1;
    }
    
    public char getVertex2() {
        return vert2;
    }
}
