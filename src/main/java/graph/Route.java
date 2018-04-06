package graph;

public class Route {
    private Point destiny;
    private double distance; //km

    public Route(Point destiny, double distance) {
        this.destiny = destiny;
        this.distance = distance;
    }

    public Point getDestiny() {
        return destiny;
    }

    public double getDistance() {
        return distance;
    }
}