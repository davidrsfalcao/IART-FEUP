package graph;

public class Route {
    private Point destiny;
    private int distance; //km
    private String name;

    public Route(Point destiny, int distance, String name) {
        this.destiny = destiny;
        this.distance = distance;
        this.name = name;
    }

    public Point getDestiny() {
        return destiny;
    }

    public int getDistance() {
        return distance;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}