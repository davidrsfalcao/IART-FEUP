import elements.People;
import elements.Vehicle;
import graph.Point;
import graph.Route;
import graph.State;
import utils.Utils;
import java.util.ArrayList;


import static java.lang.Thread.sleep;

public class Search {

    public static boolean debug = false;


    public static ArrayList<String> solution;

    public static void dfs(State st) {

        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(st.getVehicles().get(0));
        State newState = new State(st.getGraph(), st.getPeople(), vehicles);
        iterate(newState);    //inicialmente, apenas utiliza 1 veiculo

    }

    public static void iterate(State st) {

        /* get vehicle and current point */
        Vehicle v = st.getVehicles().get(0);
        String currentPoint = v.getLocation();


        if (st.getPath().contains(currentPoint)) {
            //  System.out.println("The vehicle is in: " + currentPoint);
            //  System.out.println("Current path: " + st.getPath());
            if(debug) System.out.println("->loop");
            return;
        }

        if(debug)System.out.println("");

        /* check people to be rescued in the current node */
        ArrayList<People> people = st.getPeople();
        for (People pp : people) {

            // people in a different location than the rescue, and the vehicle can transport people
            if (pp.getLocation().equals(currentPoint) &&
                    !(pp.getLocation().equals(st.getGraph().getSafe_point().getName()))
                    && pp.getNumber() > 0 && v.getCapacity() != 0) {

                //rescue the people !!

                if(debug)System.out.println("\nRescued people!\n");

                int nPeople = pp.getNumber();
                int maxCap = v.getCapacity();

                if (v.canTransport(nPeople)) {
                    v.setCurrentPersons(nPeople);
                    pp.setNumber(0);
                } else { //transporta o maximo
                    v.setCurrentPersons(maxCap);
                    pp.setNumber(nPeople - maxCap);
                }
                st.clearPath();

            }
        }

        /* check if in the safe place - leave people there */
        if (st.getGraph().getSafe_point().getName().equals(currentPoint) && v.isTransporting()) {

            if(debug)System.out.println("\nIn safe place people!\n");

            st.getGroupsPeople().add(new People(v.emptyVehicle(), currentPoint));

            if (st.allRescued()) {


                st.getPath().add(currentPoint);
                st.clearPath();

                if(debug)System.out.println("\n=========\nSolution: "+st.getReturnPath().toString() + " time: "+ v.getTime()+"\n=========");
                else System.out.println(st.getReturnPath().toString() + ";"+ v.getTime());

                return;

            } else {
              //  System.out.println("going back...");
                st.clearPath();

            }
        }

        st.getPath().add(currentPoint);

        /* print path */
        if(debug)System.out.print(st+" --- ");
        if(debug)System.out.println(v);

       /* st.displayState();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

*/
        Point search = Utils.getPointByName(currentPoint, st.getGraph().getPoints());
        ArrayList<Route> routes = search.getRoutes();

        for (Route r : routes) {
            Point dest = r.getDestiny();
            v.setLocation(dest.getName());
            v.addDistance( r.getDistance() );
            iterate(new State(st));
            v.removeDistance(r.getDistance());
        }

    }
}
