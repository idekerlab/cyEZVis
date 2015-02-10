package org.cytoscape.calvr.internal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collection;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

public class NetworkViewConverter {
	
	private static final int PORT_NUMBER = 19997;

	private final DatagramSocket socket;
	private final InetAddress address;
	
	public NetworkViewConverter() throws IOException {
		this.socket = new DatagramSocket();
		this.address = InetAddress.getByName("127.0.0.1");
	}

	public void send(final CyNetworkView view) throws IOException {
		String test = "circle c1 x=0 y=0 z=0 radius=200";
		final CyNetwork network = view.getModel();

		final Collection<View<CyNode>> nodeViews = view.getNodeViews();
		nodeViews.stream().forEach(nv->node2sahpe(nv, network));
		
		

		System.out.println("Done!");

		socket.close();
	}
	
	private final void node2sahpe(final View<CyNode> nv, final CyNetwork network) {
		String nvs = "circle";
		Double x = nv.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
		Double y = nv.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
		String name = network.getRow(nv.getModel()).get(CyNetwork.NAME, String.class);
		String kegg = network.getRow(nv.getModel()).get("KEGG_NODE_LABEL_LIST_FIRST", String.class);
		nvs = nvs + " " + name + " x=" + x + " z=" + (-y) + 
				" radius=10; text " + name + "-label label=" + kegg + " size=7 x=" + x + " z=" + (-y -25) + " y=-5 rgba=1,1,1,0.5";
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
