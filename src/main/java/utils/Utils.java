package utils;

import graph.Point;
import graph.Route;

import java.util.ArrayList;

public class Utils {

    public static Point getPointByName(String name, ArrayList<Point> points){

        for(int i=0; i< points.size(); i++){
            if(points.get(i).getName().equals(name)){
                return points.get(i);
            }

        }
        return null;
    }
    
    public static Route getEdgeByName(String name, ArrayList<Point> points){

        for(Point p : points){
        	
            ArrayList<Route> routes = p.getRoutes();
            
            for(Route r : routes) {
            	
            	 if(r.getName().equals(name)){
                     return r;
                 }
            }
        }
        return null;
    }
}
