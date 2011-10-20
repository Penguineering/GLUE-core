package de.ovgu.dke.glue.api.serialization;

public interface Serializer {
	public Object serialize(Object o) throws SerializationException;
	public Object deserialize(Object o) throws SerializationException;
}
