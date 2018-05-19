
import elements.People;
import elements.Vehicle;
import graph.Point;
import graph.Route;
import graph.State;
import utils.Utils;


import java.util.*;


public class Search {

    public enum ALGORITHM {
        DFS, ASTAR, BFS, BB
    }

    /* The algorithm to use for the search */
    public static ALGORITHM algorithm;
    public static boolean one_solution;

    /* Found Solutions */
    public static ArrayList<State> solutions;

    /* Found states */
    public static ArrayList<State> states;
    public static Stack<State> states_stack;


    /*
     * Search and show solution
     */
    public static void search(State st) {

        if (algorithm == ALGORITHM.DFS) {
            states_stack = new Stack<State>();
        } else {

            states = new ArrayList<State>();
        }

        solutions = new ArrayList<State>();

        iterate(st);
    }

    public static void show_solutions() {

        if (solutions.size() == 0) {
            System.out.println("Solution not found!");
        } else {

            filterSolutions();

            if (solutions.size() == 1) {
                System.out.println(solutions.get(0).printStats());

            } else {
                System.out.println("Found ~ " + solutions.size() + " solutions.");
                System.out.println("Display all? (y/n)");

                Scanner sc = new Scanner(System.in);
                String str = sc.nextLine();

                if (str.equals("y")) {
                    clearSolutions();

                    for (State s : solutions) {
                        System.out.println(s.printStats());
                    }
                }
            }
        }

        System.out.println("End.");
    }

    /*
     * Eliminates duplicates
     */
    public static void clearSolutions() {

        Set<State> hs = new HashSet<State>(solutions);
        solutions.clear();
        solutions.addAll(hs);

    }

    /*
     * Organize the path of the vehicles
     */
    public static void filterSolutions() {

        for (State s : solutions) {

            for (Vehicle v : s.getVehicles()) {
                v.organizePath();
            }
        }

    }


    /*
     * Check if there is a loop in the path
     */
    public static boolean checkValidState(Vehicle v) {

        String currentPoint = v.getLocation();

        if (v.getPath().contains(currentPoint)) {
            return false;
        }
        return true;
    }


    /*
     * Check if there are people in the vehicle location
     */
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
            solutions.add(new State(st));
            return true;            //found solution

        } else {
            v.getPath().add(currentPoint);

        }

        return false;
    }


    //https://stackoverflow.com/questions/16869920/a-heuristic-calculation-with-euclidean-distance
    public static double calculate_distance(Point destination, Point currentNode) {

        double x = Math.pow(destination.getX() - currentNode.getX(), 2.0);
        double y = Math.pow(destination.getX() - currentNode.getX(), 2.0);

        return x + y;
    }

    /*
     * Computes the heuristic for a state
     */
    public static double get_heuristic(State st) {

        double sum = 0;

        Point safe_p = st.getGraph().getSafe_point();

        for (People pp : st.getPeople()) {

            if (pp.getNumber() != 0) {
                String currentPoint = pp.getLocation();
                Point search = Utils.getPointByName(currentPoint, st.getGraph().getPoints());


                double dist = calculate_distance(safe_p, search);

                sum += dist * 100; //edge cost

            }

        }

        for (Vehicle v : st.getVehicles()) {
            if (v.isTransporting()) {

                String p = v.getLocation();
                Point search = Utils.getPointByName(p, st.getGraph().getPoints());

                double dist = calculate_distance(safe_p, search);

                sum += dist * 100;

            }

        }

        return st.getTotalTime() + sum;

    }


    /*
     * Computes the next states using a*
     */
    public static void compute_astar(State st, Vehicle vh, int vh_index) {

        String currentPoint = vh.getLocation();
        Point search = Utils.getPointByName(currentPoint, st.getGraph().getPoints());
        ArrayList<Route> routes = search.getRoutes();

        ArrayList<State> aux = new ArrayList<State>();

        for (Route r : routes) {                        // add all the possible next states

            vh.setLocation(r.getDestiny().getName());
            vh.addDistance(r.getDistance());


            if (vh_index == st.getIndexCount()) {
                st.addTime(r.getDistance());
            }

            st.setCost(get_heuristic(new State(st)));   // cost of the state

            if (algorithm == ALGORITHM.DFS) {
                aux.add(new State(st));

            } else {
                states.add(new State(st));                  // create a new state

            }


            if (vh_index == st.getIndexCount()) {
                st.removeTime(r.getDistance());
            }

            vh.removeDistance(r.getDistance());

        }

        if (algorithm == ALGORITHM.DFS) {
            Collections.reverse(aux);
            for (State sta : aux) {
                states_stack.push(sta);
            }
        }
    }

    /*
     * Computes the next states
     */
    public static void next_states(State st, ArrayList<Vehicle> vehicles) {

        int vh_index = st.getNextVehicle();
        Vehicle vh = vehicles.get(vh_index);

        // check this vehicle as moved
        Boolean[] moved_vehicles = st.getMovedVehicles();
        moved_vehicles[vh_index] = true;
        st.setLastMoved(vh_index);


        if (vh.isActive()) {
            compute_astar(st, vh, vh_index);

        } else {

            if (algorithm == ALGORITHM.DFS) {
                states_stack.push(new State(st));
            } else {
                states.add(new State(st));                  // create a new state

            }

        }

    }

    /*
     * Search for the solution
     */
    public static void iterate(State st) {


        ArrayList<Vehicle> vehicles = st.getVehicles();

        int toMove = st.getLastMoved();

        if (toMove != -1) {

            choseNextValidState();

        } else {

            //evaluate the first state

            for (Vehicle vh : vehicles) {

                checkState(vh, st);

            }

            next_states(st, vehicles);

            iterate(st);
        }
    }

    /*
     * Search for the solution
     */
    public static void choseNextValidState() {


        while (true) {

            State st = getAState();

            if (st == null) {
                return;
            }

            ArrayList<Vehicle> vehicles = st.getVehicles();
            int toMove = st.getLastMoved();
            Vehicle v = vehicles.get(toMove);


            if (v.isActive() && checkState(v, st)) {              // evaluate state

                continue;

            } else {

                next_states(st, vehicles);                     // create follow states

            }
        }
    }

    /*
     * Returns a state according to the algorithm
     */
    public static State getAState() {

        if (one_solution) {
            if (solutions.size() == 1) return null;
        }
        //check end
        if (algorithm == ALGORITHM.DFS) {
            if (states_stack.empty()) return null;
        } else {
            if (states.size() == 0) return null;
        }

        if (algorithm == ALGORITHM.ASTAR) {

            Collections.sort(states, new Comparator<State>() {
                @Override
                public int compare(State o1, State o2) {
                    if (o1.getCost() < o2.getCost()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        } else if (algorithm == ALGORITHM.BB) {

            Collections.sort(states, new Comparator<State>() {
                @Override
                public int compare(State o1, State o2) {
                    if (o1.getTotalTime() < o2.getTotalTime()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        } else if (algorithm == ALGORITHM.DFS) {

            return new State(states_stack.pop());
        }

        return new State(states.remove(0));
    }

}
