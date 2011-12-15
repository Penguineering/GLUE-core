package de.ovgu.dke.glue.api.serialization;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
// TODO: Javadoc
@NotThreadSafe
public interface Serializer {
	
	public Object serialize(Object o) throws SerializationException;
	public Object deserialize(Object o) throws SerializationException;
	
	public String getSchema();
	public String getFormat();
}
