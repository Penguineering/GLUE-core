package de.ovgu.dke.glue.api.reporting;

import java.util.Collection;
import java.util.LinkedList;

public class ReportListenerSupport implements Reporter {
	// private final Object source;

	private Collection<ReportListener> listeners = null;

	public ReportListenerSupport() {
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

	public synchronized void fireReport(String msg, Throwable cause) {
		if (listeners != null)
			for (final ReportListener listener : listeners)
				listener.onReport(msg, cause);
	}
}
