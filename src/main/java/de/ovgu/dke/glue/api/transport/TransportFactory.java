package de.ovgu.dke.glue.api.transport;

import java.net.URI;

import net.jcip.annotations.ThreadSafe;

/**
 * <p>
 * The transport factory allows to acquire a transport to a peer denoted by its
 * URI. Transport acquisition is preliminary to getting a packet thread and
 * sending packets to a peer.
 * </p>
 * <p>
 * The peer URI format depends on the transport implementation.
 * </p>
 * 
 * <p>
 * This class is thread safe.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
@ThreadSafe
public interface TransportFactory {
	/**
	 * <p>
	 * Create a transport to the denoted peer or retrieve it from a storage of
	 * existing transports, depending on the implementation.
	 * </p>
	 * <p>
	 * A transport exception is thrown if
	 * <ul>
	 * <li>the peer cannot be recognized,</li>
	 * <li>the transport cannot be acquired,</li>
	 * <li>the connection cannot be established,</li>
	 * <li>or an implementation-dependent error occurs.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param peer
	 *            The peer end-point of the requested transport.
	 * @return A transport implementation which will send packets to the denoted
	 *         peer.
	 * @throws TransportException
	 *             if something goes wrong, see above description for possible
	 *             reasons.
	 * 
	 */
	public Transport createTransport(URI peer) throws TransportException;

	public void setDefaultPacketHandlerFactory(PacketHandlerFactory factory)
			throws TransportException;

	public String getDefaultRegistryKey();

	public void init() throws TransportException;

	public void dispose();
}
