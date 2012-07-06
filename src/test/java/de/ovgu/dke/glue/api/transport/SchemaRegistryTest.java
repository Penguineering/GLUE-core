package de.ovgu.dke.glue.api.transport;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;

import de.ovgu.dke.glue.api.serialization.SerializationProvider;

public class SchemaRegistryTest {

	@Test
	public void T00_registerValidSchemaRecord() {
		SchemaRecord record = createSchemaRecordMock("glue:\\test");
		SchemaRegistry.getInstance().registerSchemaRecord(record);
		assertTrue(true);
	}

	@Test
	public void T01_registerNullSchemaRecord() {
		try {
			SchemaRegistry.getInstance().registerSchemaRecord(null);
			fail("Didn't catch expected NPE.");
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
	}

	@Test
	public void T02_registerOverwriteSchemaRecord() {
		// TODO adjust test case when exception is thrown on duplicate schemas
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record3 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record3);

		SchemaRegistry.getInstance().registerSchemaRecord(record1);

		assertEquals(SchemaRegistry.getInstance().getRecord("glue:\\test"),
				record1);

		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals(SchemaRegistry.getInstance().getRecord("glue:\\test"),
				record2);
		assertEquals(2, SchemaRegistry.getInstance().getAvailableSchemas()
				.size());
	}

	@Test
	public void T10_getRecord() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals(SchemaRegistry.getInstance().getRecord("glue:\\test"),
				record1);
		assertEquals(SchemaRegistry.getInstance().getRecord("glue:\\test2"),
				record2);
		assertEquals(2, SchemaRegistry.getInstance().getAvailableSchemas()
				.size());
	}

	@Test
	public void T11_getRecord_SchemaNotAvail() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertNull(SchemaRegistry.getInstance().getRecord("glue:\\test3"));

		assertEquals(2, SchemaRegistry.getInstance().getAvailableSchemas()
				.size());
	}

	@Test
	public void T12_getRecord_SchemaNull() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		try {
			SchemaRegistry.getInstance().getRecord(null);
			fail("Didn't catch expected NPE.");
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		assertEquals(2, SchemaRegistry.getInstance().getAvailableSchemas()
				.size());
	}

	@Test
	public void T20_getPacketHandlerFactory() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals(
				SchemaRegistry.getInstance().getPacketHandlerFactory(
						"glue:\\test"), record1.getPacketHandlerFactory());
		assertEquals(
				SchemaRegistry.getInstance().getPacketHandlerFactory(
						"glue:\\test2"), record2.getPacketHandlerFactory());
		assertEquals(2, SchemaRegistry.getInstance().getAvailableSchemas()
				.size());
	}

	@Test
	public void T21_getPacketHandlerFactory_SchemaNotAvail() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertNull(SchemaRegistry.getInstance().getPacketHandlerFactory(
				"glue:\\test3"));

		assertEquals(2, SchemaRegistry.getInstance().getAvailableSchemas()
				.size());
	}

	@Test
	public void T22_getPacketHandlerFactory_SchemaNull() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		try {
			SchemaRegistry.getInstance().getPacketHandlerFactory(null);
			fail("Didn't catch expected NPE.");
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		assertEquals(2, SchemaRegistry.getInstance().getAvailableSchemas()
				.size());
	}

	@Test
	public void T30_getSerializationProvider() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertEquals(
				SchemaRegistry.getInstance().getSerializationProvider(
						"glue:\\test"), record1.getSerializationProvider());
		assertEquals(
				SchemaRegistry.getInstance().getSerializationProvider(
						"glue:\\test2"), record2.getSerializationProvider());
		assertEquals(2, SchemaRegistry.getInstance().getAvailableSchemas()
				.size());
	}

	@Test
	public void T31_getSerializationProvider_SchemaNotAvail() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		assertNull(SchemaRegistry.getInstance().getSerializationProvider(
				"glue:\\test3"));

		assertEquals(2, SchemaRegistry.getInstance().getAvailableSchemas()
				.size());
	}

	@Test
	public void T32_getSerializationProvider_SchemaNull() {
		SchemaRecord record1 = createSchemaRecordMock("glue:\\test");
		SchemaRecord record2 = createSchemaRecordMock("glue:\\test2");

		SchemaRegistry.getInstance().registerSchemaRecord(record1);
		SchemaRegistry.getInstance().registerSchemaRecord(record2);

		try {
			SchemaRegistry.getInstance().getSerializationProvider(null);
			fail("Didn't catch expected NPE.");
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		assertEquals(2, SchemaRegistry.getInstance().getAvailableSchemas()
				.size());
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
