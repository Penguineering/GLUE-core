package de.ovgu.dke.glue.api.transport;

import net.jcip.annotations.NotThreadSafe;

// not thread safe, use thread containment
@NotThreadSafe
public interface PacketThread {

	public void dispose();

	public void send(final Object payload, final Packet.Priority prority)
			throws TransportException;

}
