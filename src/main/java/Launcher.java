import graph.Graph;
import graph.State;


public class Launcher {

    private static String filename = "data/Evacuation_graph.xml";

    public static void main(String[] args) {

        Graph graph = new Graph(filename);

        State state = new State(graph, filename);
        state.displayState();

        Search.dfs(state);

    }
}

   