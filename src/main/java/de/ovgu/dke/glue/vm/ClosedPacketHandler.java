package de.ovgu.dke.glue.vm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ovgu.dke.glue.api.transport.Packet;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.TransportException;

public class ClosedPacketHandler implements PacketHandler {
	
	protected static Log log = LogFactory.getLog(ClosedPacketHandler.class); 
	
	private final static ClosedPacketHandler instance = new ClosedPacketHandler();
	
	public static ClosedPacketHandler instance() {
		return instance;
	}
	
	private ClosedPacketHandler() {
		// nah!
	}

	@Override
	public void handle(PacketThread packetThread, Packet packet)
			throws TransportException {
		// ignored		
		log.warn("PacketThread closed. Message not sent.");
	}

}
