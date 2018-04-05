package utils;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import elements.People;
import elements.Vehicle;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Reader {

    private Document doc;


    public Reader(String filename) throws ParserConfigurationException, IOException, SAXException {
        File inputFile = new File(filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
    }


    public ArrayList<People> getPeopleFromFile() {

        ArrayList<People> group = new ArrayList<People>();

        try {
            Node groupNode = doc.getElementsByTagName("groups").item(0);
            NodeList peopleList = groupNode.getChildNodes();

            for (int i=0; i<peopleList.getLength();i++)
            if(peopleList.item(i).getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) peopleList.item(i);
                int number = Integer.parseInt(eElement.getElementsByTagName("number").item(0).getTextContent());
                String location = eElement.getElementsByTagName("location").item(0).getTextContent();
                group.add(new People(number,location));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return group;
    }

    public ArrayList<Vehicle> getVehiclesFromFile() {

        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

        try {
            Node vehiclesNode = doc.getElementsByTagName("vehicles").item(0);
            NodeList vehicleNodesList = vehiclesNode.getChildNodes();

            for(int i=0; i<vehicleNodesList.getLength();i++){

                if(vehicleNodesList.item(i).getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) vehicleNodesList.item(i);

                    String name = eElement.getAttribute("name");
                    int capacity = Integer.parseInt(eElement.getElementsByTagName("capacity").item(0).getTextContent());
                    int velocity = Integer.parseInt(eElement.getElementsByTagName("velocity").item(0).getTextContent());
                    String location = eElement.getElementsByTagName("location").item(0).getTextContent();

                    vehicles.add(new Vehicle(name,capacity,velocity,location));

                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return vehicles;
    }
}