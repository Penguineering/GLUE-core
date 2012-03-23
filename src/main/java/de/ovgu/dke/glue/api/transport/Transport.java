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

import de.ovgu.dke.glue.api.serialization.Serializer;

/**
 * <p>
 * A Transport represents a connection to another peer. Dependent on the
 * underlying communication method, this may either be just a place-holder for
 * the peer's address or a physical communication link. Based on a transport,
 * the application can open packet threads to send and receive message.
 * </p>
 * 
 * <p>
 * Be aware that – depending on the communication method – there may be
 * differences between the peer of a transport and the effective peer URI of the
 * communication partner, e.g. when in an XMPP communication the server routes
 * to a specific resource, while the peer only specified user and server.
 * </p>
 * 
 * <p>
 * The Transport allows to register life-cycle listeners to keep track of the
 * connection status.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
// TODO thread safety?
public interface Transport {

	/**
	 * The status of this transport.
	 */
	public static enum Status {
		/**
		 * The thread has been created, but a connection does not yet exist.
		 */
		CREATED,
		/**
		 * Connection to the peer has been established.
		 */
		CONNECTED,
		/**
		 * Serialization has been negotiated and packets can be understood by
		 * the remote peer.
		 */
		CHECKED,
		/**
		 * The transport has been closed.
		 */
		CLOSED,
		/**
		 * The transport failed, most likely due to communication errors or
		 * serialization incompatibility.
		 */
		FAILED
	}

	/**
	 * <p>
	 * Get (or, if necessary, create) the connection on this transport for a
	 * specific connection schema. A transport can hold multiple connections,
	 * however each connection is assigned a schema, which determines the
	 * default packet handler and the Serializer.
	 * </p>
	 * 
	 * <p>
	 * The transport can check the schema availability on the peer side, but
	 * does not need to. However, if the check is omitted, an error will occur
	 * on sending the first packet over the connection.
	 * </p>
	 * 
	 * @param schema
	 *            The connection schema.
	 * @return a Connection
	 * @throws TransportException
	 *             if the connection cannot be created
	 */
	public Connection getConnection(final String schema)
			throws TransportException;

	/**
	 * Get the serializer for this transport. Serializers are either negotiated
	 * using capability packets or assumed, in which case packet sending may
	 * fail due to serialization problems. A transport offers serializers for
	 * different schemas. The format is determined by the transport
	 * implementation after capabilities negotiation.
	 * 
	 * @param schema
	 *            The target schema of the serializer.
	 * @return The selected serializer or
	 *         <code>null<code> if serialization is not necessary.
	 */
	public Serializer getSerializer(final String schema);
}
