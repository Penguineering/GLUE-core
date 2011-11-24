package de.ovgu.dke.glue.api.serialization;

import java.util.Set;

public interface SerializationProvider {
	public static final String JAVA = "java";
	public static final String XML = "xml";
	public static final String STRING = "string";
	public static final String BINARY = "binary";

	public Set<String> availableFormats();

	public Set<String> getSchemas(String format);

	public Serializer getSerializer(String format, String schema)
			throws SerializationException;

}
