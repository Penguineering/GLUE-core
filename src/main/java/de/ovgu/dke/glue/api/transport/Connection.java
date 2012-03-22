package de.ovgu.dke.glue.api.transport;

import java.net.URI;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.Serializer;

/**
 * <p>
 * Serializers are bound to a connection, i.e. each connection may have a
 * different serialization schema (which must be assigned upon creation),
 * however, the serializer must be available in the Transport via the
 * SerializationProvider.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
public abstract class Connection {

	/**
	 * Use the default schema.
	 */
	public static String NO_SERALIZATION = null;

	private final String connection_schema;

	/**
	 * Create a connection with a specific serialization schema.
	 * 
	 * @param schema
	 *            The serialization schema. Using <code>null</code> disables
	 *            serialization for this connection.
	 */
	public Connection(final String schema) {
		this.connection_schema = schema;
	}

	/**
	 * Get the serialization schema for this connection, which must be set upon
	 * creation and cannot be changed.
	 * 
	 * @return The serialization schema. <code>null</code> if serialization is
	 *         disabled.
	 */
	public final String getConnectionSchema() {
		return connection_schema;
	}

	/**
	 * <p>
	 * Create a packet thread to send and receive packets from the peer of this
	 * transport.
	 * </p>
	 * 
	 * @param connection_schema
	 *            The serialization schema for this thread.
	 * @param handler
	 *            The packet handler to use for incoming packets. Set to
	 *            <code>null</code> or <code>PacketThread.DEFAULT_HANDLER</code>
	 *            to use the factory default handler for the given schema.
	 * @return A new packet thread.
	 * @throws TransportException
	 *             if the thread could not be created.
	 */
	public abstract PacketThread createThread(final PacketHandler handler)
			throws TransportException;

	/**
	 * Get the connection's transport.
	 * 
	 * @return The transport this connection belongs to.
	 */
	public abstract Transport getTransport();

	/**
	 * Send a packet in this connection. The connection's transport is used. If
	 * this method returns without an exception, the payload could be serialized
	 * and the packet has been successfully added to the packet queue. However,
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
	public final void send(final PacketThread pt, final Object payload,
			final Packet.Priority priority) throws TransportException {
		final Transport transport = getTransport();
		if (transport == null)
			throw new TransportException(
					"Transport not available, connection already disposed?");

		final String conn_s = getConnectionSchema();

		final Serializer serializer = conn_s == null ? null : transport
				.getSerializer(conn_s);

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

		sendSerializedPayload(pt, p, priority);
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
	protected abstract void sendSerializedPayload(final PacketThread pt,
			final Object payload, final Packet.Priority priority)
			throws TransportException;

	/**
	 * Get the thread's peer.
	 * 
	 * @return The peer this connection belongs to.
	 */
	public abstract URI getPeer();

}
