import graph.Graph;

public class Launcher {

    private static String filename = "data/Test2.xml";
    public static void main(String[] args){

        Graph graph = new Graph(filename);
        graph.display();
        
     }
}

   