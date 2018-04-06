package graph;


import org.xml.sax.SAXException;
import utils.Reader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Graph {

    private ArrayList<Point> points;

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
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}