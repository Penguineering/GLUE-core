package de.ovgu.dke.glue.api.transport;

import net.jcip.annotations.NotThreadSafe;

/**
 * <p>
 * A packet thread represents a linear packet-flow between two peers. Each
 * packet must be assigned to a packet thread and multiple threads may be used
 * on a transport to represent multiple, distinct communication contexts between
 * two peers. To create a packet thread, a transport must be acquired from the
 * transport factory.
 * </p>
 * 
 * <p>
 * Implementations of this interface may not be thread safe. Packets on a packet
 * thread are processed subsequently and the thread context of a packet thread
 * instance changes depending on the processing party. Implementations of the
 * GLUE API must ensure that one packet thread instance is never called twice at
 * the same time.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
// TODO wie kommt man von hier auf den transport?
@NotThreadSafe
public interface PacketThread {
	/**
	 * Use the default packet handler.
	 */
	public static PacketHandler DEFAULT_HANDLER = null;

	/**
	 * Dispose the packet thread. Incoming messages for this thread will be
	 * rejected and sending will no longer be possible.
	 */
	public void dispose();

	/**
	 * Send a packet in this thread. The thread's transport is used. If this
	 * method returns without an exception, the payload could be serialized and
	 * the packet has been successfully added to the packet queue. However,
	 * errors may still occur during the delivery process, which will be
	 * announced over the Reporter framework.
	 * 
	 * @param payload
	 *            Payload to send with this message.
	 * @param prority
	 *            The message priority, if supported by the transport, otherwise
	 *            this parameter may be ignored.
	 * @throws TransportException
	 *             If the payload cannot be serialized or the packet could not
	 *             be delivered to the send queue.
	 */
	public void send(final Object payload, final Packet.Priority prority)
			throws TransportException;

}
