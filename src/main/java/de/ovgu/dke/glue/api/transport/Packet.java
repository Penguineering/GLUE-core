package de.ovgu.dke.glue.api.transport;

/**
 * Implementations of this interface have to be kept immutable regarding the
 * interface methods, as there is no further synchronization in the transport
 * layer. It is best not to change a packet after it has been handed to a
 * transport and a transport will not change a packet after calling the packet
 * handler.
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

	Object getPayload();

	Priority getPriority();

	Object getAttribute(String key);
}
