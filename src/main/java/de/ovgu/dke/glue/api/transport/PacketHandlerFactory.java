package de.ovgu.dke.glue.api.transport;

import net.jcip.annotations.ThreadSafe;

/**
 * <p>
 * Factory for creating packet handlers.
 * </p>
 * 
 * <p>
 * The factory implementation must be thread-safe and may be invoked from
 * multiple threads simultaneously.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
@ThreadSafe
public interface PacketHandlerFactory {
	/**
	 * Create a packet handler instance.
	 * 
	 * @return A packet handler instance.
	 * @throws TransportException
	 *             if the packet handler cannot be created.
	 */
	public PacketHandler createPacketHandler() throws TransportException;

}
