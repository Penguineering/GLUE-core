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

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.ovgu.dke.glue.api.serialization.SerializationProvider;

import net.jcip.annotations.ThreadSafe;

/**
 * <p>
 * Schema registry for middle-ware registration, containing the known schemas
 * and their corresponding packet handler factories and serialization providers
 * in the form of schema records.
 * </p>
 * 
 * <p>
 * If you want to add your own middle-ware implementation, its schema must be
 * registered here.
 * </p>
 * 
 * <p>
 * This class is thread safe.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
@ThreadSafe
public class SchemaRegistry {
	private static SchemaRegistry instance = null;

	/**
	 * Get the instance of the schema registry.
	 * 
	 * @return The instance of the schema registry.
	 */
	public static final synchronized SchemaRegistry getInstance() {
		if (instance == null)
			instance = new SchemaRegistry();

		return instance;
	}

	private final Map<String, SchemaRecord> registry;

	private SchemaRegistry() {
		// do not allow public instantiation
		registry = new ConcurrentHashMap<String, SchemaRecord>();
	}

	/**
	 * Register a schema, overwrites existing schema records.
	 * 
	 * @param record
	 *            The schema record to register, which may not be {@code null}
	 * @throws NullPointerException
	 *             if the record is {@code null}
	 */
	public void registerSchemaRecord(final SchemaRecord record) {
		registry.put(record.getSchema(), record);
	}

	/**
	 * Get all available schemas.
	 * 
	 * @return a unmodifiable Set of registered schemas.
	 */
	public Set<String> getAvailableSchemas() {
		return Collections.unmodifiableSet(registry.keySet());
	}

	/**
	 * Get the record for a specific schema.
	 * 
	 * @param schema
	 *            The record's schema.
	 * @return The schema record or {@code null} if the schema is not available.
	 * @throws NullPointerException
	 *             if the schema parameter is {@code null}
	 */
	public SchemaRecord getRecord(final String schema) {
		return registry.get(schema);
	}

	/**
	 * Get the packet handler factory for a specific schema.
	 * 
	 * @param schema
	 *            The record's schema.
	 * @return A packet handler factory or {@code null} if the schema is not
	 *         registered or the record does not contain a packet handler
	 *         factory (the latter, however, should never happen)
	 * @throws NullPointerException
	 *             if the schema parameter is {@code null}
	 */
	public PacketHandlerFactory getPacketHandlerFactory(final String schema) {
		final SchemaRecord record = getRecord(schema);
		return (record == null) ? null : record.getPacketHandlerFactory();
	}

	/**
	 * Get the serialization provider for a specific schema.
	 * 
	 * @param schema
	 *            The record's schema.
	 * @return A packet handler factory or <code>null</code> if the schema is
	 *         not registered or the record does not contain a serialization
	 *         provider (the latter, however, should never happen).
	 * @throws NullPointerException
	 *             if the schema parameter is {@code null}
	 */
	public SerializationProvider getSerializationProvider(final String schema) {
		final SchemaRecord record = getRecord(schema);
		return (record == null) ? null : record.getSerializationProvider();
	}
}
