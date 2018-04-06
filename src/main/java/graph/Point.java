package graph;

import java.util.ArrayList;

public class Point {
    private String name;
    private ArrayList<Route> routes;
    protected boolean has_vehicle;

    public Point(String name, ArrayList<Route> routes) {
        this.name = name;
        this.routes = routes;
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
}