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
	 * provided transport as out-bound channels for the dispatcher.
	 * 
	 * @param factory
	 *            The transport factory to be added to the end-point.
	 * @throws NullPointerException
	 *             if the factory argument is <code>null</code>.
	 */

	public void registerTransportFactory(TransportFactory factory);

}
