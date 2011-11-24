package de.ovgu.dke.glue.vm;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ovgu.dke.glue.api.transport.Packet;
import de.ovgu.dke.glue.api.transport.Packet.Priority;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.Transport;
import de.ovgu.dke.glue.api.transport.TransportException;
import de.ovgu.dke.glue.util.transport.ClosedPacketHandler;

/**
 * 
 * @author Sebastian Stober (sebastian.stober@ovgu.de)
 *
 */
class VMPacketThread extends PacketThread {
	
	protected static Log log = LogFactory.getLog(VMPacketThread.class);

	PacketHandler clientHandler;
	PacketHandler serverHandler;

	final PacketThread reverse;
	final VMTransport transport;
	
	VMPacketThread(final VMTransport transport, PacketHandler clientHandler,
			PacketHandler serverHandler) {
		
		super();
		this.transport = transport;
		this.clientHandler = clientHandler;
		this.serverHandler = serverHandler;
		
		this.reverse = new PacketThread() {
			
			@Override
			public void sendSerializedPayload(Object payload, Priority prority)
					throws TransportException {
				sendBack(new VMPacket(payload));
			}

			@Override
			public void dispose() {
				removeReferences();
			}

			@Override
			public Transport getTransport() {
				return transport;
			}

			@Override
			public URI getPeer() {
				return null;
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
	
	// back channel
	protected void sendBack(Packet packet) throws TransportException {
		clientHandler.handle(this, packet);
	}

	@Override
	public void sendSerializedPayload(Object payload, Priority prority)
			throws TransportException {
		serverHandler.handle(reverse, new VMPacket(payload));
	}

	@Override
	public Transport getTransport() {
		return transport;
	}

	@Override
	public URI getPeer() {
		return null;
	}

}
