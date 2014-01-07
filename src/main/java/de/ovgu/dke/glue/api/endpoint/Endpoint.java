package de.ovgu.dke.glue.api.endpoint;

import java.net.URI;
import java.util.NoSuchElementException;

import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.TransportException;

/**
 * Communication endpoint for a software component using GLUE.
 * 
 * <p>
 * The end-point hides the specific wiring of GLUE peers to the software using
 * the communication channel.
 * </p>
 * 
 * <p>
 * An end-point may be connected to several transport factories, thus allowing to
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
	 * Open a packet thread to the specified peer.
	 * <p>
	 * If a packet thread cannot be opened, the {@link NoSuchElementException}
	 * is thrown.
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
	public PacketThread openPacketThread(URI peer) throws TransportException;
}
