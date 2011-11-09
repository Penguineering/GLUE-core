package de.ovgu.dke.glue.api.transport;

import net.jcip.annotations.ThreadSafe;

/**
 * <p>
 * Handler for incoming packets. This interface is the entry point for an
 * application to react to incoming packets.
 * </p>
 * 
 * <p>
 * The packet handler is called in blocking mode, i.e. incoming packets may not
 * be processed while the handler is running. An implementation should only
 * store the packet in a processing queue or call a distinct thread if
 * processing the packet is time-consuming.
 * </p>
 * 
 * <p>
 * The packet handler may be called in multiple threads simultaneously and must
 * take care of the necessary synchronization!
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
@ThreadSafe
public interface PacketHandler {
	/**
	 * Handle an incoming packet from the specified packet thread.
	 * 
	 * @param packetThread
	 *            The packet thread in which the packet occured.
	 * @param packet
	 *            The incoming packet.
	 * @throws TransportException
	 *             if there is a transport or communication related exception in
	 *             handling the packet.
	 */
	public void handle(PacketThread packetThread, Packet packet)
			throws TransportException;
}
