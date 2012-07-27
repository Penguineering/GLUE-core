package de.ovgu.dke.glue.api.transport;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;

import de.ovgu.dke.glue.api.serialization.SerializationProvider;

/**
 * <p>
 * Tests for {@link SchemaRecord}.
 * </p>
 * 
 * @author Sebastian Dorok
 * 
 */
public class SchemaRecordTests {

	/**
	 * <p>
	 * Check instantiation of valid schema record.
	 * </p>
	 */
	@Test
	public void T00_createSchemaRecord_Valid() {
		String schema = "glue://test";
		PacketHandlerFactory handlerFactory = EasyMock
				.createMock(PacketHandlerFactory.class);
		SerializationProvider provider = EasyMock
				.createMock(SerializationProvider.class);
		SchemaRecord record = SchemaRecord.valueOf(schema, handlerFactory,
				provider);
		assertEquals("Unexpected schema returned.", schema, record.getSchema());
		assertEquals("Unexpected packaet handler returned.", handlerFactory,
				record.getPacketHandlerFactory());
		assertEquals("Unexpected serialization provider returned.", provider,
				record.getSerializationProvider());
	}

	/**
	 * <p>
	 * Check instantiation of schema record with NULL schema.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public void T01_createSchemaRecord_NullSchema() {
		String schema = null;
		PacketHandlerFactory handlerFactory = EasyMock
				.createMock(PacketHandlerFactory.class);
		SerializationProvider provider = EasyMock
				.createMock(SerializationProvider.class);
		// NPE should be thrown
		SchemaRecord.valueOf(schema, handlerFactory, provider);
	}

	/**
	 * <p>
	 * Check instantiation of schema record with NULL handler factory.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public void T02_createSchemaRecord_NullHandlerFactory() {
		String schema = "glue://test";
		PacketHandlerFactory handlerFactory = null;
		SerializationProvider provider = EasyMock
				.createMock(SerializationProvider.class);
		// NPE should be thrown
		SchemaRecord.valueOf(schema, handlerFactory, provider);
	}

	/**
	 * <p>
	 * Check instantiation of valid schema record with NULL serialization
	 * provider.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public void T03_createSchemaRecord_NullSerializationProvider() {
		String schema = "glue://test";
		PacketHandlerFactory handlerFactory = EasyMock
				.createMock(PacketHandlerFactory.class);
		SerializationProvider provider = null;
		SchemaRecord.valueOf(schema, handlerFactory, provider);
	}

	/**
	 * <p>
	 * Check instantiation of schema record with empty schema.
	 * </p>
	 */
	@Test(expected = IllegalArgumentException.class)
	public void T41_createSchemaRecord_EmptySchema() {
		String schema = "";
		PacketHandlerFactory handlerFactory = EasyMock
				.createMock(PacketHandlerFactory.class);
		SerializationProvider provider = EasyMock
				.createMock(SerializationProvider.class);
		// NPE should be thrown
		SchemaRecord.valueOf(schema, handlerFactory, provider);
	}

}
