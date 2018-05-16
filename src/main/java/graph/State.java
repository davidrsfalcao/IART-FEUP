package graph;

import elements.People;
import elements.Vehicle;
import utils.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class State implements Comparable<State> {


    private Graph graph;
    private ArrayList<People> groups_people;
    private ArrayList<Vehicle> vehicles;

    private int lastMoved;                  // the index of the last moved vehicle
    private int indexCount;                 // the index of the vehicle that is counting the distance
    private int nextChange;

    private Boolean[] movedVehicles;        // moved vehicles in a state

    private int totalTime;                  // total time


    public State(Graph graph, String filename) {

        Reader reader = null;
        try {
            reader = new Reader(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Point> points = reader.getPointsFromFile();
        vehicles = reader.getVehiclesFromFile(points);
        groups_people = reader.getPeopleFromFile(points);

        this.graph = graph;

        movedVehicles = new Boolean[vehicles.size()];    // initially, no vehicle has been moved
        Arrays.fill(movedVehicles, Boolean.FALSE);

        lastMoved = -1;
        indexCount = 0;
        nextChange=-1;
        totalTime = 0;

    }

    /*
     * Copy constructor for follow states
     */
    public State(State st) {

        graph = st.getGraph();

        groups_people = new ArrayList<People>(st.getPeople().size());

        for (People p : st.getPeople()) {
            groups_people.add(new People(p));
        }

        vehicles = new ArrayList<Vehicle>(st.getVehicles().size());

        for (Vehicle v : st.getVehicles()) {
            vehicles.add(new Vehicle(v));
        }

        movedVehicles = st.movedVehicles.clone();

        lastMoved = st.lastMoved;
        indexCount = st.indexCount;
        totalTime = st.totalTime;
        nextChange=st.nextChange;
    }

    /*
     * Get the graph
     */
    public Graph getGraph() {
        return graph;
    }

    /*
     * Get the vehicles
     */
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    /*
     * Get the people
     */
    public ArrayList<People> getPeople() {
        return groups_people;
    }

    /*
     * Get the array representing the moved vehicles
     */
    public Boolean[] getMovedVehicles() {
        return movedVehicles;
    }

    /*
     * Get the index of the last moved vehicle
     */
    public int getLastMoved() {
        return lastMoved;
    }

    /*
     * Set the last moved vehicle
     */
    public void setLastMoved(int lastMoved) {
        this.lastMoved = lastMoved;
    }


    /*
     * Get the index of the vehicle that is counting the distance
     */
    public int getIndexCount() {
        return indexCount;
    }

    /*
     * Chose other vehicle to keep the count of the distance
     */
    public void stopCountingVehicle(Vehicle v) {

        int index = vehicles.indexOf(v);

        if (index == indexCount) {        //choose other vehicle to count

            for (int i = 0; i < vehicles.size(); i++) {

                Vehicle next = vehicles.get(i);

                if (next.isActive()) {
                    nextChange = i;
                    return;
                }

            }
        }

    }


    /*
     * Print intermediate state
     */
    public String toString() {
        String res = "";
        for (Vehicle v : vehicles) {
            System.out.println(v.toString());
        }
        for (People p : groups_people) {
            System.out.println(p.toString());
        }

        res += totalTime;

        res += "\n";

        for (int i = 0; i < movedVehicles.length; i++) {
            res += movedVehicles[i] + " ";
        }

        return res;
    }


    /*
     * Print final state
     */
    public String printStats() {
        String sb = "";

        for (Vehicle v : vehicles) {

            if (v.getTotalRescued() != 0) {
                System.out.println(v.toString() + "rescued: " + v.getTotalRescued() + " persons");
            }
        }
        System.out.println("Total: " + totalTime);

        return sb;
    }

    /*
     * Print state graph
     */
    public void displayState() {
        graph.print(groups_people, vehicles);
    }


    /*
     * Check if final state
     */
    public boolean inSolution() {

        for (People p : groups_people) {
            if (!(p.getLocation().equals(graph.getSafe_point().getName()))) {
                if (p.getNumber() != 0) {
                    return false;
                }
            }
        }
        for (Vehicle v : vehicles) {
            if (v.isTransporting()) return false;
        }

        return true;
    }

    /*
     * Check if all rescued
     */
    public boolean allRescued() {
        for (People p : groups_people) {
            if (!(p.getLocation().equals(graph.getSafe_point().getName()))) {
                if (p.getNumber() != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * Get the next vehicle to move
     */
    public int getNextVehicle() {

        for (int i = 0; i < vehicles.size(); i++) {

            boolean bol = movedVehicles[i];

            if (!bol) {
                return i;
            }
        }

        //all moved - reset list
        Arrays.fill(movedVehicles, Boolean.FALSE);

        if(nextChange!=-1){
            indexCount = nextChange;
            nextChange=-1;
        }


        return 0;
    }

    /*
     * Increase the state total time
     */
    public void addTime(int time) {
        totalTime += time;
    }

    public void removeTime(int time) {
        totalTime -= time;
    }


    @Override
    public int compareTo(State o) {
        return totalTime - o.totalTime;
    }
}
