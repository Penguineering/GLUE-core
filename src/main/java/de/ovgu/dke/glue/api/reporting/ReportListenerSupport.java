package de.ovgu.dke.glue.api.reporting;

import java.util.Collection;
import java.util.LinkedList;

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
