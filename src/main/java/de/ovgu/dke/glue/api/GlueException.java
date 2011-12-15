package de.ovgu.dke.glue.api;

import net.jcip.annotations.Immutable;

/**
 * Base for all GLUE exceptions. If you catch these, you get them all.
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
@Immutable
public class GlueException extends Exception {
	private static final long serialVersionUID = 3599405566748755982L;

	public GlueException() {
	}

	public GlueException(String message) {
		super(message);
	}

	public GlueException(Throwable cause) {
		super(cause);
	}

	public GlueException(String message, Throwable cause) {
		super(message, cause);
	}

}
