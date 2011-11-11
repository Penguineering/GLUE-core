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
