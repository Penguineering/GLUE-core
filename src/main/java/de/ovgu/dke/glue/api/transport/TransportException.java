package de.ovgu.dke.glue.api.transport;

import net.jcip.annotations.Immutable;

@Immutable
public class TransportException extends Exception {
	private static final long serialVersionUID = 1095168616944463901L;

	public TransportException() {
	}

	public TransportException(String msg) {
		super(msg);
	}

	public TransportException(Throwable cause) {
		super(cause);
	}

	public TransportException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
