package org.cytoscape.calvr.internal;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collection;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

/**
 * Converter from CyNetworkView to MUGIC compatible packets
 * 
 */
public class NetworkViewConverter {
	
	// TODO Create properties for these 
	private static final int PORT_NUMBER = 19997;
	private static final String IP = "127.0.0.1";

	private final DatagramSocket socket;
	private final InetAddress address;
	
	public NetworkViewConverter() throws IOException {
		this.socket = new DatagramSocket();
		this.address = InetAddress.getByName(IP);
	}

	public void send(final CyNetworkView view) throws IOException {

		convert(view);
		
		System.out.println("Done!");
		socket.close();
	}
	
	private final void convert(final CyNetworkView view) {
		final CyNetwork network = view.getModel();
		
		// Send node data
		final Collection<View<CyNode>> nodeViews = view.getNodeViews();
		nodeViews.stream().forEach(nv->node2sahpe(nv, network));

		// Send Edge Data
		final Collection<View<CyEdge>> edgeViews = view.getEdgeViews();
		edgeViews.stream().forEach(ev->edge2line(ev, view));
	}
	
	private final void edge2line(final View<CyEdge> ev, final CyNetworkView view) {
		
		final CyEdge edge = ev.getModel();
		final View<CyNode> sourceView = view.getNodeView(edge.getSource());
		final View<CyNode> targetView = view.getNodeView(edge.getTarget());
		
		
		// Extract basic node view information
		final Double x1 = sourceView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
		final Double y1 = sourceView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
		
		final Double x2 = targetView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
		final Double y2 = targetView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
		
		final Double width = ev.getVisualProperty(BasicVisualLexicon.EDGE_WIDTH);
		
		final Color color = (Color) ev.getVisualProperty(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT);
		final Double alpha = ev.getVisualProperty(BasicVisualLexicon.EDGE_TRANSPARENCY)/255.0;
		
		String name = ev.getSUID().toString();
		
		final String evs = "line " + name + " x1=" + x1 + " z1=" + (-y1) + " x2=" + x2 + " z2=" + (-y2) +
				" width=" + width + " " + decodeColor(color, alpha);
		System.out.println(evs);
		
		byte[] buf = evs.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT_NUMBER);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private final String decodeColor(final Color color, final Double alpha) {
		final double r = color.getRed()/255.0;
		final double g = color.getGreen()/255.0;
		final double b = color.getBlue()/255.0;
		
		final String colorStr = "r1=" + r + " g1=" + g + " b1=" + b + " a1=" + alpha +
								" r2=" + r + " g2=" + g + " b2=" + b + " a2=" + alpha;
		return colorStr;
	}
	
	
	/**
	 * Convert node view object to simple MUGIC primitive
	 * 
	 * @param nv Node view
	 * @param network 
	 */
	private final void node2sahpe(final View<CyNode> nv, final CyNetwork network) {
		
		final String shape = MugicShape.Circle.getName();
		
		// Extract basic node view information
		final Double x = nv.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
		final Double y = nv.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
		
		final Double size = nv.getVisualProperty(BasicVisualLexicon.NODE_SIZE);
		final Color color = (Color) nv.getVisualProperty(BasicVisualLexicon.NODE_FILL_COLOR);
		final Double alpha = nv.getVisualProperty(BasicVisualLexicon.NODE_TRANSPARENCY)/255.0;
		
		String name = network.getRow(nv.getModel()).get(CyNetwork.NAME, String.class);
		String kegg = network.getRow(nv.getModel()).get("KEGG_NODE_LABEL_LIST_FIRST", String.class);
		
		final String nvs = shape + " " + name + " x=" + x + " z=" + (-y) + 
				" radius=" + size + " " + decodeColor(color, alpha);
//						+ "text " + name + "-label label=" + name + " size=3 x=" + x + " z=" + (-y -25) + " y=-5 rgba=1,1,1,0.5";
		System.out.println(nvs);
		
		byte[] buf = nvs.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT_NUMBER);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
