package graph;

import elements.People;
import elements.Vehicle;

import java.util.ArrayList;

public class Point {

    private static int count=0;

    private int id;
    private String name;
    private int x,y;
    private ArrayList<Route> routes = new ArrayList<Route>();
    private boolean has_vehicle = false;
    private boolean has_people = false;
    private Vehicle vehicle;
    private People people;
    private boolean safe_point = false;

    public Point(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;

        this.id=++count;
    }

    public String getName() {
        return name;
    }

    public int getId() { return id; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public boolean has_vehicle() {
        return has_vehicle;
    }

    public void addRoute(Route route){
        this.routes.add(route);
    }

    public void addVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        has_vehicle = true;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void addPeople(People people){
        this.people = people;
        has_people = true;
    }

    public People getPeople() {
        return people;
    }

    public boolean has_people(){
        return has_people;
    }

    public int rescuePeople(int number){
        int actual = people.getNumber();
        int empty_seats = 0;

        if(number >= actual){
            people.setNumber(0);
            has_people = false;
            empty_seats = number - actual;
        }
        else people.setNumber(actual-number);

        return empty_seats;
    }
}