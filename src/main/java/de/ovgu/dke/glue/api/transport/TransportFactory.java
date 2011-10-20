package de.ovgu.dke.glue.api.transport;

import java.net.URI;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public interface TransportFactory {
	public Transport createTransport(URI peer) throws TransportException;
}
