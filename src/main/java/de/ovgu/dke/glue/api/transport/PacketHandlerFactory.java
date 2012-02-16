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
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
@ThreadSafe
public interface PacketHandlerFactory {
	/**
	 * Create a packet handler instance.
	 * 
	 * @param schema
	 *            The serialization schema used in this thread
	 * @return A packet handler instance.
	 * @throws InstantiationException
	 *             if the packet handler cannot be created.
	 */
	public PacketHandler createPacketHandler(final String schema)
			throws InstantiationException;
}
