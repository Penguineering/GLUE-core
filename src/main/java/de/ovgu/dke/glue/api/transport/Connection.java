package de.ovgu.dke.glue.api.transport;

import java.net.URI;

import net.jcip.annotations.ThreadSafe;
import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

/**
 * Abstract base class representing the virtual connection between two
 * middleware implementations on different clients.
 * 
 * <p>
 * Serializers are bound to a connection, i.e. each connection may have a
 * different serialization schema (which must be assigned upon creation),
 * however, the serializer must be available in the Transport via the
 * SerializationProvider.
 * </p>
 * 
 * <p>
 * The Connection may be used in multiple threads and must be thread safe!
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
@ThreadSafe
public abstract class Connection {

	private final String connection_schema;

	/**
	 * Create a connection with a specific serialization schema.
	 * 
	 * @param schema
	 *            The connection schema which may not be {@code null}
	 * @throws NullPointerException
	 *             if the schema is {@code null}
	 */
	protected Connection(final String schema) {
		if (schema == null)
			throw new NullPointerException(
					"The connection schema may not be null!");

		this.connection_schema = schema;
	}

	/**
	 * Get the connection schema for this connection, which must be set upon
	 * creation and cannot be changed.
	 * 
	 * @return The serialization schema, which cannot be {@code null}
	 */
	public final String getConnectionSchema() {
		return connection_schema;
	}

	/**
	 * Get the connection's serialization format. The value depends on the
	 * transport's capabilities, but may vary between connections if a transport
	 * supports more than one format.
	 * 
	 * @return The serialization format according to the formats in
	 *         {@link SerializationProvider}.
	 */
	public abstract String getSerializationFormat();

	/**
	 * Create a packet thread to send and receive packets from the peer of this
	 * transport.
	 * 
	 * @param connection_schema
	 *            the serialization schema for this thread
	 * @param handler
	 *            The packet handler to use for incoming packets. Set to
	 *            {@code null} or {@code PacketThread.DEFAULT_HANDLER} to use
	 *            the factory default handler for the given schema.
	 * @return a new packet thread
	 * @throws TransportException
	 *             if the thread could not be created.
	 * @throws IllegalStateException
	 *             if the transport is not available
	 */
	public abstract PacketThread createThread(final PacketHandler handler)
			throws TransportException;

	/**
	 * Get the connection's transport.
	 * 
	 * @return The transport this connection belongs to, must be non-null.
	 * @throws IllegalStateException
	 *             if the transport is not available
	 */
	public abstract Transport getTransport();

	/**
	 * Send a packet using this connection. The connection's transport is used.
	 * If this method returns without an exception, the payload could be
	 * serialized and the packet has been successfully added to the packet
	 * queue. However, errors may still occur during the delivery process, which
	 * will be announced over the Reporter framework.
	 * 
	 * @param pt
	 *            the packet thread to use with this message, may not be
	 *            {@code null}
	 * @param payload
	 *            the Payload to send with this message
	 * @param prority
	 *            the message priority, if supported by the transport, otherwise
	 *            this parameter may be ignored.
	 * @throws TransportException
	 *             If the connection schema is unknown, the payload cannot be
	 *             serialized or the packet could not be delivered to the send
	 *             queue.
	 * @throws NullPointerException
	 *             if PacketThread pt is {@code null}
	 * @throws IllegalArgumentException
	 *             if the packet thread does not belong to this connection.
	 * @throws IllegalStateException
	 *             if the transport is not available
	 */
	// TODO is null-payload okay?
	public final void send(final PacketThread pt, final Object payload,
			final Packet.Priority priority) throws TransportException {
		if (pt == null)
			throw new NullPointerException(
					"Packet thread (pt) may not be null!");

		if (pt.getConnection() != this)
			throw new IllegalArgumentException(
					"Packet thread (pt) does not belong to this connection!");

		final Transport transport = getTransport();
		if (transport == null)
			throw new IllegalStateException(
					"Transport not available, connection already disposed?");

		try {
			// retrieve the schema record
			final SchemaRecord record = SchemaRegistry.getInstance().getRecord(
					getConnectionSchema());
			if (record == null)
				// TODO use a more verbose exception, e.g.
				// UnknownTransportSchemaException
				throw new TransportException(
						"The connection uses an unknown schema!");

			// find the serializer according to the connection's format
			final SerializationProvider prov = record
					.getSerializationProvider();

			final Serializer serializer = prov == null ? null : prov
					.getSerializer(this.getSerializationFormat());

			// serialize the payload
			final Object p;
			if (serializer != null)
				// may throw a SeralizationException
				p = serializer.serialize(payload);
			else
				p = payload;

			// send the serialized payload
			sendSerializedPayload(pt, p, priority);
		} catch (SerializationException e) {
			throw new TransportException("Error on payload serialization: "
					+ e.getMessage(), e);
		}
	}

	/**
	 * Send a serialized packet in this thread. This method needs to be
	 * overwritten by the transport implementation.
	 * 
	 * @param pt
	 *            Packet thread to be used; caller guarantees that this
	 *            parameter is not {@code null} and the packet thread belongs to
	 *            this connection.
	 * @param payload
	 *            Serialized payload to send with this message.
	 * @param priority
	 *            The message priority, if supported by the transport, otherwise
	 *            this parameter may be ignored.
	 * @throws TransportException
	 *             if the packet cannot be delivered to the send queue.
	 * @throws IllegalStateException
	 *             if the transport is not available
	 */
	protected abstract void sendSerializedPayload(final PacketThread pt,
			final Object payload, final Packet.Priority priority)
			throws TransportException;

	/**
	 * Get the thread's peer. This value is unstable as some transport
	 * implementations (e.g. XMPP) may change the effective peer ID during
	 * communication.
	 * 
	 * @return The peer this connection belongs to.
	 */
	public abstract URI getPeer();

	/**
	 * <p>
	 * Check whether the peers in a connection are able to communicate with each
	 * other. This is the case if they have matching serialization methods and
	 * schemas.
	 * </p>
	 * <p>
	 * Calling this method is optional, if left out a TransportException may
	 * occur when sending the first packet.
	 * </p>
	 * <p>
	 * If the serialization compatibility cannot be ensured, communication may
	 * still work, e.g. if the peer cannot handle capabilities but is able to
	 * decode the provided packet. However, this method allows to shift the
	 * point-of-failure.
	 * </p>
	 * <p>
	 * Note: This method may block for some time!
	 * </p>
	 * 
	 * @return true if serialization compatibility is given, otherwise false.
	 *         Communication may still succeed!
	 * @throws TransportException
	 *             if negotiation packets cannot be sent.
	 * @throws IllegalStateException
	 *             if the transport is not available
	 */
	public abstract boolean checkCapabilities() throws TransportException;
}
