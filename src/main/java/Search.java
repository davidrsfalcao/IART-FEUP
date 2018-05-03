import elements.People;
import elements.Vehicle;
import graph.Point;
import graph.Route;
import graph.State;
import utils.Utils;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Search {

    public static ArrayList<String> solution;

    public static ArrayList<String> dfs(State st) {

        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(st.getVehicles().get(0));
        State newState = new State(st.getGraph(), st.getPeople(), vehicles);
        iterate(newState);    //inicialmente, apenas utiliza 1 veiculo
        return solution;
    }

    public static boolean iterate(State st) {

        Vehicle v = st.getVehicles().get(0);
        String currentPoint = v.getLocation();

        ArrayList<People> people = st.getPeople();
        for (People pp : people) {

            // people in a different location than the rescue, and the vehicle can transport people
            if (pp.getLocation().equals(currentPoint) &&
                    !(pp.getLocation().equals(st.getGraph().getSafe_point().getName()))
                    && pp.getNumber() > 0 && v.getCapacity() != 0) {

                //rescue the people !!

                int nPeople = pp.getNumber();
                int maxCap = v.getCapacity();

                if (v.canTransport(nPeople)) {
                    v.setCurrentPersons(nPeople);
                    pp.setNumber(0);
                } else { //transporta o maximo
                    v.setCurrentPersons(maxCap);
                    pp.setNumber(nPeople - maxCap);
                }
                st.clearPath();
            }
        }

        if (st.getGraph().getSafe_point().getName().equals(currentPoint) && v.isTransporting()) {
            System.out.println("In final state!");
            System.out.println(st.toString());

            st.getGroupsPeople().add(new People(v.emptyVehicle(), currentPoint));

            if (st.allRescued()) {
                st.getPath().add(currentPoint);
                st.clearPath();
                solution= new ArrayList<String>(st.getReturnPath());
                return true;

            } else {
                System.out.println("going back...");
                st.clearPath();
            }
        }

        /*st.displayState();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        ArrayList<String> path = st.getPath();

        if (path.contains(currentPoint)) {
            System.out.println("The vehicle is in: " + currentPoint);
            System.out.println("Current path: " + st.getPath());
            return false;
        }

        path.add(currentPoint);

        Point search = Utils.getPointByName(currentPoint, st.getGraph().getPoints());
        ArrayList<Route> routes = search.getRoutes();

        for (Route r : routes) {
            Point dest = r.getDestiny();
            v.setLocation(dest.getName());
            if (iterate(new State(st))) return true;
        }

        return false;

    }
}
