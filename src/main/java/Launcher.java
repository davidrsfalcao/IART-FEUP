import graph.Graph;
import graph.State;

import java.util.ArrayList;

public class Launcher {

    private static String filename = "data/Evacuation_graph.xml";

    public static void main(String[] args) {

        Graph graph = new Graph(filename);

        State state = new State(graph, filename);
        state.displayState();

        ArrayList<String> sol = Search.dfs(state);

        System.out.println("Solution: "+sol.toString());

    }
}

   