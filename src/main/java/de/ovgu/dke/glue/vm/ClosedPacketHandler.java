package de.ovgu.dke.glue.vm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ovgu.dke.glue.api.transport.Packet;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.TransportException;

/**
 * design-pattern: null-object (behavior) 
 * singleton, stateless
 * 
 * Just swallows all the packets (does nothing).
 * Can be used as place holder (for a connection to be established)
 * or to replace the reference to a just closed real handler.
 * 
 * @author stober
 *
 */
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
