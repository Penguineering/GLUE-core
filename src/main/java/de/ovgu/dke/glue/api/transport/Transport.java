package de.ovgu.dke.glue.api.transport;

public interface Transport {
	
	public static enum Status {
		// TODO
	}

	public void addLifecycleListener(LifecycleListener listener);
	
	public void removeLifecycleListener(LifecycleListener listener);
	
	public PacketThread createThread(PacketHandler handler);
	
	public void setDefaultPackerHandler(PacketHandler handler);
	
}
