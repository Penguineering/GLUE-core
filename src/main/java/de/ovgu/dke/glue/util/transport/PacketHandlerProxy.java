package de.ovgu.dke.glue.util.transport;

import de.ovgu.dke.glue.api.transport.Packet;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketThread;

public class PacketHandlerProxy implements PacketHandler {

	private PacketHandler handler;

	public PacketHandlerProxy() {
		this(ClosedPacketHandler.instance());
	}

	public PacketHandlerProxy(PacketHandler handler) {
		super();
		this.handler = handler;
	}

	public void setHandler(PacketHandler handler) {
		this.handler = handler;
	}

	public PacketHandler getHandler() {
		return handler;
	}

	@Override
	public void handle(PacketThread packetThread, Packet packet) {
		handler.handle(packetThread, packet);
	}

}
