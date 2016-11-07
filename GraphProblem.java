import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.List;
import java.util.Map;

public class GraphProblem {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: GraphProblem input.txt");
            return;
        }

        Graph graph = new Graph();
        
        try {
            String inputPath = args[0];
            // Java doesn't automatically expand ~ to the home directory
            inputPath = inputPath.charAt(0) == '~' ? System.getProperty("user.home") + inputPath.substring(1) : inputPath;
            Scanner in = new Scanner(new File(inputPath));
            PrintWriter out = new PrintWriter("output.txt");

            // read edges into graph
            {
                String[] edges = in.nextLine().split(" ");
                for(String s: edges) {
                    graph.addEdge(s.charAt(0), s.charAt(1), Integer.parseInt(s.substring(2)));
                }
            }

            while(in.hasNextLine()) {
                String line = in.nextLine();
                String[] commandParamPair = line.split(" ");
                String command = commandParamPair[0];
                String param = commandParamPair[1];
                
                switch (command) {
                    case "DISTANCE":
                        out.println(line + " = " + param +
                        graph.calculateDistance(param));
                        break;
                    case "SHORTEST":
                        String route =
                            graph.findShortestRoute(param.charAt(0), param.charAt(1));
                        out.println(
                            line + " = " + route);
                        break;
                    case "POSSIBLE":
                        Map<String, Integer> routes =
                        graph.findPossibilities(param.charAt(0),
                        param.charAt(1), Integer.parseInt(param.substring(2)));
                        out.print(line + " =");
                        for(Map.Entry<String, Integer> r : routes.entrySet())
                        {
                            out.print(" ");
                            out.print(r);
                        }
                        out.println();
                }
            }
            out.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find file");
        }
    }

    
}

