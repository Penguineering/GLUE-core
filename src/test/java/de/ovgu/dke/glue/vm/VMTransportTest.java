package de.ovgu.dke.glue.vm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ovgu.dke.glue.api.transport.Packet;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketHandlerFactory;
import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.TransportException;
import de.ovgu.dke.glue.util.transport.AsyncPackageHandlerFactory;

public class VMTransportTest {
	
	protected static Log log = LogFactory.getLog(VMTransportTest.class); 
	
	public static void main(String[] args) throws TransportException {

		// simple handler for server
		final PacketHandler serverHandler = new PacketHandler() {			
			@Override
			public void handle(PacketThread packetThread, Packet packet)
					throws TransportException {
				log.debug("server received: "+packetThread+" : "+packet);
				packetThread.send("Hello Client!", null);
			}
		};
		
		// simple dummy-factory
		PacketHandlerFactory serverHandlerFactory = new PacketHandlerFactory() {			
			@Override
			public PacketHandler createPacketHandler() {
				return serverHandler;
			}
		};
		// -> direct call (no extra threads)
		
		final ExecutorService executor = Executors.newSingleThreadExecutor();
		serverHandlerFactory = new AsyncPackageHandlerFactory(
				serverHandlerFactory, executor);
		// -> async call (in other thread) 
		
		final VMTransport transport = new VMTransport(serverHandlerFactory);
		
		final PacketHandler clientHandler = new PacketHandler() {			
			@Override
			public void handle(PacketThread packetThread, Packet packet)
					throws TransportException {
				log.debug("client received: "+packetThread+" : "+packet);				
			}
		};
		
		final PacketThread thread = transport.createThread(clientHandler);
		
		thread.send("Hello Server!", null);
		
		// wait a little for answer (from other thread) before closing
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		
		thread.dispose();
		executor.shutdown();
		
		thread.send("message after dispose()", null);
		
	}

}
