import elements.People;
import elements.Vehicle;

import graph.Graph;
import graph.Point;
import graph.Route;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import utils.Reader;
import utils.Utils;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

    @Test
    public void testVehicle(){
        Vehicle vehicle = new Vehicle("vehicle 1",4,50, "A");
        assertEquals(vehicle.getName(),"vehicle 1");
        assertEquals(vehicle.getCapacity(),4);
        assertEquals(vehicle.getVelocity(),50);
        assertEquals(vehicle.getLocation(),"A");

    }

    @Test
    public void testGetPeopleFromFile() throws ParserConfigurationException, SAXException, IOException {
        Reader reader = new Reader("data/Test1.xml");

        ArrayList<People> result = reader.getPeopleFromFile();
        assertEquals(10, result.get(0).getNumber());
        assertEquals("A", result.get(0).getLocation());

    }

    @Test
    public void testGetVehiclesFromFile() throws IOException, SAXException, ParserConfigurationException {

        Reader reader = new Reader("data/Test1.xml");

        ArrayList<Vehicle> vehicles = reader.getVehiclesFromFile();

        assertEquals(2,vehicles.size());

        Vehicle v1 = vehicles.get(0);

        assertEquals("vehicle 1", v1.getName());
        assertEquals(4, v1.getCapacity());
        assertEquals(50, v1.getVelocity());
        assertEquals("B", v1.getLocation());

        Vehicle v2 = vehicles.get(1);

        assertEquals("vehicle 2", v2.getName());
        assertEquals(2, v2.getCapacity());
        assertEquals(50, v2.getVelocity());
        assertEquals("D", v2.getLocation());

    }

    @Test
    public void testGetPointsFromFile() throws IOException, SAXException, ParserConfigurationException {

        Reader reader = new Reader("data/Test1.xml");

        ArrayList<Point> points = reader.getPointsFromFile();

        assertEquals(7, points.size());
        assertEquals("A", points.get(0).getName());
        assertEquals("C", points.get(2).getName());
        assertEquals("G", points.get(6).getName());

        String name = "D";
        Point point = Utils.getPointByName(name,points);
        assertEquals(name, point.getName());

    }

    @Test
    public void testGetRoutesFromFile() throws IOException, SAXException, ParserConfigurationException {

        Reader reader = new Reader("data/Test1.xml");
        ArrayList<Point> points = reader.getPointsFromFile();
        ArrayList<Route> routes = reader.getRoutesFromFile(points);

        assertEquals(3,routes.size());

        Point A = Utils.getPointByName("A", points);

        assertEquals(2,A.getRoutes().size());

        Point B = Utils.getPointByName("B", points);

        assertEquals(B, A.getRoutes().get(0).getDestiny());
        assertEquals(1.5, A.getRoutes().get(0).getDistance());
        assertEquals(new ArrayList<Route>(),B.getRoutes());

    }

    @Test
    public void testLoadGraph(){
        Graph graph = new Graph("data/Test1.xml");

        assertEquals(7,graph.getPoints().size());

        ArrayList<Point> points = graph.getPoints();
        Point A = Utils.getPointByName("A",points);

        assertEquals(2, A.getRoutes().size());
        
    }

}
