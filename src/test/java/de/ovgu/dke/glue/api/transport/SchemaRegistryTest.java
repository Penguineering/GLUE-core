package de.ovgu.dke.glue.api.transport;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;

import de.ovgu.dke.glue.api.serialization.SerializationProvider;

/**
 * <p>
 * Tests for {@link SchemaRegistry}.
 * </p>
 * 
 * @author Sebastian Dorok
 * 
 */
public class SchemaRegistryTest {

	/**
	 * <p>
	 * Just register an valid schema record. Everything is fine if registered
	 * schema record is returned.
	 * </p>
	 */
	@Test
	public void T00_registerValidSchemaRecord() {
		SchemaRecord record = createSchemaRecordMock("glue:\\test");
		SchemaRegistry.getInstance().registerSchemaRecord(record);
		assertEquals("Schema registry didn't return expected schema record.",
				record, SchemaRegistry.getInstance().getRecord("glue:\\test"));
	}

	/**
	 * <p>
	 * Instead of adding a valid schema record NULL is registered. Test case
	 * expects a NPE.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public void T01_registerNullSchemaRecord() {
		SchemaRegistry.getInstance().registerSchemaRecord(null);
	}

	/**
	 * <p>
	 * Tests overwriting of formerly registered records. Actually this is
	 * allowed and no exception should be thrown.
	 * </p>
	 * <p>
	 * // FIXME Actually duplicate schemas are allowed and overwrite formerly
	 * registered records. This behavior will change so test case must be
	 * adjusted.
	 * </p>
	 */
	@Test
	public void T02_registerOverwriteSchemaRecord() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record3 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record3);

		SchemaRegistry.getInstance().registerSchemaRecord(record1);

		assertEquals("Schema registry didn't return expected schema record.",
				SchemaRegistry.getInstance().getRecord("glue:\\test"), record1);

		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals("Schema registry didn't return expected schema record.",
				SchemaRegistry.getInstance().getRecord("glue:\\test"), record2);
		assertEquals("Number of registered schemas is incorrect.", 2,
				SchemaRegistry.getInstance().getAvailableSchemas().size());
	}

	/**
	 * <p>
	 * Check if schema records are returned correctly.
	 * </p>
	 */
	@Test
	public void T10_getRecord() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals("Schema registry didn't return expected schema record.",
				SchemaRegistry.getInstance().getRecord("glue:\\test"), record1);
		assertEquals("Schema registry didn't return expected schema record.",
				SchemaRegistry.getInstance().getRecord("glue:\\test2"), record2);
		assertEquals("Number of registered schemas is incorrect.", 2,
				SchemaRegistry.getInstance().getAvailableSchemas().size());
	}

	/**
	 * <p>
	 * If schema is not registered NULL instead of schema record must be
	 * returned.
	 * </p>
	 */
	@Test
	public void T11_getRecord_SchemaNotAvail() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertNull("Expected NULL value but got schema record.", SchemaRegistry
				.getInstance().getRecord("glue:\\test3"));

		assertEquals("Number of registered schemas is incorrect.", 2,
				SchemaRegistry.getInstance().getAvailableSchemas().size());
	}

	/**
	 * <p>
	 * NPE must be thrown if argument for getRecord is NULL.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public void T12_getRecord_SchemaNull() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals("Number of registered schemas is incorrect.", 2,
				SchemaRegistry.getInstance().getAvailableSchemas().size());

		// NPE should be thrown
		SchemaRegistry.getInstance().getRecord(null);
	}

	/**
	 * <p>
	 * Check whether relationship between schema and packet handler is correct
	 * by retrieving for schema specific packet handler.
	 * </p>
	 */
	@Test
	public void T20_getPacketHandlerFactory() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals("Returned not expected packet handler.", SchemaRegistry
				.getInstance().getPacketHandlerFactory("glue:\\test"),
				record1.getPacketHandlerFactory());
		assertEquals("Returned not expected packet handler.", SchemaRegistry
				.getInstance().getPacketHandlerFactory("glue:\\test2"),
				record2.getPacketHandlerFactory());
		assertEquals("Number of registered schemas is incorrect.", 2,
				SchemaRegistry.getInstance().getAvailableSchemas().size());
	}

	/**
	 * <p>
	 * NULL is expected if packet handler for not registered schema is
	 * requested.
	 * </p>
	 */
	@Test
	public void T21_getPacketHandlerFactory_SchemaNotAvail() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertNull(
				"Return value must be NULL but got packet handler instance.",
				SchemaRegistry.getInstance().getPacketHandlerFactory(
						"glue:\\test3"));

		assertEquals("Number of registered schemas is incorrect.", 2,
				SchemaRegistry.getInstance().getAvailableSchemas().size());
	}

	/**
	 * <p>
	 * NPE is expected if packet handler for NULL schema is requested.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public void T22_getPacketHandlerFactory_SchemaNull() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals("Number of registered schemas is incorrect.", 2,
				SchemaRegistry.getInstance().getAvailableSchemas().size());
		// NPE should be thrown
		SchemaRegistry.getInstance().getPacketHandlerFactory(null);
	}

	/**
	 * <p>
	 * Check whether relationship between schema and serialization provider is
	 * correct by retrieving for schema specific serialization provider.
	 * </p>
	 */
	@Test
	public void T30_getSerializationProvider() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals(
				"Returned not expected serialization provider.",
				SchemaRegistry.getInstance().getSerializationProvider(
						"glue:\\test"), record1.getSerializationProvider());
		assertEquals(
				"Returned not expected serialization provider.",
				SchemaRegistry.getInstance().getSerializationProvider(
						"glue:\\test2"), record2.getSerializationProvider());
		assertEquals("Number of registered schemas is incorrect.", 2,
				SchemaRegistry.getInstance().getAvailableSchemas().size());
	}

	/**
	 * <p>
	 * NULL is expected if packet handler for not registered schema is
	 * requested.
	 * </p>
	 */
	@Test
	public void T31_getSerializationProvider_SchemaNotAvail() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertNull(
				"Return value must be NULL but got serialization provider instance.",
				SchemaRegistry.getInstance().getSerializationProvider(
						"glue:\\test3"));

		assertEquals("Number of registered schemas is incorrect.", 2,
				SchemaRegistry.getInstance().getAvailableSchemas().size());
	}

	/**
	 * <p>
	 * NPE is expected if serialization provider for NULL schema is requested.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public void T32_getSerializationProvider_SchemaNull() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals("Number of registered schemas is incorrect.", 2,
				SchemaRegistry.getInstance().getAvailableSchemas().size());

		// NPE should be thrown
		SchemaRegistry.getInstance().getSerializationProvider(null);
	}

	private SchemaRecord createSchemaRecordMock(String schema) {
		SchemaRecord record = EasyMock.createMock(SchemaRecord.class);
		PacketHandlerFactory handlerFactory = EasyMock
				.createMock(PacketHandlerFactory.class);
		SerializationProvider provider = EasyMock
				.createMock(SerializationProvider.class);

		EasyMock.expect(record.getSchema()).andReturn(schema).anyTimes();
		EasyMock.expect(record.getPacketHandlerFactory())
				.andReturn(handlerFactory).anyTimes();
		EasyMock.expect(record.getSerializationProvider()).andReturn(provider)
				.anyTimes();

		EasyMock.replay(record);

		return record;
	}

}
