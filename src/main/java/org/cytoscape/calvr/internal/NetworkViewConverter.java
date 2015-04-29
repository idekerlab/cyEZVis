package org.cytoscape.calvr.internal;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
	
	private static final Double OFFSET = 5.0; 
	
	public final void convert(final CyNetworkView view) throws IOException {
		final PacketSender sender = new PacketSender();
		
		final CyNetwork network = view.getModel();
		final Collection<View<CyNode>> nodeViews = view.getNodeViews();
		final Collection<View<CyEdge>> edgeViews = view.getEdgeViews();

		// Send Edge Data
		final List<String> edgeStrList = edgeViews.stream()
				.map(ev->edge2line(ev, view))
				.collect(Collectors.toList());
		sender.send(edgeStrList);
		
		// Send node data
		final List<String> nodeStrList = nodeViews.stream()
			.map(nv->node2sahpe(nv, network))
			.collect(Collectors.toList());
		sender.send(nodeStrList);
		
		sender.close();
	}
	
	private final String edge2line(final View<CyEdge> ev, final CyNetworkView view) {
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
		
		final String evs = "line " + name + " pos1=" + x1 + ",0," + (-y1) + 
				" pos2=" + x2 + ",0," + (-y2) +
				" width=" + width + " " + decodeColor(color, alpha);
		
		System.out.println(evs);
		
		return evs;
	}


	/**
	 * Convert node view object to simple MUGIC primitive
	 * 
	 * @param nv Node view
	 * @param network 
	 */
	private final String node2sahpe(final View<CyNode> nv, final CyNetwork network) {
		
		final String shape = MugicShape.Circle.getName();
		
		// Extract basic node view information
		final Double x = nv.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
		final Double y = nv.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
		
		final Double width = nv.getVisualProperty(BasicVisualLexicon.NODE_WIDTH);
		final Double height = nv.getVisualProperty(BasicVisualLexicon.NODE_HEIGHT);
		final Double radius = width/2.0;
		final Color color = (Color) nv.getVisualProperty(BasicVisualLexicon.NODE_FILL_COLOR);
		final Double alpha = nv.getVisualProperty(BasicVisualLexicon.NODE_TRANSPARENCY)/255.0;
		
		// Label Text data
		final String name = network.getRow(nv.getModel()).get(CyNetwork.NAME, String.class) + "-" + nv.getModel().getSUID();
		final String label = nv.getVisualProperty(BasicVisualLexicon.NODE_LABEL);
		final Integer labelSize = nv.getVisualProperty(BasicVisualLexicon.NODE_LABEL_FONT_SIZE);
		final Double labelX = x - (radius/2.0);
		final Double labelY = y + radius + labelSize + OFFSET;
		final Color labelColor = (Color) nv.getVisualProperty(BasicVisualLexicon.NODE_LABEL_COLOR);
		final Double labelAlpha = nv.getVisualProperty(BasicVisualLexicon.NODE_LABEL_TRANSPARENCY).doubleValue();
		
		final String nvs = shape + " " + name + " pos=" + x + ",-0.1," + (-y) + 
				" radius=" + radius + " " + decodeColor(color, alpha) + 
				"; text label-" + nv.getModel().getSUID() + 
				" label=\"" + label + "\" pos=" + 
				labelX + ",0," + (-labelY) + 
				" size=" + labelSize + 
				" " + decodeColor(labelColor, labelAlpha) + 
				" enableOutline=0";
		
		System.out.println(nvs);
		
		return nvs;
	}


	private final String decodeColor(final Color color, final Double alpha) {
		final double r = color.getRed()/255.0;
		final double g = color.getGreen()/255.0;
		final double b = color.getBlue()/255.0;
		
		final String colorStr = "color=" + r + "," + g + "," + b + "," + alpha;
		return colorStr;
	}
}
