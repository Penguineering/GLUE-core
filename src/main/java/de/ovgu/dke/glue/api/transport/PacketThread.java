/*
 * Copyright 2012 Stefan Haun, Thomas Low, Sebastian Stober, Andreas Nürnberger
 * 
 *      Data and Knowledge Engineering Group, 
 * 		Faculty of Computer Science,
 *		Otto-von-Guericke University,
 *		Magdeburg, Germany
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ovgu.dke.glue.api.transport;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;
import net.jcip.annotations.NotThreadSafe;

/**
 * <p>
 * A packet thread represents a linear packet-flow between two peers. Each
 * packet must be assigned to a packet thread and multiple threads may be used
 * on a connection to represent multiple, distinct communication contexts
 * between two peers. To create a packet thread, a connection must be acquired
 * from a transport.
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
 * Packet threads are distinguished by their identifiers. The transport
 * implementation must take care to generate unique IDs and also may implement
 * mechanisms to determine if an ID is local, i.e. has been created by the
 * transport instance. Packet Threads are considered equal if their IDs are
 * equal.
 * </p>
 * 
 * @throws IllegalStateException
 *             if this packet thread or the underlying connection/transport are
 *             not in a state where they can process packets.
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

	private final Connection connection;
	private final String id;

	/**
	 * Create a packet thread for the specified connection
	 * 
	 * @param connection
	 *            Connection this packet thread belongs to
	 * @param id
	 *            The ID of this packet thread. The id must be unique among all
	 *            packet threads and there must be a direct one-to-one matching
	 *            between packet threads and IDs.
	 * @throws NullPointerException
	 *             if the connection parameter is @code{null}
	 */
	public PacketThread(final Connection connection, final String id) {
		if (connection == null)
			throw new NullPointerException("Connection may not be null!");
		if (id == null)
			throw new NullPointerException("ID must not be null!");

		this.connection = connection;
		this.id = id;
	}

	/**
	 * Send a packet in this thread. The thread's connection is used. If this
	 * method returns without an exception, the payload could be serialized and
	 * the packet has been successfully added to the packet queue. However,
	 * errors may still occur during the delivery process, which will be
	 * announced over the Reporter framework.
	 * 
	 * @param payload
	 *            the payload to send with this message
	 * @param prority
	 *            the message priority, if supported by the transport, otherwise
	 *            this parameter may be ignored.
	 * @throws TransportException
	 *             If the connection schema is unknown, the payload cannot be
	 *             serialized or the packet could not be delivered to the send
	 *             queue.
	 * @throws IllegalStateException
	 *             if the transport is not available
	 */
	// TODO is null-payload okay?
	public final void send(final Object payload, final Packet.Priority priority)
			throws TransportException {
		final Transport transport = getConnection().getTransport();
		if (transport == null)
			throw new IllegalStateException(
					"Transport not available, connection already disposed?");

		try {
			// retrieve the schema record
			final SchemaRecord record = SchemaRegistry.getInstance().getRecord(
					getConnection().getConnectionSchema());
			if (record == null)
				// TODO use a more verbose exception, e.g.
				// UnknownTransportSchemaException or maybe
				// SerializationException
				throw new TransportException(
						"The connection uses an unknown schema!");

			// find the serializer according to the connection's format
			final SerializationProvider prov = record
					.getSerializationProvider();

			final Serializer serializer = prov == null ? null : prov
					.getSerializer(getConnection().getSerializationFormat());

			// serialize the payload
			final Object p;
			if (serializer != null)
				// may throw a SeralizationException
				p = serializer.serialize(payload);
			else
				p = payload;

			// send the serialized payload
			sendSerializedPayload(p, priority);
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
	protected abstract void sendSerializedPayload(final Object payload,
			final Packet.Priority priority) throws TransportException;

	/**
	 * Dispose the packet thread.
	 * 
	 * Incoming messages for this thread will be rejected and sending will no
	 * longer be possible. Calling {@code send} after {@code dispose} should
	 * result in an {@link IllegalStateException}.
	 */
	public abstract void dispose();

	/**
	 * Get the connection to which this thread belongs.
	 * 
	 * @return The Connection which is used for packet delivery in this thread.
	 * @throws IllegalStateException
	 *             if the packet thread does not have a valid connection
	 * 
	 */
	public final Connection getConnection() {
		return connection;
	}

	/**
	 * Get the ID of the packet thread.
	 * 
	 * The ID must be unique among all packet threads and there must be a direct
	 * one-to-one matching between packet threads and IDs, so that the ID can be
	 * used to store a reference to the packet thread.
	 * 
	 * @return The ID of this packet thread, which must not be null.
	 */
	public final String getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PacketThread other = (PacketThread) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
