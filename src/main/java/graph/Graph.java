package graph;


import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.collections15.Transformer;
import org.xml.sax.SAXException;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;


import elements.People;
import elements.Vehicle;
import utils.Reader;
import utils.Utils;


public class Graph {

	private ArrayList<Point> points;
	private Point safe_point;
    private ArrayList<People> groups_people;
    private ArrayList<Vehicle> vehicles;

	public Graph(String filename){
		Reader reader = null;
		try {
			reader = new Reader(filename);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		points = reader.getPointsFromFile();

		reader.getRoutesFromFile(points);
		safe_point = reader.getSafePointFromFile(points);

        vehicles = reader.getVehiclesFromFile(points);
        groups_people = reader.getPeopleFromFile(points);
	}

	public ArrayList<People> getPeople() { return groups_people; }

    public ArrayList<Vehicle> getVehicles() { return vehicles; }

	public ArrayList<Point> getPoints() {
		return points;
	}

	public Point getSafe_point() {
		return safe_point;
	}

	public void display() {

		SparseMultigraph<String, String> g = new SparseMultigraph<String, String>();

		/* add all point to the graph */
		for(Point p : points) {
			g.addVertex(p.getName());
		}

		/* add edges */
		for(Point p : points) {
			ArrayList<Route> routes = p.getRoutes();

			for( Route r : routes ) {

				if(! g.containsEdge(r.getName())) {
					g.addEdge(r.getName(), p.getName(), r.getDestiny().getName());
				}
			}
		}


		Transformer<String, Point2D> locationTransformer = new Transformer<String, Point2D>() {

			@Override
			public Point2D transform(String vertex) {

				for(Point p : points) {
					if(p.getName().equals(vertex)){
						return new Point2D.Double((double) p.getX(), (double) p.getY());
					}
				}
				return null;
			}
		};

		/* create a layout */
		StaticLayout<String, String> layout = new StaticLayout<String, String>( g, locationTransformer);
		layout.setSize(new Dimension(1000, 1000));
		VisualizationViewer<String, String> vv = new VisualizationViewer<String, String>( layout);
		vv.setPreferredSize(new Dimension(1000, 1000));


		/* add the labels to the graph */
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);


		/* show the graph */
		JFrame frame = new JFrame("Graph View");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		vv.setOpaque(false);
		frame.pack();
		frame.setVisible(true);


	}


}