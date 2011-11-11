package de.ovgu.dke.glue.api.transport;

/**
 * <p>
 * Listener interface for status changes in a transport's life-cycle.
 * </p>
 * 
 * <p>
 * Listeners are invoked in the caller's thread context and should be aware of
 * thread boundaries and must take care of the necessary synchronization
 * themselves!
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
// TODO in die TransportFactory
// TODO auch für Transports
// TODO AbstractTransportFactory, die die listener korrekt aufruft
public interface LifecycleListener {
	/**
	 * Called when the connection status of a transport changes. See the
	 * Transport interface for possible states.
	 * 
	 * @param transport
	 *            The transport which changed its status.
	 * @param oldStatus
	 *            The old status.
	 * @param newStatus
	 *            The new status.
	 */
	public void onStatusChange(Transport transport, Transport.Status oldStatus,
			Transport.Status newStatus);
	
	// onThreadCreate(PacketThread)
	// onThreadDispose(PacketThread)

}
