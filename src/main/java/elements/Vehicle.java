package elements;

import java.util.ArrayList;

public class Vehicle {

    public enum State {
        GO, RETURN
    }

    private String name;
    private int capacity;
    public int currentPersons;
    private int totalRescued;
    private int velocity;
    private String location;
    private float totalDistance;
    private boolean active;
    private State state;

    //path
    private ArrayList<String> goPath;
    private ArrayList<String> returnPath;
    private ArrayList<String> finalPath;


    public Vehicle(String name, int capacity, int velocity, String location) {
        this.name = name;
        this.capacity = capacity;
        this.velocity = velocity;
        this.location = location;
        this.currentPersons = 0;
        this.totalDistance = 0;
        this.goPath = new ArrayList<String>();
        this.returnPath = new ArrayList<String>();

        this.totalRescued = 0;
        this.active = true;
        state = State.GO;
    }


    public Vehicle(Vehicle vehicle) {
        this.name = vehicle.name;
        this.capacity = vehicle.capacity;
        this.currentPersons = vehicle.currentPersons;
        this.velocity = vehicle.velocity;
        this.location = vehicle.location;
        this.totalDistance = vehicle.totalDistance;

        goPath = new ArrayList<String>(vehicle.getPath().size());
        for (String s : vehicle.getPath()) {
            goPath.add(new String(s));
        }

        returnPath = new ArrayList<String>(vehicle.getReturnPath().size());
        for (String s : vehicle.getReturnPath()) {
            returnPath.add(new String(s));
        }

        this.totalRescued = vehicle.totalRescued;
        this.active = vehicle.active;
        this.state = vehicle.state;
    }


    /* getters */

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void stop() {
        this.active = false;
    }

    public int getTotalRescued() {
        return totalRescued;
    }

    public int getCapacity() {
        return capacity;
    }


    public int getVelocity() {
        return velocity;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<String> getReturnPath() {
        return returnPath;
    } //saved path

    public ArrayList<String> getPath() {
        return goPath;
    }           //current path

    public ArrayList<String> getFinalPath(){
        return finalPath;
    }   //final path

    public String getState() {
        return state.name();
    }

    /* setters */

    public void addDistance(int distance) {
        this.totalDistance += distance;
    }

    public void removeDistance(int distance) {
        this.totalDistance -= distance;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setReturn(){ this.state = State.RETURN; }

    public void setGo(){ this.state = State.GO;  }



    /*
     * Save the current path and clear
     */
    public void clearPath() {

        ArrayList<String> newList = new ArrayList<String>(this.goPath);
        this.returnPath.addAll(newList);
        this.goPath = new ArrayList<String>();
    }

    /*
     * Check if the vehicle can transport n persons
     * */
    public boolean canTransport(int number) {
        return capacity - number >= 0;
    }

    /*
     * Check if the vehicle has people
     * */
    public boolean isTransporting() {
        return currentPersons != 0;
    }

    /*
     * Update current persons in the vehicle
     * */
    public void setCurrentPersons(int number) {
        this.currentPersons = number;
        this.capacity = capacity - number;
    }

    /*
     * Leave people in the safe place
     * */
    public int emptyVehicle() {
        this.capacity = capacity + currentPersons;
        int rescued = currentPersons;
        this.currentPersons = 0;
        this.totalRescued += rescued;
        return rescued;
    }

    /*
     * Print vehicle status
     * */
    public String toString() {
        return this.name + " with " + this.currentPersons + " people; Distance: " + this.totalDistance + " Path: " +
                (returnPath.isEmpty() ? "" : returnPath.toString()) +
                (goPath.isEmpty() ? "" : goPath.toString());
    }


    /*
     * Organizes the vehicle saved path
     * */
    public void organizePath(){

        finalPath = new ArrayList<String>();
        finalPath.addAll(returnPath);
        finalPath.addAll(goPath);


        if(! finalPath.get(finalPath.size()-1).equals("H")){
            for(int j=finalPath.size()-1; j>0; j--){
                if(finalPath.get(j).equals("H")){
                    finalPath = new ArrayList(finalPath.subList(0, j+1));
                    return;
                }
            }
        }
    }

}