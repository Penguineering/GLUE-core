package de.ovgu.dke.glue.api.transport;

public interface PacketThread {

	public void dispose();
	
	public void send(Packet packet) throws TransportException;
	
	
}
