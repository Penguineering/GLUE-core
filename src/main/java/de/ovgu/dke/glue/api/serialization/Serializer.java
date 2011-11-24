package de.ovgu.dke.glue.api.serialization;

/**
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
// not thread safe
public interface Serializer {
	public Object serialize(Object o) throws SerializationException;
	public Object deserialize(Object o) throws SerializationException;
	
	public String getSchema();
}
