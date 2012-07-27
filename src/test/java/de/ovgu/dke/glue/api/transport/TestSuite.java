package de.ovgu.dke.glue.api.transport;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * <p>
 * This test suite is just for convenience to start all tests just with one
 * call. It works well with the Eclipse J-Unit plugin.
 * </p>
 * <p>
 * Surefire will ignore this suite during Maven test due to naming conventions
 * (it doesn't start or end with "Test" but with "Tests"). This prevents running
 * test cases twice during Maven test.
 * </p>
 * 
 * @author Sebastian Dorok
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ SchemaRegistryTests.class, TransportRegistryTests.class,
		SchemaRecordTests.class })
public class TestSuite {

}
