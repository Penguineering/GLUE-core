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
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 */
// be aware of thread boundaries!
public interface ReportListener {
	/**
	 * Called when a reportable event occurred.
	 * 
	 * @param source
	 *            The Reporter that detected the event.
	 * @param msg
	 *            The message, i.e. some descriptive text or an error message if
	 *            available, otherwise <code>null</code>.
	 * @param cause
	 *            The cause for the report, if a
	 *            <code>Throwable<code> is available, <code>null</code>
	 *            otherwise.
	 * @param level
	 *            The report error level, see <code>Reporter.Level</code> for
	 *            available values.
	 */
	public void onReport(Reporter source, String msg, Throwable cause,
			Reporter.Level level);
}
