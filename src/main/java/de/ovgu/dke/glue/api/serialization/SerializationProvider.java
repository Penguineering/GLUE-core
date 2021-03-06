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
package de.ovgu.dke.glue.api.serialization;

import java.util.List;

import net.jcip.annotations.Immutable;

/**
 * <p>
 * Interface to provide serializers for different serialization methods. The
 * target types aim at the way data is transmitted by the transport. The
 * interpretation itself must be denoted by the schema.
 * </p>
 * <p>
 * In order to check transport compatibility, the different serialization
 * methods and schemes are compared. Only if there is a matching pair the peers
 * can communicate with each other.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
@Immutable
public interface SerializationProvider {
	/**
	 * Serialization uses Java Objects, which are transferred by reference.
	 */
	public static final String JAVA = "java";
	/**
	 * Serialization uses Java Serializable Objects. The serialized object will
	 * be casted to <code>java.lang.Serializable</code>.
	 */
	public static final String SERIALIZABLE = "serializable";
	/**
	 * The serialized object will be casted to <code>…</code>.
	 */
	// TODO which XML base class will we use?
	public static final String XML = "xml";
	/**
	 * The serialized object will be sent around as <code>String</code>. The
	 * <code>toString()</code> method is called to get the actual payload.
	 */
	public static final String STRING = "string";
	/**
	 * The serialized object is a binary object and will be casted to
	 * <code>byte[]</code>.
	 */
	public static final String BINARY = "binary";

	/**
	 * Get a list of available serialization formats.
	 * 
	 * The list may be sorted by preference. The format that is preferred most
	 * should be the first item in the list.
	 * 
	 * @return (Immutable) List of available formats for serialization.
	 */
	public List<String> availableFormats();

	/**
	 * Get a Serializer for a specified format.
	 * 
	 * @param format
	 *            The format to serialize to.
	 * @return A Serializer or {@code null} if no serialization is necessary.
	 * @throws SerializationException
	 *             if the format is unknown or the serializer cannot be created.
	 * @throws NullPointerException
	 *             if the format parameter is {@code null}
	 */
	public Serializer getSerializer(String format)
			throws SerializationException;
}
