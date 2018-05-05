import elements.People;
import elements.Vehicle;
import graph.Point;
import graph.Route;
import graph.State;
import utils.Utils;
import java.util.ArrayList;


import static java.lang.Thread.sleep;

public class Search {

    public static boolean debug =false;


    public static void dfs(State st) {

        iterate(st);

    }

    public static boolean checkValidState(Vehicle v, State st){

        String currentPoint = v.getLocation();
        if (v.getPath().contains(currentPoint)) {
            return false;
        }
        return true;
    }

    public static void checkRescuePeople(Vehicle v, State st){

        String currentPoint = v.getLocation();

        ArrayList<People> people = st.getPeople();
        for (People pp : people) {

            // people in a different location than the rescue, and the vehicle can transport people
            if (pp.getLocation().equals(currentPoint) &&
                    !(pp.getLocation().equals(st.getGraph().getSafe_point().getName()))
                    && pp.getNumber() > 0 && v.getCapacity() != 0) {

                int nPeople = pp.getNumber();
                int maxCap = v.getCapacity();

                if (v.canTransport(nPeople)) {
                    v.setCurrentPersons(nPeople);
                    pp.setNumber(0);
                } else {
                    v.setCurrentPersons(maxCap);
                    pp.setNumber(nPeople - maxCap);
                }
                v.clearPath();

            }
        }
    }

    public static boolean checkSafePlace(Vehicle v, State st){

        String currentPoint = v.getLocation();

        if (st.getGraph().getSafe_point().getName().equals(currentPoint) && v.isTransporting()) {

            st.getPeople().add(new People(v.emptyVehicle(), currentPoint));

            if (st.allRescued()) {

                v.getPath().add(currentPoint);
                v.clearPath();

                return true;

            } else {

                v.clearPath();

            }
        }
        return false;
    }

    public static void iterate(State st) {


        ArrayList<Vehicle> vehicles = st.getVehicles();

        boolean sol=false;

        for(Vehicle v : vehicles){

            String currentPoint = v.getLocation();

            if( ! checkValidState(v, st) ) return;

            checkRescuePeople(v, st);

            if( checkSafePlace(v, st)) sol=true;

            else v.getPath().add(currentPoint);

        }

        if(sol){
            System.out.println(st.printStats());
            return;
        }


        /* -----------------------------*/
        if(debug) {
            System.out.println(st.toString());
            st.displayState();


            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /* -----------------------------*/


        /* see next iterations */
        for(Vehicle v :vehicles){

            String currentPoint = v.getLocation();
            Point search = Utils.getPointByName(currentPoint, st.getGraph().getPoints());
            ArrayList<Route> routes = search.getRoutes();

            for (Route r : routes) {
                v.getGoRoutes().add(r);
            }
        }

        decide(st, vehicles);

    }

    public static void decide(State st, ArrayList<Vehicle> vehicles ){

        Vehicle v = vehicles.get(0);

        for(int i=0; i< v.getGoRoutes().size(); i++){

            Route decision = v.getGoRoutes().get( i );
            v.setLocation(decision.getDestiny().getName());
            v.addDistance(decision.getDistance());

            if( vehicles.size()==1 ){

                iterate(new State(st));

            }
            else{
                decide(st, new ArrayList<Vehicle>( vehicles.subList(1, vehicles.size())));
            }

            v.removeDistance(decision.getDistance());
        }

    }
}
