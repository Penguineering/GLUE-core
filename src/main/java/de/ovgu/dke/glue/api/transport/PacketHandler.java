package de.ovgu.dke.glue.api.transport;

public interface PacketHandler {

	public void handle(PacketThread packetThread, Packet packet) throws TransportException;
	
}
