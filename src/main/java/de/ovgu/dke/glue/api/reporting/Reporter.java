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

/**
 * A reporter is capable of emitting events into the reporting framework to
 * notify listeners of errors or status changes.
 * 
 * The reporter implementation must be thread safe, c.f.
 * {@link ReportListenerSupport}
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
public interface Reporter {
	/**
	 * The logging level of the reported event.
	 */
	public static enum Level {
		/**
		 * The event is at info level.
		 */
		INFO,
		/**
		 * Warning level: something went wrong, but the error could be remedied.
		 * However, care might be necessary to avoid further failures.
		 */
		WARN,
		/**
		 * Something went wrong and recovery was not possible
		 */
		ERROR
	}

	/**
	 * Add a report listener to this reporter. The listener will get future
	 * reports. If the listener is already registered nothing happens.
	 * 
	 * @param listener
	 *            the report listener to be added, must be non-null
	 * @throws NullPointerException
	 *             if the listener parameter is {@code null}
	 */
	public void addReportListener(ReportListener listener);

	/**
	 * Remove the specified report listener from this reporter. Future reports
	 * will no longer be sent to the listener. If the listener is not registered
	 * nothing happens.
	 * 
	 * @param listener
	 *            the report listener to be removed
	 * @throws NullPointerException
	 *             if the listener parameter is {@code null}
	 */
	public void removeReportListener(ReportListener listener);
}
