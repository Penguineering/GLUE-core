package de.ovgu.dke.glue.api.transport;

import de.ovgu.dke.glue.api.reporting.Reporter;

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
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
// TODO thread safety?
public interface Transport extends Reporter {

	/**
	 * The status of this transport.
	 */
	public static enum Status {
		// TODO find sensible status values
	}

	/**
	 * Add a life-cycle listener to this transport.
	 * 
	 * @param listener
	 *            The life-cycle listener to add.
	 */
	public void addLifecycleListener(LifecycleListener listener);

	/**
	 * Remove a life-cycle listener from this report.
	 * 
	 * @param listener
	 *            The life-cycle listener to remove.
	 */
	public void removeLifecycleListener(LifecycleListener listener);

	/**
	 * <p>
	 * Create a packet thread to send and receive packets from the peer of this
	 * transport.
	 * </p>
	 * 
	 * @param handler
	 *            The packet handler to use for incoming packets. Set to
	 *            <code>null</code> or <code>PacketThread.DEFAULT_HANDLER</code>
	 *            to use the factory default handler.
	 * @return A new packet thread.
	 * @throws TransportException
	 *             if the thread could not be created.
	 */
	public PacketThread createThread(PacketHandler handler)
			throws TransportException;

	// TODO das eher in die TransportFactory?
	// TODO mit transport exception 
	public void setDefaultPackerHandler(PacketHandler handler);

}
