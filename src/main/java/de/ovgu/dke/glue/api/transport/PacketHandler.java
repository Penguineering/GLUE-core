package de.ovgu.dke.glue.api.transport;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public interface PacketHandler {
	public void handle(PacketThread packetThread, Packet packet) throws TransportException;
}
