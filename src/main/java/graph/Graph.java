package graph;


import elements.People;
import elements.Vehicle;
import org.xml.sax.SAXException;
import utils.Reader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Graph 

    private ArrayList<Point> points;
    private ArrayList<People> groups_people;
    private ArrayList<Vehicle> vehicles;


    public Graph(String filename){
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

        points = reader.getPointsFromFile();
        reader.getRoutesFromFile(points);
        vehicles = reader.getVehiclesFromFile(points);
        groups_people = reader.getPeopleFromFile(points);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}