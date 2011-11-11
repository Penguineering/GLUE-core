package de.ovgu.dke.glue.vm;

import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketHandlerFactory;
import de.ovgu.dke.glue.api.transport.PacketThread;
import de.ovgu.dke.glue.api.transport.Transport;
import de.ovgu.dke.glue.api.transport.TransportException;

/**
 * 
 * @author Sebastian Stober (sebastian.stober@ovgu.de)
 *
 */
public class VMTransport implements Transport {

	final PacketHandlerFactory packetHandlerFactory;

	public VMTransport(final PacketHandlerFactory packetHandlerFactory) {
		super();
		this.packetHandlerFactory = packetHandlerFactory;
	}


	@Override
	public PacketThread createThread(final PacketHandler handler)
			throws TransportException {
		try {
			return new VMPacketThread(this, handler, packetHandlerFactory
					.createPacketHandler());
		} catch (InstantiationException e) {
			throw new TransportException("Unable to create packet handler: "
					+ e.getMessage(), e);
		}
	}
}
