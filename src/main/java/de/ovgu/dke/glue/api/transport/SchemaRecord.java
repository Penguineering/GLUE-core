/*
 * Copyright 2012 Stefan Haun, Thomas Low, Sebastian Stober, Andreas Nürnberger
 * 
 *      Data and Knowledge Engineering Group, 
 * 		Faculty of Computer Science,
 *		Otto-von-Guericke University,
 *		Magdeburg, Germany
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ovgu.dke.glue.api.transport;

import net.jcip.annotations.Immutable;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;

/**
 * <p>
 * A schema record for middle-ware registration. This record contains a schema
 * and the corresponding packet handler factory and serialization provider.
 * </p>
 * 
 * <p>
 * This class is immutable and thread safe. However, immutablility only refers
 * to the references, i.e. the packet handler factory and serialization provider
 * could be changed, depending on their implementation, although the contract
 * states otherwise.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
@Immutable
public class SchemaRecord {
	/**
	 * Create a schema record
	 * 
	 * @param schema
	 *            The schema to be registered, which should be a valid URI and
	 *            must not be empty
	 * @param packetHandlerFactory
	 *            packet handler factory for this schema which may not be
	 *            {@code null}
	 * @param serializationProvider
	 *            serialization provider for this schema which may not be
	 *            {@code null}
	 * @return a schema record instance
	 * @throws NullPointerException
	 *             if one of the parameters is {@code null}
	 * @throws IllegalArgumentException
	 *             if the schema is an empty String.
	 */
	public static SchemaRecord valueOf(String schema,
			PacketHandlerFactory packetHandlerFactory,
			SerializationProvider serializationProvider) {

		// the above exceptions will be thrown in the constructor
		return new SchemaRecord(schema, packetHandlerFactory,
				serializationProvider);
	}

	private final String schema;
	private final PacketHandlerFactory packetHandlerFactory;
	private final SerializationProvider serializationProvider;

	/**
	 * Create a new schema record. Construction is private, use the valueOf
	 * methods for construction!
	 * 
	 * @param schema
	 *            the schema to be registered
	 * @param packetHandlerFactory
	 *            packet handler factory for this schema
	 * @param serializationProvider
	 *            serialization provider for this schema
	 * @throws NullPointerException
	 *             if one of the parameters is {@code null}
	 * @throws IllegalArgumentException
	 *             if the schema is an empty String.
	 */
	private SchemaRecord(String schema,
			PacketHandlerFactory packetHandlerFactory,
			SerializationProvider serializationProvider) {
		super();

		this.schema = schema;
		this.packetHandlerFactory = packetHandlerFactory;
		this.serializationProvider = serializationProvider;

		// throws exceptions if invariants are broken
		checkInvariants();
	}

	private void checkInvariants() {
		if (this.schema == null)
			throw new NullPointerException("Schema must be provided.");
		if (schema.isEmpty())
			throw new IllegalArgumentException("Schema may not be empty.");

		if (this.packetHandlerFactory == null)
			throw new NullPointerException(
					"Packet Handler Factory must be provided.");

		if (this.serializationProvider == null)
			throw new NullPointerException(
					"Serialization Provider must be provided.");
	}

	/**
	 * Get the schema of this record
	 * 
	 * @return record schema string; not {@code null}
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * get the packet handler factory for this record
	 * 
	 * @return the packet handler factory for this record's schema; not
	 *         {@code null}
	 */
	public PacketHandlerFactory getPacketHandlerFactory() {
		return packetHandlerFactory;
	}

	/**
	 * get the serialization provider for this record
	 * 
	 * @return the serialization provider for this record's schema; not
	 *         {@code null}
	 */
	public SerializationProvider getSerializationProvider() {
		return serializationProvider;
	}
}
