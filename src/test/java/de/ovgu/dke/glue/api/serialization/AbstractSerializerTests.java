package de.ovgu.dke.glue.api.serialization;

import static org.junit.Assert.*;
import org.junit.Test;

public abstract class AbstractSerializerTests {

	/**
	 * 
	 * @param format
	 *            if needed for instantiation of serializer, else it can be
	 *            ignored
	 * @return a serializer that can be tested
	 */
	public abstract Serializer getSerializer(String format);

	/**
	 * Test whether serializer returns correct format.
	 */
	@Test
	public void T00_getFormat() {
		Serializer ser = getSerializer(SerializationProvider.BINARY);
		assertNotNull(
				"Serializer doesn't handle formats correctly - NULL format.",
				ser.getFormat());
		assertTrue(
				"Serializer doesn't handle formats correctly - not allowed format",
				matchesAllowedFormats(ser.getFormat()));
	}

	/**
	 * Test reaction when format argument is NULL. There should be a default
	 * format that is not NULL according to the API.
	 * 
	 * In case of serializers that don't support dynamic formats this test will
	 * always pass.
	 */
	@Test
	public void T01_getFormat_NullFormat() {
		Serializer ser = getSerializer(null);
		assertNotNull(
				"Serializer doesn't handle formats correctly - NULL format.",
				ser.getFormat());
		assertTrue(
				"Serializer doesn't handle formats correctly - not allowed format",
				matchesAllowedFormats(ser.getFormat()));
	}

	/**
	 * {@link Serializer} interface claims that the serializer have to take care
	 * that the format is one of the defined in {@link SerializationProvider}.
	 * It is not specified how this should be handled. Test case tests only
	 * return value. Test fails if wrong format is returned.
	 * 
	 * In case of serializers that don't support dynamic formats this test will
	 * always pass.
	 */
	@Test
	public void T02_getFormat_NonSuitableArgument() {
		Serializer ser = getSerializer("WRONG_FORMAT");
		assertNotSame(
				"Serializer doesn't handle formats correctly - not allowed format",
				"WRONG_FORMAT", ser.getFormat());
		assertTrue(
				"Serializer doesn't handle formats correctly - not allowed format",
				matchesAllowedFormats(ser.getFormat()));
	}

	/**
	 * Test if serialization and deserialization works by serializing a string
	 * and deserializing it.
	 * 
	 * The string contains: characters
	 */
	@Test
	public void T10_serializeAndDeserialize_Characters() {
		String payload = "thisIsADataPackage";
		Serializer ser = getSerializer(SerializationProvider.STRING);
		Object o;
		try {
			o = ser.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, ser.deserialize(o));
		} catch (SerializationException e) {
			e.printStackTrace();
			fail("Caught unexpected serialization exception!");
		}
	}

	/**
	 * Test if serialization and deserialization works by serializing a string
	 * and deserializing it.
	 * 
	 * The string contains: characters and whitespaces
	 */
	@Test
	public void T11_serializeAndDeserialize_Whitespaces() {
		String payload = "this is a data package";
		Serializer ser = getSerializer(SerializationProvider.STRING);
		Object o;
		try {
			o = ser.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, ser.deserialize(o));
		} catch (SerializationException e) {
			e.printStackTrace();
			fail("Caught unexpected serialization exception!");
		}
	}

	/**
	 * Test if serialization and deserialization works by serializing a string
	 * and deserializing it.
	 * 
	 * The string contains: numbers
	 */
	@Test
	public void T12_serializeAndDeserialize_Numbers() {
		String payload = "1234567890";
		Serializer ser = getSerializer(SerializationProvider.STRING);
		Object o;
		try {
			o = ser.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, ser.deserialize(o));
		} catch (SerializationException e) {
			e.printStackTrace();
			fail("Caught unexpected serialization exception!");
		}
	}

	/**
	 * Test if serialization and deserialization works by serializing a string
	 * and deserializing it.
	 * 
	 * The string contains: special characters
	 */
	@Test
	public void T13_serializeAndDeserialize_SpecialCharacters() {
		String payload = "°^!\"§$%&/()=?`´*+~#'-_:.;,<>|'{[]}\\";
		Serializer ser = getSerializer(SerializationProvider.STRING);
		Object o;
		try {
			o = ser.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, ser.deserialize(o));
		} catch (SerializationException e) {
			e.printStackTrace();
			fail("Caught unexpected serialization exception!");
		}
	}

	/**
	 * Test if serialization of NULL works correctly by throwing a null pointer
	 * exception.
	 * 
	 */
	@Test
	public void T14_serialize_NullArgument() {
		Serializer ser = getSerializer(SerializationProvider.STRING);
		try {
			ser.serialize(null);
			fail("Didn't catch expected null pointer exception!");
		} catch (SerializationException e) {
			e.printStackTrace();
			fail("Catch serialization exception - did not expect that!");
		} catch (NullPointerException e) {
			e.printStackTrace();
			assertTrue("Catched expected NPE exception!", true);
		}
	}

	/**
	 * Test if deserialization of NULL works correctly by throwing a
	 * serialization exception.
	 * 
	 */
	@Test
	public void T15_deserialize_NullArgument() {
		Serializer ser = getSerializer(SerializationProvider.STRING);
		try {
			ser.deserialize(null);
			fail("Didn't catch expected null pointer exception!");
		} catch (SerializationException e) {
			e.printStackTrace();
			fail("Catch serialization exception - did not expect that!");
		} catch (NullPointerException e) {
			e.printStackTrace();
			assertTrue("Catched expected NPE exception!", true);
		}
	}

	// helper method to determine whether format is a correct one
	private boolean matchesAllowedFormats(String format) {
		if (SerializationProvider.BINARY.equals(format)) {
			return true;
		}
		if (SerializationProvider.JAVA.equals(format)) {
			return true;
		}
		if (SerializationProvider.STRING.equals(format)) {
			return true;
		}
		if (SerializationProvider.XML.equals(format)) {
			return true;
		}
		if (SerializationProvider.SERIALIZABLE.equals(format)) {
			return true;
		}
		return false;
	}

}
