import elements.People;
import elements.Vehicle;
import graph.Graph;
import graph.Point;
import graph.Route;
import graph.State;
import utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Search {

    public static void dfs(State st) {

        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(st.getVehicles().get(0));
        State newState = new State(st.getGraph(), st.getPeople(), vehicles);
        iterate(newState);    //inicialmente, apenas utiliza 1 veiculo
    }

    public static boolean iterate(State st) {

        Vehicle v = st.getVehicles().get(0);
        String currentPoint = v.getLocation();

        ArrayList<People> people = st.getPeople();
        for(People pp : people){
            if(pp.getLocation().equals(currentPoint) && pp.getNumber()>0){

                //rescue the people !!

                int nPeople = pp.getNumber();
                int maxCap = v.getCapacity();

                if(maxCap >= nPeople){
                    pp.setNumber(0);
                    v.setCapacity(maxCap-nPeople);
                }
                else{
                    pp.setNumber(nPeople-maxCap);
                    v.setCapacity(0);
                }

                st.clearPath();
            }
        }

        if(st.getGraph().getSafe_point().getName().equals(currentPoint)){
            System.out.println("In final state!");
            System.out.println(st.toString());
            return true;
        }


        st.displayState();
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> path = st.getPath();

        if(path.contains(currentPoint)){
            System.out.println("The vehicle is in: "+ currentPoint);
            System.out.println("Current path: "+st.getPath());
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
