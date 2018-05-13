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

        solutions = new ArrayList<State>();
        iterate(st);

        if(solutions.size()==0){
            System.out.println("Solution not found!");
        }
        else{

            Collections.sort(solutions);
            System.out.println(solutions.get(0).printStats());
            System.out.println("end");
        }

    }

    /*
     * Check if there is a loop in the path
     * */
    public static boolean checkValidState(Vehicle v) {

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

    /*
     * Check if the vehicle is in the safe place, and if is an end state
     */
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
                v.stop();               //stop the vehicle
                st.stopCountingVehicle(v);
            }
        }
        return false;
    }


    /*
     * Check if the state is a solution/valid/invalid
     */
    public static boolean checkState(Vehicle v, State st) {

        if (v.isActive()) {

            String currentPoint = v.getLocation();

            if (!checkValidState(v)) {
                return true;            //invalid state
            }

            checkRescuePeople(v, st);

            if (checkSafePlace(v, st)) {
                solutions.add(st);
                return true;            //found solution

            } else {
                v.getPath().add(currentPoint);
            }
        }

        return false;
    }

    /*
     * Computes the next states
     */
    public static void next_states(State st, ArrayList<Vehicle> vehicles) {

        int vh_index = st.getNextVehicle();
        Vehicle vh = vehicles.get(vh_index);

        if (vh.isActive()) {

            String currentPoint = vh.getLocation();
            Point search = Utils.getPointByName(currentPoint, st.getGraph().getPoints());
            ArrayList<Route> routes = search.getRoutes();


            Boolean[] moved_vehicles = st.getMovedVehicles();
            moved_vehicles[vh_index] = true;
            st.setLastMoved(vh_index);


            for (Route r : routes) {

                vh.setLocation(r.getDestiny().getName());
                vh.addDistance(r.getDistance());

                if (vh_index == st.getIndexCount()) {
                    st.addTime(r.getDistance());
                }

                iterate(new State(st));

                if (vh_index == st.getIndexCount()) {
                    st.removeTime(r.getDistance());
                }

                vh.removeDistance(r.getDistance());

            }
        }
    }


    public static void iterate(State st) {


        ArrayList<Vehicle> vehicles = st.getVehicles();

        int toMove = st.getLastMoved();

        if (toMove != -1) {

            Vehicle v = vehicles.get(toMove);

            if (checkState(v, st)) {        // no more follow states
                return;
            }
        }
        else{

            //evaluate the first state

            for(Vehicle vh: vehicles){
                checkState(vh, st);
            }

        }

        next_states(st, vehicles);          // create follow states

    }

}
