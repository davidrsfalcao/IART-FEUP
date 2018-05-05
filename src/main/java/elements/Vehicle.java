package elements;

import graph.Route;

import java.util.ArrayList;

public class Vehicle {
    private String name;
    private int capacity;
    private int currentPersons;
    private int totalRescued=0;
    private int velocity;
    private String location;
    private ArrayList<String> goPath;
    private ArrayList<String> returnPath;
    private float totalTime;
    private ArrayList<Route> toGo;


    public Vehicle(String name, int capacity, int velocity, String location) {
        this.name = name;
        this.capacity = capacity;
        this.velocity = velocity;
        this.location = location;
        this.currentPersons=0;
        this.totalTime=0;
        this.goPath = new ArrayList<String>();
        this.returnPath = new ArrayList<String>();
        toGo=new ArrayList<Route>();
        this.totalRescued=0;
    }


    public Vehicle(Vehicle vehicle){
        this.name=vehicle.name;
        this.capacity = vehicle.capacity;
        this.currentPersons=vehicle.currentPersons;
        this.velocity=vehicle.velocity;
        this.location=vehicle.location;
        this.totalTime=vehicle.totalTime;
        this.goPath=new ArrayList<String>(vehicle.getPath());
        this.returnPath=new ArrayList<String>(vehicle.getReturnPath());
        this.toGo=new ArrayList<Route>( );
        this.totalRescued = vehicle.totalRescued;
    }


    /* getters */

    public String getName() {
        return name;
    }

    public int getTotalRescued() { return totalRescued; }

    public int getCapacity() {  return capacity;  }

    public float getTime() { return this.totalTime; }

    public int getVelocity() {
        return velocity;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Route> getGoRoutes(){ return toGo; }

    /*
     * Get the current path
     */
    public ArrayList<String> getPath() { return goPath; }

    /*
     * Get the saved path
     */
    public ArrayList<String> getReturnPath() { return returnPath; }


    /*
     * Save the current path and clear
     */
    public void clearPath() {
        ArrayList<String> newList =new ArrayList<String>(this.goPath);
        this.returnPath.addAll(newList);
        this.goPath=new ArrayList<String>();
    }



    public void setCapacity(int capacity) { this.capacity = capacity; }

    public void addDistance(int distance ){ this.totalTime+= distance; }

    public void removeDistance(int distance) { this.totalTime -= distance; }


    public void setLocation(String location) {
        this.location = location;
    }

    public boolean canTransport(int number){ return capacity-number>=0; }

    public boolean isTransporting(){  return currentPersons!=0;  }

    public void setCurrentPersons(int number){
        this.currentPersons=number;
        this.capacity= capacity-number;
    }

    public int emptyVehicle(){
        this.capacity=capacity+currentPersons;
        int rescued = currentPersons;
        this.currentPersons=0;
        this.totalRescued+=rescued;
        return rescued;
    }

    public String toString(){
        return this.name+" with "+this.currentPersons+" people; time: "+this.totalTime + " Current path: "+
        (returnPath.isEmpty()?"":returnPath.toString()) +
                (goPath.isEmpty()?"":goPath.toString());
    }

}