import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GraphProblem
{
    public static void main(String[] args)
    {
        if (args.length != 1) {
            System.out.println("usage: GraphProblem input.txt");
            return;
        }

        Graph graph = new Graph();
        
        try {
            Scanner in = new Scanner(new File(args[0]));
            PrintWriter out = new PrintWriter("output.txt");

            // read routes into graph
            String[] routes = in.nextLine().split(" ");
            for(String s: routes) {
                graph.addRoute(s.charAt(0), s.charAt(1), Integer.parseInt(s.substring(2)));
            }

            while(in.hasNextLine())
            {
                String line = in.nextLine();
                String[] commandParamPair = line.split(" ");
                
                switch (commandParamPair[0])
                {
                    case "DISTANCE":
                        out.println(line + " = " + commandParamPair[1] +
                        graph.calculateDistance(commandParamPair[1]));
                        break;
                    case "SHORTEST":
//                        Pair<String, Integer> route =
//                            graph.findShortestRoute(commandParamPair[1]);
//                        out.println(
//                            line + " = " + route.getKey() + route.getValue());
                        break;
                    case "POSSIBLE":
                }
            }
            out.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find file");
        }
    }

    
}

