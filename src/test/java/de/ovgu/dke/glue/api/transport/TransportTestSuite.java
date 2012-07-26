package de.ovgu.dke.glue.api.transport;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SchemaRegistryTest.class, TransportRegistryTest.class,
		SchemaRecordTest.class })
public class TransportTestSuite {

}
