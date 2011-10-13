package de.ovgu.dke.glue.vm;

import de.ovgu.dke.glue.api.transport.Packet;

public class PacketImpl implements Packet {
	
	final Object payload;

	public PacketImpl(Object payload) {
		super();
		this.payload = payload;
	}

	@Override
	public Object getPayload() {
		return payload;
	}

	@Override
	public Priority getPriority() {
		return Priority.DEFAULT;
	}

	@Override
	public Object getAttribute(String key) {
		return null;
	}
	
	@Override
	public String toString() {
		return (payload != null) ? payload.toString() : "null";
	}

}
