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
package de.ovgu.dke.glue.api.reporting;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Support class to implement reporters to be used as a targe in the proxy
 * pattern.
 * 
 * This implementation is thread safe.
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
public class ReportListenerSupport implements Reporter {
	/**
	 * The source reporter for onReport events. As the listener support is
	 * intended to be used in a specific implementation, this field can be
	 * stored here.
	 */
	private final Reporter source;

	private Collection<ReportListener> listeners = null;

	/**
	 * Creates a new report listener support instance.
	 * 
	 * @param source
	 *            The reporter source to be announces in onReport events.
	 */
	public ReportListenerSupport(final Reporter source) {
		this.source = source;
	}

	@Override
	public synchronized void addReportListener(ReportListener listener) {
		if (listener == null)
			return;

		if (listeners == null)
			listeners = new LinkedList<ReportListener>();

		listeners.add(listener);
	}

	@Override
	public synchronized void removeReportListener(ReportListener listener) {
		if (listeners != null && listener != null)
			listeners.remove(listener);
	}

	public synchronized void fireReport(String msg, Throwable cause,
			Reporter.Level level) {
		if (listeners != null)
			for (final ReportListener listener : listeners)
				listener.onReport(this.source, msg, cause, level);
	}
}
