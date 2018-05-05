import elements.People;
import elements.Vehicle;
import graph.Point;
import graph.Route;
import graph.State;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;


import static java.lang.Thread.sleep;

public class Search {

    public static ArrayList<State> solutions;

    public static boolean debug = false;

    /*
     * dfs search
     * */
    public static void dfs(State st) {

        solutions=new ArrayList<State>();
        iterate(st);
        for(State s: solutions)
            System.out.println(s.printStats());
        //Collections.sort(solutions);
        System.out.println("end");
    }

    /*
     * Check if there is a loop in the path
     * */
    public static boolean checkValidState(Vehicle v, State st) {

        String currentPoint = v.getLocation();
        if (v.getPath().contains(currentPoint)) {
            return false;
        }
        return true;
    }

    /*
     * Check if there are people in the vehicle location
     * */
    public static void checkRescuePeople(Vehicle v, State st) {

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

    public static boolean checkSafePlace(Vehicle v, State st) {

        String currentPoint = v.getLocation();

        if (st.getGraph().getSafe_point().getName().equals(currentPoint) && v.isTransporting()) {

            st.getPeople().add(new People(v.emptyVehicle(), currentPoint));

            if (st.inSolution()) {

                v.getPath().add(currentPoint);
                v.clearPath();

                return true;

            } else {

                v.clearPath();

            }

            if (st.allRescued()) {
                v.stop();
            }
        }
        return false;
    }

    public static void iterate(State st) {

        ArrayList<Vehicle> vehicles = st.getVehicles();

        boolean sol = false;

        for (Vehicle v : vehicles) {

            if (v.isActive()) {

                String currentPoint = v.getLocation();

                if (!checkValidState(v, st)) return;

                checkRescuePeople(v, st);

                if (checkSafePlace(v, st)) sol = true;

                else v.getPath().add(currentPoint);
            }

        }

        if (sol) {
           // System.out.println(st.printStats());
            solutions.add(st);
            return;
        }


        /* -----------------------------*/
        if (debug) {
            System.out.println(st.toString());
            st.displayState();


            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /* -----------------------------*/


        /* see next iterations */
        for (Vehicle v : vehicles) {

            if (v.isActive()) {
                String currentPoint = v.getLocation();
                Point search = Utils.getPointByName(currentPoint, st.getGraph().getPoints());
                ArrayList<Route> routes = search.getRoutes();

                for (Route r : routes) {
                    v.getGoRoutes().add(r);
                }
            }

        }

        decide(st, vehicles);

    }

    public static void decide(State st, ArrayList<Vehicle> vehicles) {

        Vehicle v = vehicles.get(0);

        if (!v.isActive()) {
            if (vehicles.size() == 1) {
                iterate(new State(st));

            } else {
                decide(st, new ArrayList<Vehicle>(vehicles.subList(1, vehicles.size())));
            }
        } else {
            for (int i = 0; i < v.getGoRoutes().size(); i++) {

                Route decision = v.getGoRoutes().get(i);
                v.setLocation(decision.getDestiny().getName());
                v.addDistance(decision.getDistance());

                if (vehicles.size() == 1) {

                    iterate(new State(st));

                } else {
                    decide(st, new ArrayList<Vehicle>(vehicles.subList(1, vehicles.size())));
                }

                v.removeDistance(decision.getDistance());
            }

        }
    }
}
