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

	/**
	 * Get the serializer for this transport. Serializers are either negotiated
	 * using capability packets or assumed, in which case packet sending may
	 * fail due to serialization problems.
	 * 
	 * @return The selected serializer or
	 *         <code>null<code> if serialization is not necessary.
	 */
	public Serializer getSerializer();

	/**
	 * <p>
	 * Check whether the peers in a transport are able to communicate with each
	 * other, i.e. they have matching serialization methods and schemas.
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
	 */
	public boolean checkCapabilities() throws TransportException;
}
