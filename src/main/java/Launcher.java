import graph.Graph;

public class Launcher {

    private static String filename = "data/Test1.xml";
    public static void main(String[] args){

        Graph graph = new Graph(filename);
        graph.display();
        
     }
}

   