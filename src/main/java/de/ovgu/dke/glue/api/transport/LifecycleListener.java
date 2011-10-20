package de.ovgu.dke.glue.api.transport;

// be aware of thread boundaries!
public interface LifecycleListener {

	// public void onClose(Transport closed);

	public void onStatusChange(Transport transport, Transport.Status oldStatus,
			Transport.Status newStatus);

}
