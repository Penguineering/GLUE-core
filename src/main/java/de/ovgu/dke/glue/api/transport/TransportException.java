package de.ovgu.dke.glue.api.transport;

import de.ovgu.dke.glue.api.GlueException;
import net.jcip.annotations.Immutable;

/**
 * <p>
 * Generic exception covering problems with the transport layer.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
@Immutable
public class TransportException extends GlueException {
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
