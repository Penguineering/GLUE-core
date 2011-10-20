package de.ovgu.dke.glue.api.reporting;

// be aware of thread boundaries!
public interface ReportListener {
	public void onReport(String msg, Throwable cause);
}
