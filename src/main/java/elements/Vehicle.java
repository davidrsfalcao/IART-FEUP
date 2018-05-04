package elements;

import graph.Point;

public class Vehicle {
    private String name;
    private int capacity;
    private int currentPersons;
    private int velocity;
    private String location;
    private float delay = 0;

    private float totalTime; // Tempo total gasto a navegar ate estado atual

    public Vehicle(String name, int capacity, int velocity, String location) {
        this.name = name;
        this.capacity = capacity;
        this.velocity = velocity;
        this.location = location;
        this.currentPersons=0;
        this.totalTime=0;
    }

    public Vehicle(Vehicle vehicle){
        this.name=vehicle.name;
        this.capacity = vehicle.capacity;
        this.currentPersons=vehicle.currentPersons;
        this.velocity=vehicle.velocity;
        this.location=vehicle.location;
        this.totalTime=vehicle.totalTime;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {  return capacity;  }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public void addDistance(int distance ){ this.totalTime+= distance/velocity; }

    public void removeDistance(int distance) { this.totalTime -= distance/velocity;}

    public float getTime() { return this.totalTime; }

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

    public String toString(){
        return "Vehicle with "+this.currentPersons+" people; time: "+this.totalTime;
    }

}