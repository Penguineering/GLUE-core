package de.ovgu.dke.glue.api.transport;

import java.net.URI;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.Serializer;

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
 * <p>
 * Serializers are bound to a packet thread, i.e. each packet thread may have a
 * different serialization schema (which must be assigned upon creation),
 * however, the serializer must be available in the Transport via the
 * SerializationProvider.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
@NotThreadSafe
public abstract class PacketThread {
	/**
	 * Use the default packet handler.
	 */
	public static PacketHandler DEFAULT_HANDLER = null;

	/**
	 * Use the default schema.
	 */
	public static String NO_SERALIZATION = null;

	private final String schema;

	/**
	 * Create a packet thread with a specific serialization schema.
	 * 
	 * @param schema
	 *            The serialization schema. Using <code>null</code> disables
	 *            serialization for this thread.
	 */
	public PacketThread(final String schema) {
		this.schema = schema;
	}

	/**
	 * Get the serialization schema for this packet thread, which must be set
	 * upon creation and cannot be changed.
	 * 
	 * @return The serialization schema. <code>null</code> if serialization is
	 *         disabled.
	 */
	public final String getSerializationSchema() {
		return schema;
	}

	/**
	 * Dispose the packet thread. Incoming messages for this thread will be
	 * rejected and sending will no longer be possible.
	 */
	public abstract void dispose();

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
	public final void send(final Object payload, final Packet.Priority priority)
			throws TransportException {
		final Transport transport = getTransport();
		if (transport == null)
			throw new TransportException(
					"Transport not available, PacketThread already disposed?");

		final Serializer serializer = getSerializationSchema() == null ? null
				: transport.getSerializer(getSerializationSchema());

		final Object p;
		if (serializer != null)
			try {
				p = serializer.serialize(payload);
			} catch (SerializationException e) {
				throw new TransportException("Error on payload serialization: "
						+ e.getMessage(), e);
			}
		else
			p = payload;

		sendSerializedPayload(p, priority);
	}

	/**
	 * Send a serialized packet in this thread. This method needs to be
	 * overwritten by the transport implementation.
	 * 
	 * @param payload
	 *            Serialized payload to send with this message.
	 * @param priority
	 *            The message priority, if supported by the transport, otherwise
	 *            this parameter may be ignored.
	 * @throws TransportException
	 *             if the packet cannot be delivered to the send queue.
	 */
	protected abstract void sendSerializedPayload(final Object payload,
			final Packet.Priority priority) throws TransportException;

	/**
	 * Get the thread's transport.
	 * 
	 * @return The transport this packet thread belongs to.
	 */
	public abstract Transport getTransport();

	/**
	 * Get the thread's peer.
	 * 
	 * @return The transport peer this thread belongs to.
	 */
	public abstract URI getPeer();
}
