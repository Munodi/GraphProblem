i Description
There are three classes used to model a graph: Graph, Vertex and Edge.
The Graph class maintains a list of references to Vertices, each Vertex has a
name, a list of references to Edges it is incident to and a couple other
members for Dijkstra's algorithm, each Edge has a reference to each Vertex it
is connected to and the distance between them.
As Edge can return the opposite vertex to a known one and Vertex has a list of
all its incident edges, one can traverse the graph by first finding the starting
vertex in the Graph's list then looping the incidence list and calling
getOppositeVertex.
 

Graph 
   |              \                \
Vertex <- Edge -> Vertex <- Edge -> Vertex
   |______^  ^_______|______^  ^______|
          

ii Assumptions
In the POSSIBLE operation, any vertex may be visited as many times as possible
as long as the route's length is less than that specified.

As recursion is used for POSSIBLE I'm assuming the graph and maximum distance
won't be too large and cause a stack overflow.

The input file is well-formed.

iii Compiling and running
To compile run:
javac GraphProblem.java
javac -cp ".:junit-4.12.jar" GraphTest.java

To run the program run:
java GraphProblem filename
The output.txt file will be in this directory.

To run tests run:
java -cp ".:junit-4.12.jar:hamcrest-core-1.3.jar" org.junit.runner.JUnitCore GraphTest

On Windows replace the colons with semicolons
