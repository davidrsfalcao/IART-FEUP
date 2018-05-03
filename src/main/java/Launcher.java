import graph.Graph;

public class Launcher {

    private static String filename = "data/Evacuation_graph.xml";
    public static void main(String[] args){

        Graph graph = new Graph(filename);
        graph.display();
        
     }
}

   