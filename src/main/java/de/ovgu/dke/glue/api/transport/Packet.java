package de.ovgu.dke.glue.api.transport;

public interface Packet {
	
	public static enum Priority {
		HIGH,
		NORMAL,
		DEFERRABLE;
		
		public static final Priority DEFAULT = Priority.NORMAL;
	}
	
	Object getPayload();
	
	Priority getPriority();
	
	Object getAttribute(String key);

}
