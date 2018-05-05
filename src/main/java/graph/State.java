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

    /*
    * Copy constructor for follow states
     */
    public State(State st) {

        graph = st.getGraph();

        groups_people = new ArrayList<People>(st.getPeople().size());
        for(People p: st.getPeople()) groups_people.add( new People(p) );

        vehicles = new ArrayList<Vehicle>(st.getVehicles().size());
        for(Vehicle v: st.getVehicles()) vehicles.add( new Vehicle(v) );

    }

    /*
    * Get the graph
    */
    public Graph getGraph() { return graph;  }

    /*
     * Get the vehicles
     */
    public ArrayList<Vehicle> getVehicles() { return vehicles; }

    /*
     * Get the people
     */
    public ArrayList<People> getPeople(){ return groups_people; }


    /*
     * Print state
     */
    public String toString(){
        String res="";
        for(Vehicle v:vehicles){
            System.out.println(v.toString());
        }
        for(People p:groups_people){
            System.out.println(p.toString());
        }
        return res;
    }

    public String printStats(){
        String sb="";
        sb+= " Final solution: ";
        for(Vehicle v:vehicles){
            if(v.getTotalRescued()!=0){
                System.out.println(v.toString() + "rescued: "+v.getTotalRescued()+" persons");
            }
        }

        return sb;
    }

    /*
     * Print state graph
     */
    public void displayState(){  graph.print(groups_people, vehicles);  }


    /*
     * Check if final state
     */
    public boolean allRescued(){
        for(People p : groups_people){
            if(! (p.getLocation().equals(graph.getSafe_point().getName()))){
                if( p.getNumber()!=0 ){
                    return false;
                }
            }
        }
        for(Vehicle v:vehicles){
            if(v.isTransporting()) return false;
        }
        return true;
    }
}
