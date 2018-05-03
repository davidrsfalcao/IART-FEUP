import elements.Vehicle;
import graph.Graph;
import graph.Point;
import graph.Route;

import java.util.ArrayList;

public class Search {

    public static void dfs(Graph graph) {

        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(graph.getVehicles().get(0));
        State st = new State(graph, graph.getPeople(), vehicles);
        iterate(st);    //inicialmente, apenas utiliza 1 veiculo
    }

    public static void iterate(State st) {

        Vehicle v = st.getVehicles().get(0);
        Point p = v.getLocation();

        if(st.alreadyVisited(p.getName()))return;

        System.out.println(st);

        st.addToPath(p.getName());

        ArrayList<Route> routes = p.getRoutes();

        for (Route r : routes) {
            Point dest = r.getDestiny();

            v.setLocation(dest);
            iterate(st);

        }

    }
}
