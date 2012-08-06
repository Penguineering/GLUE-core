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

import net.jcip.annotations.NotThreadSafe;

/**
 * <p>
 * Payload serializer. Called to convert a payload object into a representation
 * the transport can send and convert back to the original object type on
 * receipt.
 * </p>
 * 
 * <p>
 * Serializaers must be provided by the application via a
 * <code>SerializationProvider</code>.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
// TODO @Immutable?
@NotThreadSafe
public interface Serializer {
	/**
	 * <p>
	 * Get the result class type of this serializer. The format enables the
	 * transport layer to determine the result class of the serialize call.
	 * </p>
	 * <p>
	 * The serializer should return a type matching the specification of the
	 * format in the {@link SerializationProvider}.
	 * </p>
	 * 
	 * @return A String specifying the result class type.
	 */
	public String getFormat();

	/**
	 * Serialize a payload object to be sent over the transport.
	 * 
	 * @param o
	 *            The payload to be serialized.
	 * @return A class type depending on the format, which can be obtained by
	 *         calling <code>getFormat</code>.
	 * @throws SerializationException
	 *             if the serialization fails
	 * @throws NullPointerException
	 *             if the parameter is {@code null}
	 * @throws ClassCastException
	 *             (optional) if the parameter object does not match the
	 *             format-specific type
	 */
	public Object serialize(Object o) throws SerializationException;

	/**
	 * De-serialize a packet from the transport into a payload object to be
	 * handed to the application.
	 * 
	 * @param o
	 *            The object to be de-serialized.
	 * @return The payload object.
	 * @throws SerializationException
	 *             if the de-serialization fails.
	 * @throws NullPointerException
	 *             if the parameter is {@code null}
	 * @throws ClassCastException
	 *             (optional) if the parameter object does not match the
	 *             format-specific type
	 */
	public Object deserialize(Object o) throws SerializationException;

}
