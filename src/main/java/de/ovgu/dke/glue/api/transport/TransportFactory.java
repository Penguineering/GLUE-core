package de.ovgu.dke.glue.api.transport;

import java.net.URI;
import java.util.Properties;

import net.jcip.annotations.ThreadSafe;
import de.ovgu.dke.glue.api.reporting.Reporter;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;

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
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
@ThreadSafe
public interface TransportFactory extends Reporter {
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

	/**
	 * Set the default packet handler factory, which is called whenever a
	 * transport is created.
	 * 
	 * @param factory
	 *            The packet handler factory.
	 * @throws TransportException
	 *             If the factory cannot be set.
	 */
	public void setDefaultPacketHandlerFactory(PacketHandlerFactory factory)
			throws TransportException;

	/**
	 * Set the serialization provider for transports.
	 * 
	 * @param provider
	 *            The serialization provider.
	 * @throws TransportException
	 *             If the serialization provider cannot be set.
	 */
	public void setSerializationProvider(final SerializationProvider provider)
			throws TransportException;

	/**
	 * Get the default key for this registry. While the user is allowed to use a
	 * different key, this one is used for generic initialization (such was in
	 * the TransportRegistry) or if the user does not intent to use multiple
	 * instances of a transport implementation.
	 * 
	 * @return The default key for the transport factory implementation.
	 */
	public String getDefaultRegistryKey();

	/**
	 * Initialize the transport factory and bring it to a state where packets
	 * can be sent and received.
	 * 
	 * @param config
	 *            Implementation specific configuration, may be
	 *            <code>null</code>.
	 * @throws TransportException
	 *             if the initialization fails.
	 */
	public void init(final Properties config) throws TransportException;

	/**
	 * Dispose the transport factory and free all resources.
	 */
	public void dispose();

	/**
	 * Add a life-cycle listener to this transport.
	 * 
	 * @param listener
	 *            The life-cycle listener to add.
	 */
	public void addTransportLifecycleListener(
			TransportLifecycleListener listener);

	/**
	 * Remove a life-cycle listener from this report.
	 * 
	 * @param listener
	 *            The life-cycle listener to remove.
	 */
	public void removeTransportLifecycleListener(
			TransportLifecycleListener listener);

}
