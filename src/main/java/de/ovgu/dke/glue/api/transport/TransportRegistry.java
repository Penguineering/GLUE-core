package de.ovgu.dke.glue.api.transport;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class TransportRegistry {
	private static TransportRegistry instance = null;

	public static synchronized TransportRegistry getInstance() {
		if (instance == null)
			instance = new TransportRegistry();

		return instance;
	}

	private final ConcurrentMap<String, TransportFactory> registry;
	private String defaultKey;

	private TransportRegistry() {
		this.registry = new ConcurrentHashMap<String, TransportFactory>();
	}

	public void registerTransportFactory(String key, TransportFactory factory) {
		registry.put(key, factory);
	}

	public Set<String> getTransportFactoryKeys() {
		return Collections.unmodifiableSet(registry.keySet());
	}

	public void setDefaultTransportFactory(String key) {
		this.defaultKey = key;
	}

	public TransportFactory getDefaultTransportFactory() {
		return registry.get(defaultKey);
	}
}
