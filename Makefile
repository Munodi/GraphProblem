
all: GraphProblem.class Graph.class Path.class

GraphProblem.class: GraphProblem.java
	javac GraphProblem.java

Graph.class: Graph.java
	javac Graph.java

Path.class: Path.java
	javac Path.java

.PHONY: clean
clean:
	rm -f *.class output.txt
