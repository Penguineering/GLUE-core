package de.ovgu.dke.glue.vm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ovgu.dke.glue.api.transport.Packet;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.TransportException;

public class VMTransportTest {
	
	protected static Log log = LogFactory.getLog(VMTransportTest.class); 
	
	public static void main(String[] args) throws TransportException {

		final PacketHandler serverHandler = new PacketHandler() {			
			@Override
			public void handle(PacketThread packetThread, Packet packet)
					throws TransportException {
				log.debug("server received: "+packetThread+" : "+packet);	
				packetThread.send(new PacketImpl("Hello Client!"));
			}
		};
		
		final VMTransport transport = new VMTransport(serverHandler);
		
		final PacketHandler clientHandler = new PacketHandler() {			
			@Override
			public void handle(PacketThread packetThread, Packet packet)
					throws TransportException {
				log.debug("client received: "+packetThread+" : "+packet);				
			}
		};
		
		final PacketThread thread = transport.createThread(clientHandler);
		
		thread.send(new PacketImpl("Hello Server!"));
		thread.dispose();
		
		thread.send(new PacketImpl("message after dispose()"));
		
	}

}
