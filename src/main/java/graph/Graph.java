package graph;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections15.Transformer;
import org.xml.sax.SAXException;

import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
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
		vehicles = reader.getVehiclesFromFile(points);
		groups_people = reader.getPeopleFromFile(points);
		safe_point = reader.getSafePointFromFile(points);

	}

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


		//System.out.println("The graph g = " + g.toString());

		printGraph(g); 

	}

	public void printGraph(SparseMultigraph<String, String> g) {


		/* set edges length in graph */ 

		Transformer<String,Integer> edgeLength = new Transformer<String, Integer>(){

			@Override
			public Integer transform(String input) {
				Route e = Utils.getEdgeByName(input, points);
				return (int)e.getDistance(); 
			}
		}; 


		SpringLayout<String, String> layout = new SpringLayout<String, String>( g,  edgeLength ); 


		layout.setForceMultiplier(1);
		//layout.setStretch(0);
		layout.setRepulsionRange(200);
		//layout.setSize(new Dimension(500,500));  

		
		//layout.setLocation("B", new Point(0,0));

		/* for(Point p :points) {
			layout.lock(p.getName(), true);
		} */



		VisualizationViewer<String,String> vv = new VisualizationViewer<String,String>(layout); 
		//vv.setPreferredSize(new Dimension(800,800)); 

		colorGraph(vv);

		JFrame frame = new JFrame("Simple Graph View"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.getContentPane().add(vv);  

		frame.pack(); 
		frame.setVisible(true);  
	}


	private void colorGraph(VisualizationViewer<String, String> vv) {

		Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {

			@Override
			public Paint transform(String input) {
				return Color.GREEN;
			}
		};

		Transformer<String,Stroke> edgeStrokeTransformer = new Transformer<String,Stroke>() {


			@Override
			public Stroke transform(String input) {
				float dash[] = {10.0f};
				final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

				return edgeStroke;
			}
		};

		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		// Create a graph mouse and add it to the visualization component
		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		vv.setGraphMouse(gm); 

	}
}