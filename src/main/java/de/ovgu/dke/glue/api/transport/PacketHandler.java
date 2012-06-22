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

import net.jcip.annotations.ThreadSafe;

/**
 * <p>
 * Handler for incoming packets. This interface is the entry point for an
 * application to react to incoming packets.
 * </p>
 * 
 * <p>
 * The packet handler is called in blocking mode, i.e. incoming packets may not
 * be processed while the handler is running. An implementation should only
 * store the packet in a processing queue or call a distinct thread if
 * processing the packet is time-consuming.
 * </p>
 * 
 * <p>
 * The packet handler may be called in multiple threads simultaneously and must
 * take care of the necessary synchronization!
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
@ThreadSafe
public interface PacketHandler {
	/**
	 * Handle an incoming packet from the specified packet thread.
	 * 
	 * @param packetThread
	 *            The packet thread in which the packet occurred, may not be
	 *            {@code null}
	 * @param packet
	 *            The incoming packet, may not be {@code null}
	 * @throw NullPointerException if either packetThread or packet are
	 *        {@code null}; however, as the contract forbids this,
	 *        implementations are not obliged to do so.
	 * @throw IllegalStateException if packet handling in the current state is
	 *        not possible (the reason may be implementation dependent).
	 */
	public void handle(PacketThread packetThread, Packet packet);
}
