package de.ovgu.dke.glue.vm;

import de.ovgu.dke.glue.api.transport.LifecycleListener;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketHandlerFactory;
import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.Transport;

public class VMTransport implements Transport {
	
	final PacketHandlerFactory packetHandlerFactory;
	
	public VMTransport(final PacketHandlerFactory packetHandlerFactory) {
		super();
		this.packetHandlerFactory = packetHandlerFactory;
	}

	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PacketThread createThread(final PacketHandler handler) {
		return new VMPacketThread(
				handler, 
				packetHandlerFactory.createPacketHandler());		
	}

	@Override
	public void setDefaultPackerHandler(PacketHandler handler) {
		// do we really need this? No ;-)		
	}

}
