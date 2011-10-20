package de.ovgu.dke.glue.api.transport;

public interface LifecycleListener {

	// public void onClose(Transport closed);

	public void onStatusChange(Transport transport, Transport.Status oldStatus,
			Transport.Status newStatus);

}
