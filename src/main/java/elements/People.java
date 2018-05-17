package elements;

public class People {
    private int number;
    private String location;

    public People(int number, String location) {
        this.number = number;
        this.location = location;
    }

    public People(People people){
        this.number=people.number;
        this.location=new String(people.location);
    }

    public int getNumber() {
        return number;
    }

    public String getLocation() {
        return location;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String toString(){ return "Group with: "+number+" persons in: "+location; }
}