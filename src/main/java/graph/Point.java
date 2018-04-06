package graph;

import elements.Vehicle;

import java.util.ArrayList;

public class Point {
    private String name;
    private ArrayList<Route> routes;
    private boolean has_vehicle = false;
    private Vehicle vehicle;

    public Point(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

}