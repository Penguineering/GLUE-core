package de.ovgu.dke.glue.util.transport;

import de.ovgu.dke.glue.api.transport.Packet;

/**
 * Abstract implementation of the Packet interface containing pay-load and
 * priority. Can be used as base for any packet implementation.
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
public abstract class AbstractPacket implements Packet {
	private final Object payload;
	private final Packet.Priority priority;

	public AbstractPacket(final Object payload, final Packet.Priority priority) {
		this.payload = payload;
		this.priority = priority;
	}

	@Override
	public Object getPayload() {
		return payload;
	}

	@Override
	public Priority getPriority() {
		return priority;
	}

	@Override
	public Object getAttribute(String key) {
		return null;
	}

}
