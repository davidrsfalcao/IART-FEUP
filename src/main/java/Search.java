import elements.Vehicle;
import graph.Graph;
import graph.Point;
import graph.Route;
import graph.State;
import utils.Utils;

import java.util.ArrayList;

public class Search {

    public static void dfs(State st) {

        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(st.getVehicles().get(0));
        State newState = new State(st.getGraph(), st.getPeople(), vehicles);
        iterate(newState);    //inicialmente, apenas utiliza 1 veiculo
    }

    public static void iterate(State st) {

        Vehicle v = st.getVehicles().get(0);
        Point p = v.getLocation();

        if(st.alreadyVisited(p.getName())){
            System.out.println("The vehicle is "+p.getName());
            System.out.println("Current path: "+st.getPath());
            return;
        }

        st.addToPath(p.getName());

        Point search = Utils.getPointByName(p.getName(), st.getGraph().getPoints());
        ArrayList<Route> routes = search.getRoutes();

        for (Route r : routes) {
            Point dest = r.getDestiny();
            v.setLocation(dest);
            iterate(new State(st));
        }

    }
}
