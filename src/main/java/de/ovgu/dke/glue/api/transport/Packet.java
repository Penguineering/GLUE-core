package de.ovgu.dke.glue.api.transport;

/**
 * <p>
 * The Packet represents a single data-gram sent over a transport via the packet
 * thread. Each packet has a pay-load — the actual data – and a priority, which
 * allows packets to be sent first, such as status messages, or be deferred.
 * Further implementation dependent attributes may be stored and can be
 * retrieved if the key is known. However, this makes an application depending
 * on the communication method and underlying implementation!
 * </p>
 * 
 * <p>
 * Implementations of this interface have to be kept immutable regarding the
 * interface methods, as there is no further synchronization in the transport
 * layer. It is best not to change a packet after it has been handed to a
 * transport and a transport will not change a packet after calling the packet
 * handler.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
public interface Packet {

	/**
	 * The packet priority. An implementation may choose different queues or
	 * sending methods depending on the priority.
	 */
	public static enum Priority {
		/**
		 * Default priority; one of the following as set in the transport or
		 * packet tread implementation.
		 */
		DEFAULT,
		/**
		 * High-priority packet – try to route this packet first.
		 */
		HIGH,
		/**
		 * Normal priority, send packet in the standard packet stream.
		 */
		NORMAL,
		/**
		 * Packet may be deferred, i.e. sent off-line or much later. However,
		 * timeouts may still apply.
		 */
		DEFERRABLE;
	}

	/**
	 * Get the pay-load of this packet. There is no further specification about
	 * what the packet must contain.
	 * 
	 * @return The packet's pay-load or <code>null</code> if the packet did not
	 *         contain any.
	 */
	Object getPayload();

	/**
	 * Get the packet's priority.
	 * 
	 * @return The priority of this packet.
	 */
	Priority getPriority();

	/**
	 * Get an (implementation-dependent) attribute for this packet. Note that
	 * this makes an application depending on the communication method and
	 * underlying implementation!
	 * 
	 * @param key
	 *            The key of the attribute which shall be retrieved.
	 * @return The attribute value or <code>null</code> if there is no value for
	 *         the key.
	 */
	Object getAttribute(String key);
}
