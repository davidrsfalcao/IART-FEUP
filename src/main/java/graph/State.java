package graph;

import elements.People;
import elements.Vehicle;
import org.xml.sax.SAXException;
import utils.Reader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;


public class State {

    private Graph graph;
    private ArrayList<People> groups_people;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<String> path;

    public State(Graph graph, String filename) {

        Reader reader = null;
        try {
            reader = new Reader(filename);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        ArrayList<Point> points = reader.getPointsFromFile();

        vehicles = reader.getVehiclesFromFile(points);
        groups_people = reader.getPeopleFromFile(points);

        this.graph = graph;
        this.path=new ArrayList<String>();

    }

    public State(Graph g, ArrayList<People> pp, ArrayList<Vehicle> vv) {

        this.graph = g;
        this.groups_people = pp;
        this.vehicles=vv;
        this.path=new ArrayList<String>();
    }

    public State(State st) {

        this.graph = st.getGraph();
        this.groups_people = new ArrayList<People>(st.getPeople());
        this.vehicles = new ArrayList<Vehicle>(st.getVehicles());
        this.path=new ArrayList<String>(st.getPath());
    }

    public Graph getGraph() { return graph;  }

    public void addToPath(String node){ this.path.add(node); }

    public boolean alreadyVisited(String node){ return this.path.contains(node); }

    public ArrayList<Vehicle> getVehicles() { return vehicles; }

    public ArrayList<String> getPath() { return path; }

    public ArrayList<People> getPeople() { return groups_people; }

    public String toString(){  return ""+path.toString();   }


}
