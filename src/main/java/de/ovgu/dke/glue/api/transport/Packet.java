package de.ovgu.dke.glue.api.transport;

// keep immutable! no further synchronization in transport layer
public interface Packet {
	
	public static enum Priority {
		HIGH,
		NORMAL,
		DEFERRABLE;
		
		//TODO DEFAULT als extrawert
		public static final Priority DEFAULT = Priority.NORMAL;
	}
	
	Object getPayload();
	
	Priority getPriority();
	
	Object getAttribute(String key);

}
