package elements;

import graph.Point;

public class Vehicle {
    private String name;
    private int capacity;
    private int currentPersons;
    private int velocity;
    private String location;
    private float delay = 0;

    public Vehicle(String name, int capacity, int velocity, String location) {
        this.name = name;
        this.capacity = capacity;
        this.velocity = velocity;
        this.location = location;
        this.currentPersons=0;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {  return capacity;  }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getVelocity() {
        return velocity;
    }

    public String getLocation() {
        return location;
    }

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
        return rescued;
    }

}