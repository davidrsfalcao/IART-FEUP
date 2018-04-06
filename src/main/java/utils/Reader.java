package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import elements.People;
import elements.Vehicle;
import graph.Point;
import graph.Route;
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

    public ArrayList<Point> getPointsFromFile(){

        ArrayList<Point> points = new ArrayList<Point>();

        try {
            Node pointsNode = doc.getElementsByTagName("points").item(0);
            NodeList pointsNodesList = pointsNode.getChildNodes();
            for(int i=0; i<pointsNodesList.getLength();i++){

                if(pointsNodesList.item(i).getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) pointsNodesList.item(i);

                    String name = eElement.getAttribute("name");
                    points.add(new Point(name));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return points;
    }

    public ArrayList<Route> getRoutesFromFile(ArrayList<Point> points){

        ArrayList<Route> routes = new ArrayList<Route>();

        try {
            Node routesNode = doc.getElementsByTagName("routes").item(0);
            NodeList routeNodesList = routesNode.getChildNodes();

            for(int i=0; i<routeNodesList.getLength();i++){

                if(routeNodesList.item(i).getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) routeNodesList.item(i);

                    String originS = eElement.getElementsByTagName("from").item(0).getTextContent();
                    String destinyS = eElement.getElementsByTagName("to").item(0).getTextContent();
                    double distance = Double.parseDouble(eElement.getElementsByTagName("distance").item(0).getTextContent());

                    Point origin = Utils.getPointByName(originS,points);
                    Point destiny = Utils.getPointByName(destinyS,points);

                    Route route = new Route(destiny, distance);

                    origin.addRoute(route);
                    routes.add(route);

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return routes;
    }
}