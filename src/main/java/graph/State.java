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
    private ArrayList<String> goPath;
    private ArrayList<String> returnPath;

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
    }

    public State(Graph g, ArrayList<People> pp, ArrayList<Vehicle> vv) {

        this.graph = g;
        this.groups_people = pp;
        this.vehicles=vv;
        this.goPath=new ArrayList<String>();
        this.returnPath= new ArrayList<String>();
    }

    public State(State st) {

        this.graph = st.getGraph();
        this.groups_people = new ArrayList<People>(st.getPeople());
        this.vehicles = new ArrayList<Vehicle>(st.getVehicles());
        this.goPath=new ArrayList<String>(st.getPath());
        this.returnPath=new ArrayList<String>(st.getReturnPath());
    }

    public Graph getGraph() { return graph;  }

    public ArrayList<Vehicle> getVehicles() { return vehicles; }

    public ArrayList<String> getPath() { return goPath; }

    public ArrayList<String> getReturnPath() { return returnPath; }

    public void clearPath() {
        returnPath=new ArrayList<String>(this.goPath);
        this.goPath=new ArrayList<String>();
    }

    public ArrayList<People> getPeople() { return groups_people; }

    public String toString(){  return "go: "+goPath.toString()+"\nreturn: "+returnPath.toString();   }

    public void displayState(){  graph.print(groups_people, vehicles);   }
}
