package de.ovgu.dke.glue.api.serialization;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractSerializationProviderTests {

	private ArrayList<Serializer> availSerializers = null;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		availSerializers = null;
	}

	public abstract int getMaxNumOfSerializers();

	public abstract SerializationProvider getSerializationProvider(
			List<Serializer> serializers);

	private SerializationProvider getSerializationProvider() {
		return getSerializationProvider(getSerializers());
	}

	private List<Serializer> getSerializers() {
		availSerializers = new ArrayList<Serializer>();

		Serializer binSerializer = EasyMock.createMock(Serializer.class);
		Serializer stringSerializer = EasyMock.createMock(Serializer.class);

		EasyMock.expect(binSerializer.getFormat())
				.andReturn(SerializationProvider.BINARY).anyTimes();
		EasyMock.expect(stringSerializer.getFormat())
				.andReturn(SerializationProvider.STRING).anyTimes();

		EasyMock.replay(binSerializer);
		EasyMock.replay(stringSerializer);

		int num = getMaxNumOfSerializers();
		switch (num) {
		case 1:
			availSerializers.add(binSerializer);
			break;
		default:
			availSerializers.add(binSerializer);
			availSerializers.add(stringSerializer);
			break;
		}
		return availSerializers;
	}

	/**
	 * 1) Tests whether all registered Serializers' formats are returned. <br>
	 * 2) Checks the order of returned formats (preferred items at first)
	 */
	@Test
	public void T00_AvailableFormats() {
		// get SerializationProvider for test
		SerializationProvider provider = getSerializationProvider();
		// check count
		assertEquals(
				Math.min(availSerializers.size(), getMaxNumOfSerializers()),
				provider.availableFormats().size());
		// check order
		for (int i = 0; i < availSerializers.size(); i++)
			assertEquals(availSerializers.get(i).getFormat(), provider
					.availableFormats().get(i));
	}

	@Test
	public void T01_AvailableFormats_NoSerializers() {
		int num = getMaxNumOfSerializers();
		switch (num) {
		case Integer.MAX_VALUE:
			try {
				List<Serializer> list = getSerializers();
				list.add(null);
				getSerializationProvider(list);
			} catch (NullPointerException e) {
				assertTrue(true);
			}
		case 1:
			try {
				getSerializationProvider(null);
			} catch (NullPointerException e) {
				assertTrue(true);
			}
			break;
		}
	}

	/**
	 * 1) Get the most preferred serializer.
	 */
	@Test
	public void T10_GetSerializer() {
		SerializationProvider provider = getSerializationProvider();
		// get the serializer
		try {
			assertEquals(availSerializers.get(0),
					provider.getSerializer(provider.availableFormats().get(0)));
		} catch (SerializationException e) {
			fail(e.toString());
		}
	}

	@Test
	public void T11_GetSerializers_NoSuitableSerializer() {
		SerializationProvider provider = getSerializationProvider();
		// get the serializer
		try {
			provider.getSerializer(SerializationProvider.JAVA);
		} catch (SerializationException e) {
			assertTrue(true);
		}
	}

	@Test
	public void T12_GetSerializers_NullArgument() {
		SerializationProvider provider = getSerializationProvider();
		// get the serializer
		try {
			provider.getSerializer(null);
		} catch (SerializationException e) {
			assertTrue(true);
		}
	}

}
