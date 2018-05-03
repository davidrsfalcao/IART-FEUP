import elements.People;
import elements.Vehicle;
import graph.Graph;

import java.util.ArrayList;


public class State {

    private Graph graph;
    private ArrayList<People> people;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<String> path;

    public State(Graph graph, ArrayList<People> people, ArrayList<Vehicle> vehicles) {

        this.graph = graph;
        this.people = people;
        this.vehicles = vehicles;
        this.path=new ArrayList<String>();

    }

    public Graph getGraph() { return graph;  }

    public void addToPath(String node){ this.path.add(node); }

    public boolean alreadyVisited(String node){ return this.path.contains(node); }

    public ArrayList<Vehicle> getVehicles() { return vehicles; }

    public String toString(){
        return ""+path.toString();
    }


}
