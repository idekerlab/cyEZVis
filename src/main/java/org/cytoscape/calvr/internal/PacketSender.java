package org.cytoscape.calvr.internal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Properties;

import org.cytoscape.property.CyProperty;

public class PacketSender {

	private static final int DEFAULT_PORT_NUMBER = 16667;
	private static final String DEFAULT_IP = "127.0.0.1";
	private final String CALVR_IP_PROP_NAME = "calvr.ip";
	private final String CALVR_PORT_PROP_NAME = "calvr.port";
	
	private String ipAddress;
	private final Integer portNumber;

	private final DatagramSocket socket;
	private final InetAddress address;

	public PacketSender(final CyProperty<Properties> props) throws IOException {
		this.socket = new DatagramSocket();
		
		ipAddress = props.getProperties().getProperty(CALVR_IP_PROP_NAME);
		final String portNumberString = props.getProperties().getProperty(CALVR_PORT_PROP_NAME);
		
		if(ipAddress == null) {
			ipAddress = DEFAULT_IP;
		}
		this.address = InetAddress.getByName(ipAddress);
		
		if(portNumberString == null) {
			this.portNumber = DEFAULT_PORT_NUMBER;
		} else {
			this.portNumber = Integer.parseInt(portNumberString);
		}
		
		System.out.println("Sending to EZVis server at " + ipAddress + ":" + portNumber);
	}

	public void send(final Collection<String> dataCollection) throws IOException {
		dataCollection.stream().forEach(data -> send(data));
	}

	public final void send(final String data) {
		final byte[] buf = data.getBytes();
		
		// TODO: Optimize packet size
		System.out.println("Size = " + buf.length);
		final DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portNumber);
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