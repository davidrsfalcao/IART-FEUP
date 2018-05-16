
import elements.People;
import elements.Vehicle;
import graph.Point;
import graph.Route;
import graph.State;
import utils.Utils;

import java.util.*;


public class Search {

    public enum ALGORITHM {
        DFS, ASTAR
    }


    public static ArrayList<State> solutions;

    public static Map<Double, List<State>> states;

    /*
     * dfs search
     * */
    public static void dfs(State st) {

        solutions = new ArrayList<State>();
        iterate(st, ALGORITHM.DFS);

        if (solutions.size() == 0) {
            System.out.println("Solution not found!");
        } else {

            /*Collections.sort(solutions);
            System.out.println(solutions.get(0).printStats());
            System.out.println("end");*/

            for (State s : solutions) {
                System.out.println(s.printStats());
            }
        }
        System.out.println("end");

    }

    /*
     * dfs search
     * */
    public static void a_star(State st) {

        solutions = new ArrayList<State>();
        states = new LinkedHashMap<Double, List<State>>();

        iterate(st, ALGORITHM.ASTAR);

        if (solutions.size() == 0) {
            System.out.println("Solution not found!");
        } else {

            /*Collections.sort(solutions);
            System.out.println(solutions.get(0).printStats());
            System.out.println("end");*/

            for (State s : solutions) {
                System.out.println(s.printStats());
            }
        }
        System.out.println("end");

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
                v.setReturn();
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
                v.setGo();

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

        return false;
    }

    /*
     * Computes the next states
     */

    /*
            //ordena rotas de acordo com heuristica

            */


    //https://stackoverflow.com/questions/16869920/a-heuristic-calculation-with-euclidean-distance
    public static double calculate_distance(Point destination, Point currentNode) {

        double x = Math.pow(destination.getX() - currentNode.getX(), 2.0);
        double y = Math.pow(destination.getX() - currentNode.getX(), 2.0);

        return x + y;
    }


    public static void put(Map<Double, List<State>> map, Double key, State value) {
        if (map.get(key) == null) {
            List<State> list = new ArrayList<State>();
            list.add(value);
            map.put(key, list);
        } else {
            map.get(key).add(value);
        }
    }

    public static void addNewStates(Vehicle vh, int vh_index, State st, ArrayList<Route> routes) {

        //Heuristic - closer to destiny

        String loc = st.getPeople().get(0).getLocation();
        Point dest = Utils.getPointByName(loc, st.getGraph().getPoints());

        // add all the possible next states
        for (Route r : routes) {

            double res = calculate_distance(r.getDestiny(), dest);

            //add state with the corresponding cost
            vh.setLocation(r.getDestiny().getName());
            vh.addDistance(r.getDistance());

            if (vh_index == st.getIndexCount()) {
                st.addTime(r.getDistance());
            }

            put(states, res, new State(st));


            if (vh_index == st.getIndexCount()) {
                st.removeTime(r.getDistance());
            }

            vh.removeDistance(r.getDistance());

        }
    }


    public static int compute_astar(State st, Vehicle vh, int vh_index, ArrayList<Vehicle> vehicles) {

        //if (vh.isActive()) {

        String currentPoint = vh.getLocation();
        Point search = Utils.getPointByName(currentPoint, st.getGraph().getPoints());
        ArrayList<Route> routes = search.getRoutes();


        addNewStates(vh, vh_index, st, routes);
        return 0;

        //  } else {

        // next_states(st, vehicles, ALGORITHM.ASTAR);  //the vehicle is in the same location
        //  return 1;
        // }
    }


    public static void compute_dfs(State st, Vehicle vh, int vh_index, ArrayList<Vehicle> vehicles) {

        if (vh.isActive()) {

            String currentPoint = vh.getLocation();
            Point search = Utils.getPointByName(currentPoint, st.getGraph().getPoints());
            ArrayList<Route> routes = search.getRoutes();

            for (Route r : routes) {

                vh.setLocation(r.getDestiny().getName());
                vh.addDistance(r.getDistance());

                if (vh_index == st.getIndexCount()) {
                    st.addTime(r.getDistance());
                }

                iterate(new State(st), ALGORITHM.DFS);

                if (vh_index == st.getIndexCount()) {
                    st.removeTime(r.getDistance());
                }

                vh.removeDistance(r.getDistance());

            }

        } else {

            next_states(st, vehicles, ALGORITHM.DFS);  //the vehicle is in the same location

        }
    }

    public static boolean cenas = true;

    public static void next_states(State st, ArrayList<Vehicle> vehicles, ALGORITHM alg) {

        int vh_index = st.getNextVehicle();
        Vehicle vh = vehicles.get(vh_index);

        // check this vehicle as moved
        Boolean[] moved_vehicles = st.getMovedVehicles();
        moved_vehicles[vh_index] = true;
        st.setLastMoved(vh_index);


        if (alg == ALGORITHM.DFS) {

            compute_dfs(st, vh, vh_index, vehicles);

        } else if (alg == ALGORITHM.ASTAR) {

            if (vh.isActive()) {
                compute_astar(st, vh, vh_index, vehicles);
            } else {


                put(states, 345.0, new State(st));      //??????


            }

        }

    }


    public static void iterate(State st, ALGORITHM alg) {


        ArrayList<Vehicle> vehicles = st.getVehicles();

        int toMove = st.getLastMoved();

        if (toMove != -1) {

            choseNextValidState();

        } else {

            //evaluate the first state

            for (Vehicle vh : vehicles) {

                checkState(vh, st); //check this state

            }

            next_states(st, vehicles, ALGORITHM.ASTAR);

            iterate(st, ALGORITHM.ASTAR);

        }


    }


    public static void choseNextValidState() {

        label:
        while (true) {

            State st = getAState();

            if (st == null) {
                return;
            }

            ArrayList<Vehicle> vehicles = st.getVehicles();
            int toMove = st.getLastMoved();
            Vehicle v = vehicles.get(toMove);

            if (v.isActive() && checkState(v, st)) {              // evaluate state

                break label;  //until it finds a state

            } else {

                next_states(st, vehicles, ALGORITHM.ASTAR);     // create follow states

                choseNextValidState();

            }
        }
    }


    public static State getAState() {

        for (Map.Entry<Double, List<State>> entry : states.entrySet()) {
            Double key = entry.getKey();
            List<State> value = entry.getValue();

            State st = value.remove(0);
            if (value.size() == 0) {
                states.remove(key, value);
            }

            return st;

        }
        return null;
    }


}
