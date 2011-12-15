package de.ovgu.dke.glue.api.serialization;

import de.ovgu.dke.glue.api.GlueException;
import net.jcip.annotations.Immutable;

/**
 * 
 * <p>
 * Generic exception covering problems with the serialization.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
@Immutable
public class SerializationException extends GlueException {
	private static final long serialVersionUID = 4621980971205449774L;

	public SerializationException() {
	}

	public SerializationException(String msg) {
		super(msg);
	}

	public SerializationException(Throwable cause) {
		super(cause);
	}

	public SerializationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
