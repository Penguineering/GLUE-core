package de.ovgu.dke.glue.vm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ovgu.dke.glue.api.transport.Packet;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.TransportException;

class VMPacketThread implements PacketThread {
	
	protected static Log log = LogFactory.getLog(VMPacketThread.class);

	PacketHandler clientHandler;
	PacketHandler serverHandler;
	
	final PacketThread reverse;
	
	VMPacketThread(PacketHandler clientHandler,
			PacketHandler serverHandler) {
		
		super();
		this.clientHandler = clientHandler;
		this.serverHandler = serverHandler;
		
		this.reverse = new PacketThread() {			
			@Override
			public void send(Packet packet) throws TransportException {
				sendBack(packet);
			}
	
			@Override
			public void dispose() {
				removeReferences();
			}
		};
	}
	
	private void removeReferences() {
		// remove references to allow garbage collection
		clientHandler = ClosedPacketHandler.instance();
		serverHandler = ClosedPacketHandler.instance();	
		log.debug("PacketThread closed.");
	}

	@Override
	public void dispose() {
		removeReferences();
	}

	@Override
	public void send(final Packet packet) throws TransportException {
		serverHandler.handle(reverse, packet);
	}
	
	// back channel
	protected void sendBack(Packet packet) throws TransportException {
		clientHandler.handle(this, packet);
	}

}
