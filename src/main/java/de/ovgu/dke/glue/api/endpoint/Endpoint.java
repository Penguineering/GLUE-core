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
package de.ovgu.dke.glue.api.endpoint;

import java.net.URI;
import java.util.NoSuchElementException;

import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.transport.Connection;
import de.ovgu.dke.glue.api.transport.PacketHandlerFactory;
import de.ovgu.dke.glue.api.transport.TransportException;
import de.ovgu.dke.glue.api.transport.TransportFactory;

/**
 * Communication end-point for a software component using GLUE.
 * 
 * <p>
 * The end-point hides the specific wiring of GLUE peers to the software using
 * the communication channel.
 * </p>
 * 
 * <p>
 * An end-point may be connected to several transport factories, thus allowing
 * to use different channels. Based on the peer URI the actual communication
 * channel is selected.
 * </p>
 * 
 * <p>
 * To enable incoming connections (i.e. packet threads which are initiated by
 * another end-point) registration as an in-bound end-point with the
 * {@link TransportFactory} is necessary.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 */
public interface Endpoint {
	/**
	 * The identifier for this end-point, which is used to assign a configured
	 * end-point to a software component.
	 * 
	 * @return A non-null string containing the end-point's identifier.
	 */
	public String getId();

	/**
	 * Get the schema of this end-point.
	 * 
	 * @return The schema supported by this MiddleWare.
	 */
	public String getSchema();

	/**
	 * Get the packet handler factory for this end-point.
	 * 
	 * @return the packet handler factory for this end-point; not {@code null}
	 */
	public PacketHandlerFactory getPacketHandlerFactory();

	/**
	 * get the serialization provider for this end-point.
	 * 
	 * @return the serialization provider for this end-point; not {@code null}
	 */

	public SerializationProvider getSerializationProvider();

	/**
	 * Open a connection to the specified peer.
	 * <p>
	 * If a connection cannot be opened, the {@link NoSuchElementException} is
	 * thrown.
	 * <p>
	 * 
	 * @param peer
	 *            The target peer. A channel is selected based on the peer URI.
	 * @return An established packet thread
	 * @throws NoSuchElementException
	 *             if a matching connection cannot be created.
	 * @throws TransportException
	 *             on internal transport errors.
	 * @throws NullPointerException
	 *             if one the URI is <code>null</code>.
	 */
	public Connection openConnection(URI peer) throws TransportException;

	/**
	 * Register a transport factory to this end-point, allowing to use the
	 * provided transport as out-bound channel for the dispatcher.
	 * 
	 * @param factory
	 *            The transport factory to be added to the end-point.
	 * @throws NullPointerException
	 *             if the factory argument is <code>null</code>.
	 */
	public void registerTransportFactory(TransportFactory factory);

}
