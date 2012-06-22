/*
 * Copyright 2012 Stefan Haun, Thomas Low, Sebastian Stober, Andreas Nürnberger
 * 
 *      Data and Knowledge Engineering Group, 
 * 		Faculty of Computer Science,
 *		Otto-von-Guericke University,
 *		Magdeburg, Germany
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ovgu.dke.glue.api.transport;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.ThreadSafe;

/**
 * <p>
 * The centralized register for transport factories allows decoupling of
 * transport factory initialization and usage. One factory may be set as default
 * factory, but multiple transport factories are accessible.
 * </p>
 * <p>
 * If more than one factory shall be used, the application has to take care of
 * factory selection.
 * </p>
 * 
 * <p>
 * This class is thread safe.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
@ThreadSafe
public class TransportRegistry {
	private static TransportRegistry instance = null;

	public static boolean AS_DEFAULT = true;
	public static boolean NO_DEFAULT = false;
	public static String DEFAULT_KEY = null;

	/**
	 * Get the singleton instance of the transport registry.
	 * 
	 * @return Singleton transport registry instance.
	 */
	public static synchronized TransportRegistry getInstance() {
		if (instance == null)
			instance = new TransportRegistry();

		return instance;
	}

	private final Map<String, TransportFactory> registry;
	private String defaultKey;

	private TransportRegistry() {
		this.registry = new ConcurrentHashMap<String, TransportFactory>();
	}

	/**
	 * Register a transport factory. If the key already exists, its associated
	 * factory is overwritten.
	 * 
	 * @param key
	 *            Key for accessing the transport factory or {@code DEFAULT_KEY}
	 *            to use the factory implementation's default.
	 * @param factory
	 *            Transport factory to be registered.
	 * @throws NullPointerException
	 *             if factory parameter are {@code null}
	 */
	// TODO asDefault-Parameter?
	// TODO siehe Korrespondenz zu loadTransportFactory
	public void registerTransportFactory(String key, TransportFactory factory) {
		if (factory == null)
			throw new NullPointerException("Factory may not be null!");

		final String k = (key == DEFAULT_KEY) ? factory.getDefaultRegistryKey()
				: key;

		registry.put(k, factory);
	}

	/**
	 * Get the set of all registered transport factory keys.
	 * 
	 * @return Unmodifiable set of keys in the registry.
	 */
	public Set<String> getTransportFactoryKeys() {
		return Collections.unmodifiableSet(registry.keySet());
	}

	/**
	 * <p>
	 * Set the default transport factory.
	 * </p>
	 * 
	 * @param key
	 *            The key of the default transport factory or {@code null} to
	 *            remove the default setting.
	 * @throws IllegalArgumentException
	 *             if a factory with the provided key is not registered.
	 */
	// TODO Name setDefaultTransportFactoryKey
	public void setDefaultTransportFactory(final String key) {
		if (key != null && !registry.containsKey(key))
			throw new IllegalArgumentException(
					"Will not set default key which is not in the registry!");

		this.defaultKey = key;
	}

	/**
	 * Get the transport factory for a key.
	 * 
	 * @param key
	 *            The key used when registering the transport factory.
	 * @return A transport factory instance or {@code null} if there is no
	 *         factory for the key.
	 * @throws NullPointerException
	 *             if the key parameter is {@code null}
	 */
	public TransportFactory getTransportFactory(final String key) {
		return registry.get(key);
	}

	/**
	 * Get the default transport factory.
	 * 
	 * @return The transport factory instance set as default or {@code null}, if
	 *         no default is set or there is no transport factory for the
	 *         default key in the registry.
	 */
	public static synchronized TransportFactory getDefaultTransportFactory() {
		final TransportRegistry reg = getInstance();
		return reg.defaultKey == null ? null : reg
				.getTransportFactory(reg.defaultKey);
	}

	/**
	 * <p>
	 * Generic loading for a transport factory, which only needs to be specified
	 * by its class name. This way a transport implementation can be invoked
	 * without any compile time dependency.
	 * </p>
	 * 
	 * <p>
	 * After creating an instance of the transport factory, the default packet
	 * handler factory and the serialization provider are set. Then
	 * <code>init()</code> is called and the factory is registered.
	 * </p>
	 * 
	 * @param factoryClass
	 *            The canonical class name of the transport factory.
	 * @param config
	 *            A properties instance which will be handed to the transport
	 *            implementation.
	 * @param asDefault
	 *            Set to <code>AS_DEFAULT</code> if this is the default factory,
	 *            <code>NO_DEFAULT</code> otherwise.
	 * @param key
	 *            The key to use for this transport factory,
	 *            <code>DEFAULT_KEY</code> if you do not want to specify.
	 * @return the transport factory instance
	 * @throws ClassNotFoundException
	 *             if the factoryClass cannot be loaded
	 * @throws TransportException
	 *             if anything goes wrong during instantiation or setup
	 */
	public TransportFactory loadTransportFactory(String factoryClass,
			Properties config, boolean asDefault, String key)
			throws ClassNotFoundException, TransportException {
		try {
			// get the class
			final Class<?> clazz = Class.forName(factoryClass);

			// create instance
			final TransportFactory factory = (TransportFactory) clazz
					.newInstance();

			// some setup
			if (factory != null) {
				factory.init(config);

				// TODO das eher in registerTransportFactory?

				// register the factory
				registerTransportFactory(key, factory);

				if (asDefault) {
					final String k = (key == DEFAULT_KEY) ? factory
							.getDefaultRegistryKey() : key;
					setDefaultTransportFactory(k);
				}
			}

			return factory;
		} catch (SecurityException e) {
			throw new TransportException(
					"Security exception on accessing constructor for "
							+ factoryClass + ": " + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new TransportException(
					"Illegal arguments calling constructor for " + factoryClass
							+ ": " + e.getMessage(), e);
		} catch (InstantiationException e) {
			throw new TransportException("Could not instantiate "
					+ factoryClass + ": " + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new TransportException("Illegal access: " + e.getMessage(), e);
		}

	}

	/**
	 * Dispose all transport factory. Call to clean-up before exiting the
	 * application or if the GLUE library is not needed anymore.
	 */
	public void disposeAll() {
		for (final TransportFactory factory : this.registry.values())
			factory.dispose();
		defaultKey = null;
		registry.clear();
	}

}
