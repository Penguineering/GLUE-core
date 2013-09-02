package de.ovgu.dke.glue.api.transport;

import java.net.URI;
import java.util.NoSuchElementException;

/**
 * Communication endpoint for a software component using GLUE.
 * 
 * <p>
 * The endpoint hides the specific wiring of GLUE peers to the software using
 * the communication channel.
 * </p>
 * 
 * <p>
 * An endpoint may be connected to several transport factories, thus allowing to
 * use different channels. Based on the peer URI the actual communication
 * channel is selected.
 * </p>
 * 
 * <p>
 * This is a client-side API interface, i.e. only methods for communication are
 * exposed.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 */
public interface Endpoint {
	/**
	 * The identifier for this endpoint, which is used to assign a configured
	 * endpoint to a software component.
	 * 
	 * @return A non-null string containing the endpoint's identifier.
	 */
	public String getId();

	/**
	 * Get a connection to the specified peer, using the specified schema.
	 * <p>
	 * If a connection cannot be created, the {@link NoSuchElementException} is
	 * thrown.
	 * <p>
	 * 
	 * @param peer
	 *            The target peer. A channel is selected based on the peer URI.
	 * @param schema
	 *            The communication schema to connect to the right peer
	 *            middle-ware.
	 * @return A connection with an established channel.
	 * @throws NoSuchElementException
	 *             if a matching connection cannot be created.
	 * @throws TransportException
	 *             on internal transport errors.
	 * @throws NullPointerException
	 *             if one of the parameters is <code>null</code>.
	 */
	public Connection getConnection(URI peer, String schema)
			throws TransportException;
}
