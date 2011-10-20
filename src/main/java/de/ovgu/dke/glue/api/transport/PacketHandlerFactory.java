package de.ovgu.dke.glue.api.transport;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public interface PacketHandlerFactory {
	
	public PacketHandler createPacketHandler() throws TransportException;

}
