/*
 * Copyright 2012-2014 Stefan Haun, Thomas Low, Sebastian Stober, Andreas Nürnberger
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

import java.net.URI;

import net.jcip.annotations.ThreadSafe;
import de.ovgu.dke.glue.api.endpoint.Endpoint;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;

/**
 * Abstract base class representing the virtual connection between two matching
 * middle-ware implementations on different clients.
 * 
 * <p>
 * Serializers are bound to a connection, i.e. each connection may have a
 * different serialization schema (which must be assigned upon creation),
 * however, the serializer must be available in the Transport via the
 * SerializationProvider.
 * </p>
 * 
 * <p>
 * Each Connection is bound to a specific end-point.
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
public interface Connection {
	/**
	 * Get the end-point to which this Connection is bound.
	 * 
	 * @return The end-point for this Connection.
	 */
	public Endpoint getEndpoint();

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
