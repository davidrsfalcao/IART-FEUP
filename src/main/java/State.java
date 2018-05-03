import elements.People;
import elements.Vehicle;
import graph.Graph;
import graph.Point;
import graph.Route;
import org.xml.sax.SAXException;
import utils.Reader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class State {

    private ArrayList<People> groups_people;
    private ArrayList<Vehicle> vehicles;
    Graph graph;

    public State(Graph graph, String filename) {

        Reader reader = null;
        try {
            reader = new Reader(filename);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        ArrayList<Point> points = reader.getPointsFromFile();
        vehicles = reader.getVehiclesFromFile(points);
        groups_people = reader.getPeopleFromFile(points);

        this.graph = graph;
    }



    // A function used by DFS
    void DFSUtil(Point p, boolean visited[]) {
        // Mark the current node as visited and print it
        int pos = p.getId();
        int pos2;

        visited[pos] = true;
        System.out.print(p.getName() + " ");

        // Recur for all the vertices adjacent to this vertex

        ArrayList<Route> routes = p.getRoutes();

        for (Route r : routes) {
            Point dest = r.getDestiny();

            pos2 = dest.getId();
            if (!visited[pos2])
                DFSUtil(dest, visited);
        }

    }


    // The function to do DFS traversal. It uses recursive DFSUtil()
    void DFS( ) {
        int size = graph.getPoints().size();

        // Mark all the vertices as not visited(set as
        // false by default in java)
        boolean visited[] = new boolean[size];

        // Call the recursive helper function to print DFS traversal
        Point start = graph.getPoints().get(0);
        DFSUtil(start, visited);
    }
}
