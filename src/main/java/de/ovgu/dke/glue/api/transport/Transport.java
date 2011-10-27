package de.ovgu.dke.glue.api.transport;

import de.ovgu.dke.glue.api.reporting.Reporter;

public interface Transport extends Reporter {
	
	public static enum Status {
		// TODO
	}

	public void addLifecycleListener(LifecycleListener listener);
	
	public void removeLifecycleListener(LifecycleListener listener);
	
	/**
	 * 
	 * @param handler may not be <code>null</code>
	 * @return
	 * @throws TransportException
	 */
	public PacketThread createThread(PacketHandler handler) throws TransportException;
	
	public void setDefaultPackerHandler(PacketHandler handler);
	
}
