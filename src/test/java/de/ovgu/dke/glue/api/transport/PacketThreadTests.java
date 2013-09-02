package de.ovgu.dke.glue.api.transport;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;
import de.ovgu.dke.glue.api.transport.Packet.Priority;

/**
 * <p>
 * Tests for {@link PacketThread}.
 * </p>
 * 
 * @author Sebastian Dorok
 * 
 */
public class PacketThreadTests {

	/**
	 * <p>
	 * Test whether the constructor of the abstract class throws NPE when
	 * connection is NULL.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public void T00_constructor() {
		new PacketThreadTestImpl(null);
	}

	/**
	 * <p>
	 * Test checks whether sendSerializedPayload method is called when
	 * everything is fine for sending.
	 * </p>
	 * 
	 * @throws SerializationException
	 * @throws TransportException
	 */
	@Test
	public void T10_send() throws SerializationException, TransportException {
		String connectionSchema = "glue://test";
		String serializationFormat = SerializationProvider.STRING;
		String payload = "";

		Transport transportMock = EasyMock.createMock(Transport.class);
		Connection connectionMock = EasyMock.createMock(Connection.class);
		EasyMock.expect(connectionMock.getTransport()).andReturn(transportMock)
				.anyTimes();
		EasyMock.expect(connectionMock.getConnectionSchema()).andReturn(
				connectionSchema);
		EasyMock.expect(connectionMock.getSerializationFormat()).andReturn(
				serializationFormat);
		EasyMock.replay(connectionMock);

		SerializationProvider serializationProviderMock = EasyMock
				.createMock(SerializationProvider.class);
		SchemaRecord schemaRecordMock = EasyMock.createMock(SchemaRecord.class);
		Serializer serializerMock = EasyMock.createMock(Serializer.class);
		EasyMock.expect(
				serializationProviderMock.getSerializer(serializationFormat))
				.andReturn(serializerMock);
		EasyMock.expect(schemaRecordMock.getSerializationProvider()).andReturn(
				serializationProviderMock);
		EasyMock.expect(schemaRecordMock.getSchema()).andReturn(
				connectionSchema);
		EasyMock.replay(serializationProviderMock);
		EasyMock.replay(schemaRecordMock);

		SchemaRegistry.getInstance().registerSchemaRecord(schemaRecordMock);

		PacketThreadTestImpl packetThread = new PacketThreadTestImpl(
				connectionMock);
		packetThread.send(payload, Priority.DEFAULT);
	}

	/**
	 * <p>
	 * For sending packet thread needs a transport. Check if it recognizes that
	 * there is no transport.
	 * </p>
	 * 
	 * @throws SerializationException
	 * @throws TransportException
	 */
	@Test(expected = IllegalStateException.class)
	public void T11_send_TransportNull() throws SerializationException,
			TransportException {
		String connectionSchema = "glue://test";
		String serializationFormat = SerializationProvider.STRING;
		String payload = "";

		Transport transportMock = null;
		Connection connectionMock = EasyMock.createMock(Connection.class);
		EasyMock.expect(connectionMock.getTransport()).andReturn(transportMock);
		EasyMock.expect(connectionMock.getConnectionSchema()).andReturn(
				connectionSchema);
		EasyMock.expect(connectionMock.getSerializationFormat()).andReturn(
				serializationFormat);
		EasyMock.replay(connectionMock);

		SerializationProvider serializationProviderMock = EasyMock
				.createMock(SerializationProvider.class);
		SchemaRecord schemaRecordMock = EasyMock.createMock(SchemaRecord.class);
		Serializer serializerMock = EasyMock.createMock(Serializer.class);
		EasyMock.expect(
				serializationProviderMock.getSerializer(serializationFormat))
				.andReturn(serializerMock);
		EasyMock.expect(schemaRecordMock.getSerializationProvider()).andReturn(
				serializationProviderMock);
		EasyMock.expect(schemaRecordMock.getSchema()).andReturn(
				connectionSchema);
		EasyMock.replay(serializationProviderMock);
		EasyMock.replay(schemaRecordMock);

		SchemaRegistry.getInstance().registerSchemaRecord(schemaRecordMock);

		PacketThreadTestImpl packetThread = new PacketThreadTestImpl(
				connectionMock);
		packetThread.send(payload, Priority.DEFAULT);
	}

	/**
	 * <p>
	 * For sending packet thread needs a schema record. Check if it recognizes
	 * that there is no appropriate schema record.
	 * </p>
	 * 
	 * @throws SerializationException
	 * @throws TransportException
	 */
	@Test(expected = TransportException.class)
	public void T12_send_SchemaRecordNull() throws SerializationException,
			TransportException {
		String connectionSchema = "glue://test";
		String connectionSchema2 = "glue://test2";
		String serializationFormat = SerializationProvider.STRING;
		String payload = "";

		Transport transportMock = EasyMock.createMock(Transport.class);
		Connection connectionMock = EasyMock.createMock(Connection.class);
		EasyMock.expect(connectionMock.getTransport()).andReturn(transportMock)
				.anyTimes();
		EasyMock.expect(connectionMock.getConnectionSchema()).andReturn(
				connectionSchema2);
		EasyMock.expect(connectionMock.getSerializationFormat()).andReturn(
				serializationFormat);
		EasyMock.replay(connectionMock);

		SerializationProvider serializationProviderMock = EasyMock
				.createMock(SerializationProvider.class);
		SchemaRecord schemaRecordMock = EasyMock.createMock(SchemaRecord.class);
		Serializer serializerMock = EasyMock.createMock(Serializer.class);
		EasyMock.expect(
				serializationProviderMock.getSerializer(serializationFormat))
				.andReturn(serializerMock);
		EasyMock.expect(schemaRecordMock.getSerializationProvider()).andReturn(
				serializationProviderMock);
		EasyMock.expect(schemaRecordMock.getSchema()).andReturn(
				connectionSchema);
		EasyMock.replay(serializationProviderMock);
		EasyMock.replay(schemaRecordMock);

		SchemaRegistry.getInstance().registerSchemaRecord(schemaRecordMock);

		PacketThreadTestImpl packetThread = new PacketThreadTestImpl(
				connectionMock);
		packetThread.send(payload, Priority.DEFAULT);
	}

	/**
	 * 
	 * <p>
	 * Inner class to implement PacketThread.
	 * </p>
	 * 
	 * @author Sebastian Dorok
	 * 
	 */
	class PacketThreadTestImpl extends PacketThread {

		public PacketThreadTestImpl(Connection connection) {
			// TODO test the ID property
			super(connection, "dummy-test-id");
		}

		@Override
		protected void sendSerializedPayload(Object payload, Priority priority)
				throws TransportException {
			// do nothing
			assertTrue(true);
		}

		@Override
		public void dispose() {
			// do nothing

		}

	}

}
