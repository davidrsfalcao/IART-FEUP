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
        Vehicle vehicle = new Vehicle("vehicle 1",4,50, new Point("A",2,2));
        assertEquals(vehicle.getName(),"vehicle 1");
        assertEquals(vehicle.getCapacity(),4);
        assertEquals(vehicle.getVelocity(),50);
        assertEquals(vehicle.getLocation(),new Point("A",2,2));

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
        assertEquals(700, point.getX());
        assertEquals(700, point.getY());

    }

    @Test
    public void testGetRoutesFromFile() throws IOException, SAXException, ParserConfigurationException {

        Reader reader = new Reader("data/Test1.xml");
        ArrayList<Point> points = reader.getPointsFromFile();
        ArrayList<Route> routes = reader.getRoutesFromFile(points);

        assertEquals(6,routes.size());

        Point A = Utils.getPointByName("A", points);

        assertEquals(2,A.getRoutes().size());

        Point B = Utils.getPointByName("B", points);

        assertEquals(B, A.getRoutes().get(0).getDestiny());
        assertEquals(200, A.getRoutes().get(0).getDistance());
        assertEquals(A,B.getRoutes().get(0).getDestiny());

    }

    @Test
    public void testGetVehiclesFromFile() throws IOException, SAXException, ParserConfigurationException {

        Reader reader = new Reader("data/Test1.xml");
        ArrayList<Point> points = reader.getPointsFromFile();
        ArrayList<Vehicle> vehicles = reader.getVehiclesFromFile(points);

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

        Point A = Utils.getPointByName("A",points);
        Point D = Utils.getPointByName("D",points);

        assertEquals(false, A.has_vehicle());
        assertEquals(true, D.has_vehicle());
        assertEquals(50, D.getVehicle().getVelocity());

    }

    @Test
    public void testGetPeopleFromFile() throws ParserConfigurationException, SAXException, IOException {
        Reader reader = new Reader("data/Test1.xml");
        ArrayList<Point> points = reader.getPointsFromFile();
        ArrayList<People> result = reader.getPeopleFromFile(points);

        assertEquals(10, result.get(0).getNumber());
        assertEquals("A", result.get(0).getLocation());

        Point A = Utils.getPointByName("A",points);
        assertEquals(10, A.getPeople().getNumber());


    }

    @Test
    public void getSafePointFromFile() throws IOException, SAXException, ParserConfigurationException {
        Reader reader = new Reader("data/Test1.xml");
        ArrayList<Point> points = reader.getPointsFromFile();
        Point safe_point = reader.getSafePointFromFile(points);

        assertEquals("E", safe_point.getName());

    }

    @Test
    public void testLoadGraph(){
        Graph graph = new Graph("data/Test1.xml");

        assertEquals(7,graph.getPoints().size());

        ArrayList<Point> points = graph.getPoints();
        Point A = Utils.getPointByName("A",points);

        assertEquals(2, A.getRoutes().size());
        assertEquals(10, A.getPeople().getNumber());
        assertEquals("E", graph.getSafe_point().getName());

    }

    @Test
    public void testRescuePeoplePoint() {
        Graph graph = new Graph("data/Test1.xml");
        ArrayList<Point> points = graph.getPoints();
        Point A = Utils.getPointByName("A",points);
        int empty_seats;

        empty_seats = A.rescuePeople(4);

        assertEquals(6, A.getPeople().getNumber());
        assertEquals(0, empty_seats);

        empty_seats = A.rescuePeople(7);

        assertEquals(0, A.getPeople().getNumber());
        assertEquals(1, empty_seats);

    }
}
