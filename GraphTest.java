import java.util.Map;
import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {
    private Graph instance;
    
    public GraphTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Graph();
    }
    
    @After
    public void tearDown() {
    }
    
    // addEdge tests
    
    @Test
    public void addEdge_shouldSucceed() {        
        instance.addEdge('A', 'B', 2);
        instance.addEdge('B', 'C', 7);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void addEdge_testDistanceIsTooShort_shouldThrowException() {
        instance.addEdge('A', 'B', 0);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void addEdge_testVertexIDsMatch_shouldThrowException() {
        instance.addEdge('A', 'A', 4);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void addEdge_testAddingASecondEdgeBetweenVertices_shouldThrowException() {
        instance.addEdge('A', 'B', 2);
        instance.addEdge('A', 'B', 7);
    }
    
    // calculateDistance tests
    
    @Test
    public void calculateDistance_testValidArguments() {
        instance.addEdge('A', 'D', 7);
        instance.addEdge('E', 'D', 4);
        instance.addEdge('C', 'D', 5);
        instance.addEdge('E', 'A', 1);
        String route = "AEDC";
        int expResult = 1 + 4 + 5;
        int result = instance.calculateDistance(route);
        assertEquals(expResult, result);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void calculateDistance_testFirstVertexNonExistent_shouldThrowException() {
        //instance.addEdge('A', 'D', 7);
        instance.addEdge('E', 'D', 4);
        instance.addEdge('C', 'D', 5);
        //instance.addEdge('E', 'A', 1);
        String route = "AEDC";
        instance.calculateDistance(route);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void calculateDistance_testRouteIsNotInGraph_shouldThrowException() {
        instance.addEdge('A', 'D', 7);
        instance.addEdge('E', 'D', 4);
        instance.addEdge('C', 'D', 5);
        //instance.addEdge('E', 'A', 1);
        String route = "AEDC";
        instance.calculateDistance(route);
    }

    // findShortestRoute tests
    
    @Test(expected=IllegalArgumentException.class)
    public void findShortestRoute_shouldFailBecauseStartIsNotInGraph() {
        char start = 'A';
        char finish = 'E';
        //instance.addEdge('A', 'B', 1);
        //instance.addEdge('A', 'C', 4);
        //instance.addEdge('A', 'D', 5);
        instance.addEdge('C', 'E', 10);
        instance.addEdge('D', 'E', 3);
        
        String expResult = "ADE8";
        String result = instance.findShortestRoute(start, finish);
        assertEquals(expResult, result);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void findShortestRoute_testNoPathToFinish_shouldThrowException() {
        char start = 'A';
        char finish = 'E';
        instance.addEdge('A', 'B', 1);
        instance.addEdge('A', 'C', 4);
        instance.addEdge('A', 'D', 5);
        //instance.addEdge('C', 'E', 10);
        //instance.addEdge('D', 'E', 3);
        
        String result = instance.findShortestRoute(start, finish);
    }
    
    @Test
    public void findShortestRoute_testValidArguments() {
        char start = 'A';
        char finish = 'E';
        instance.addEdge('A', 'B', 1);
        instance.addEdge('A', 'C', 4);
        instance.addEdge('A', 'D', 5);
        instance.addEdge('C', 'E', 10);
        instance.addEdge('D', 'E', 3);
        
        String expResult = "ADE8";
        String result = instance.findShortestRoute(start, finish);
        assertEquals(expResult, result);
    }

    // findPossibilities tests
    
    @Test
    public void findPossibilities_testValidArguments() {
        char start = 'A';
        char finish = 'C';
        instance.addEdge('A', 'B', 3);
        instance.addEdge('A', 'C', 4);
        instance.addEdge('A', 'D', 5);
        instance.addEdge('C', 'E', 10);
        instance.addEdge('D', 'E', 3);
        int maxDistance = 14;
        Map<String, Integer> expResult = new TreeMap<>();
        expResult.put("ABAC", 10);
        expResult.put("AC", 4);
        expResult.put("ACAC", 12);
        expResult.put("ADAC", 14);
        
        Map<String, Integer> result = instance.findPossibilities(start, finish, maxDistance);
        assertEquals(expResult, result);
    }

    @Test
    public void findPossibilities_testStartEqualsFinish_shouldSucceed() {
        char start = 'A';
        char finish = start;
        instance.addEdge('A', 'B', 3);
        instance.addEdge('A', 'C', 4);
        instance.addEdge('A', 'D', 5);
        instance.addEdge('C', 'E', 10);
        instance.addEdge('D', 'E', 3);
        int maxDistance = 10;
        Map<String, Integer> expResult = new TreeMap<>();
        expResult.put("A", 0);
        expResult.put("ABA", 6);
        expResult.put("ACA", 8);
        expResult.put("ADA", 10);

        Map<String, Integer> result = instance.findPossibilities(start, finish, maxDistance);
        assertEquals(expResult, result);
    }
    
    @Test
    public void findPossibilities_WithDistanceShorterThanAnyPossibleRoute_shouldReturnEmpty() {
        char start = 'A';
        char finish = 'E';
        instance.addEdge('A', 'B', 3);
        instance.addEdge('A', 'C', 4);
        instance.addEdge('A', 'D', 5);
        instance.addEdge('C', 'E', 10);
        instance.addEdge('D', 'E', 3);
        int maxDistance = 5;
        Map<String, Integer> expResult = new TreeMap<>();
        
        Map<String, Integer> result = instance.findPossibilities(start, finish, maxDistance);
        assertEquals("Shouldn't find any routes", expResult, result);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void findPossibilities_testStartVertexNonExistent() {
        instance.addEdge('B', 'C', 7);
        char start = 'A';
        char finish = 'C';
        int maxDistance = 50;
        instance.findPossibilities(start, finish, maxDistance);
    }

    @Test(expected=IllegalArgumentException.class)
    public void findPossibilities_testFinishVertexNonExistent() {
        instance.addEdge('B', 'C', 7);
        char start = 'C';
        char finish = 'A';
        int maxDistance = 50;
        instance.findPossibilities(start, finish, maxDistance);
    }
    
}
