package elements;

import graph.Point;

public class Vehicle {
    private String name;
    private int capacity;
    private int velocity;
    private String location;
    private float delay = 0;

    public Vehicle(String name, int capacity, int velocity, String location) {
        this.name = name;
        this.capacity = capacity;
        this.velocity = velocity;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

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

}