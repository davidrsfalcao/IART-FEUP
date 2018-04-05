package tests;

import elements.People;
import elements.Vehicle;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import utils.Reader;

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
        Reader reader = new Reader(getClass().getResource("test1.xml").getPath());

        ArrayList<People> result = reader.getPeopleFromFile();
        assertEquals(10, result.get(0).getNumber());
        assertEquals("A", result.get(0).getLocation());

    }

    @Test
    public void testGetVehiclesFromFile() throws IOException, SAXException, ParserConfigurationException {
        Reader reader = new Reader(getClass().getResource("test1.xml").getPath());

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

}
