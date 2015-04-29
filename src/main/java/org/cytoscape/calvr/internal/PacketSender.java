package org.cytoscape.calvr.internal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collection;

public class PacketSender {

	// TODO Create properties for these
	private static final int PORT_NUMBER = 16667;
	private static final String IP = "127.0.0.1";

	private final DatagramSocket socket;
	private final InetAddress address;

	public PacketSender() throws IOException {
		this.socket = new DatagramSocket();
		this.address = InetAddress.getByName(IP);
	}

	public void send(final Collection<String> dataCollection) throws IOException {
		dataCollection.stream().forEach(data -> send(data));
	}

	public final void send(final String data) {
		final byte[] buf = data.getBytes();
		System.out.println("Size = " + buf.length);
		final DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT_NUMBER);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void close() {
		socket.close();
	}
}
