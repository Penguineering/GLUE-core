package de.ovgu.dke.glue.api.transport;

import net.jcip.annotations.NotThreadSafe;

// not thread safe, use thread containment
@NotThreadSafe
public interface PacketThread {

	public void dispose();
	
	// TODO parameter: +payload, +prio, -packet
	public void send(Packet packet) throws TransportException;
	
	
}
