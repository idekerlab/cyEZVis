package org.cytoscape.calvr.internal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkViewConverter {

	public NetworkViewConverter() {

	}

	public void send() throws IOException {
		int port = 19997;
		
		String test = "circle c1 x=0 y=0 z=0 radius=200";

		byte[] buf = test.getBytes();
		DatagramSocket socket = new DatagramSocket();
		InetAddress address = InetAddress.getByName("127.0.0.1");
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
		socket.send(packet);

		System.out.println("Done!");

		socket.close();
	}
}
