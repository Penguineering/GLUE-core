package de.ovgu.dke.glue.api.transport;

// keep immutable! no further synchronization in transport layer
public interface Packet {

	public static enum Priority {
		/**
		 * Default priority; one of the following as set in the transport or
		 * packet tread implementation.
		 */
		DEFAULT, HIGH, NORMAL, DEFERRABLE;
	}

	Object getPayload();

	Priority getPriority();

	Object getAttribute(String key);
}
