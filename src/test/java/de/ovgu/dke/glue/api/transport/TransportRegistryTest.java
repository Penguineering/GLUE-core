package de.ovgu.dke.glue.api.transport;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransportRegistryTest {

	// TODO
	// on order to improve unit tests registerTransportFactory() should have
	// more functionality for example the defaultkey behavior (see T02 and 42)
	// and the default factory switches so they can be tested -> in this
	// scenario loadTransportFactory is just another entry point for adding
	// factories

	private static String FACTORY_ONE_KEY = "FactoryOne";
	private static String FACTORY_TWO_KEY = "FactoryTwo";
	private static String DEFAULT_REGISTRY_KEY = "test";

	private TransportFactory factoryOne = null;
	private TransportFactory factoryTwo = null;
	private TransportFactory factoryThree = null;

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {
		TransportRegistry.getInstance().disposeAll();
	}

	@Test
	public void T00_testRegisterTransportFactory() {
		registerMockTransportFactories();

		assertSame(
				"Returned factory is not the registered one!",
				factoryTwo,
				TransportRegistry.getInstance().getTransportFactory(
						FACTORY_TWO_KEY));
	}

	@Test
	public void T01_testRegisterTransportFactory_OverwriteExisitingFactory() {
		registerMockTransportFactories();
		registerMockTransportFactoryByName(FACTORY_TWO_KEY);

		assertNotSame("Factory with this key should have been overwritten.",
				factoryTwo, TransportRegistry.getInstance()
						.getTransportFactory(FACTORY_TWO_KEY));

		assertSame(
				"Returned factory is not the registered one!",
				factoryThree,
				TransportRegistry.getInstance().getTransportFactory(
						FACTORY_TWO_KEY));

	}

	@Test
	public void T02_testRegisterTransportFactory_DefaultKeyBehavior() {
		registerMockTransportFactories();
		registerMockTransportFactoryByName(TransportRegistry.DEFAULT_KEY);

		assertSame("Returned factory is not the registered one!", factoryThree,
				TransportRegistry.getInstance().getTransportFactory("test"));

	}

	@Test
	public void T10_testGetTransportFactory_NoFactoryAdded() {
		// delete all registered factories to make sure that no factory is added
		// FIXME: cannot safely test behavior of newly instantiated registry
		// because it is a singleton and order of execution of test cases cannot
		// be guaranteed
		TransportRegistry.getInstance().disposeAll();
		assertSame(
				"Expected NULL but factory was returned!",
				null,
				TransportRegistry.getInstance().getTransportFactory(
						FACTORY_ONE_KEY));
	}

	@Test
	public void T11_testGetTransportFactory_KeyNotRegistered() {
		registerMockTransportFactoryOne();

		assertSame(
				"Expected NULL but factory was returned!",
				null,
				TransportRegistry.getInstance().getTransportFactory(
						FACTORY_TWO_KEY));
	}

	@Test
	public void T20_testDisposeAll() {
		registerMockTransportFactories();
		factoryOne.dispose();
		EasyMock.expectLastCall().once();
		EasyMock.replay(factoryOne);

		factoryTwo.dispose();
		EasyMock.expectLastCall().once();
		EasyMock.replay(factoryTwo);

		// dispose all factories, EasyMock takes care that the dispose methods
		// are called exactly once, if not an assertion error is thrown
		TransportRegistry.getInstance().disposeAll();
		EasyMock.verify(factoryOne);
		EasyMock.verify(factoryTwo);
	}

	@Test
	public void T30_testSetDefaultTransportFactory() {
		registerMockTransportFactories();
		TransportRegistry.getInstance().setDefaultTransportFactory(
				FACTORY_ONE_KEY);

		assertSame("Unexpected default factory returned.", factoryOne,
				TransportRegistry.getDefaultTransportFactory());
	}

	@Test
	public void T31_testSetDefaultTransportFactory_KeyNotRegistered() {
		registerMockTransportFactoryOne();
		TransportRegistry.getInstance().setDefaultTransportFactory(
				FACTORY_ONE_KEY);

		try {
			TransportRegistry.getInstance().setDefaultTransportFactory(
					FACTORY_TWO_KEY);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		registerMockTransportFactoryTwo();
		assertEquals("Unexpected default factory returned.", factoryOne,
				TransportRegistry.getDefaultTransportFactory());
	}

	@Test
	public void T32_testSetDefaultTransportFactory_RemoveDefaultSetting() {
		T30_testSetDefaultTransportFactory();

		TransportRegistry.getInstance().setDefaultTransportFactory(null);

		assertNull("Unexpected default factory returned.",
				TransportRegistry.getDefaultTransportFactory());
	}

	@Test
	public void T40_testGetTransportFactoryKeys() {
		registerMockTransportFactories();

		assertEquals("Number of registered factories differs!", 2,
				TransportRegistry.getInstance().getTransportFactoryKeys()
						.size());
	}

	@Test
	public void T41_testGetTransportFactoryKeys_SameKeyTwice() {
		registerMockTransportFactories();
		registerMockTransportFactoryTwo();

		assertEquals("Number of registered factories differs!", 2,
				TransportRegistry.getInstance().getTransportFactoryKeys()
						.size());
	}

	@Test
	public void T42_testGetTransportFactoryKeys_DefaultKeyBahavior() {
		registerMockTransportFactories();
		registerMockTransportFactoryByName(TransportRegistry.DEFAULT_KEY);

		assertEquals("Number of registered factories differs!", 3,
				TransportRegistry.getInstance().getTransportFactoryKeys()
						.size());
	}

	// helper methods

	private void registerMockTransportFactories() {
		registerMockTransportFactoryOne();
		registerMockTransportFactoryTwo();
	}

	private void registerMockTransportFactoryOne() {
		factoryOne = EasyMock.createNiceMock(TransportFactory.class);
		TransportRegistry.getInstance().registerTransportFactory(
				FACTORY_ONE_KEY, factoryOne, TransportRegistry.NO_DEFAULT);
	}

	private void registerMockTransportFactoryTwo() {
		factoryTwo = EasyMock.createNiceMock(TransportFactory.class);
		TransportRegistry.getInstance().registerTransportFactory(
				FACTORY_TWO_KEY, factoryTwo, TransportRegistry.NO_DEFAULT);
	}

	private void registerMockTransportFactoryByName(String key) {
		factoryThree = EasyMock.createNiceMock(TransportFactory.class);
		EasyMock.expect(factoryThree.getDefaultRegistryKey())
				.andReturn(DEFAULT_REGISTRY_KEY).anyTimes();
		EasyMock.replay(factoryThree);
		TransportRegistry.getInstance().registerTransportFactory(key,
				factoryThree, TransportRegistry.NO_DEFAULT);
	}
}
