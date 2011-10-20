package de.ovgu.dke.glue.vm;

import de.ovgu.dke.glue.api.transport.Packet;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.TransportException;

public class PacketHandlerProxy implements PacketHandler {
	
	PacketHandler handler;

	public PacketHandlerProxy() {
		this(ClosedPacketHandler.instance());
	}
	
	public PacketHandlerProxy(PacketHandler handler) {
		super();
		this.handler = handler;
	}
	
	public void setPacketHandler(PacketHandler handler) {
		this.handler = handler;
	}
	
	public PacketHandler getHandler() {
		return handler;
	}

	@Override
	public void handle(PacketThread packetThread, Packet packet)
			throws TransportException {
		handler.handle(packetThread, packet);		
	}

}
