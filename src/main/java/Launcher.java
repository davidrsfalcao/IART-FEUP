import graph.Graph;
import graph.State;

import static java.lang.Thread.sleep;


public class Launcher {

    private static String filename = "data/Evacuation_graph.xml";

    public static void main(String[] args) {

        Graph graph = new Graph(filename);

        State state = new State(graph, filename);

        state.displayState();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Search.a_star(state);

    }
}

   