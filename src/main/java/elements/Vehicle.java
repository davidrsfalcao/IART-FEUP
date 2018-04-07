package elements;

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

    public int getVelocity() {
        return velocity;
    }

    public String getLocation() {
        return location;
    }

}