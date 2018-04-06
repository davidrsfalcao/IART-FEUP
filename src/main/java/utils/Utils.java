package utils;

import graph.Point;

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

}
