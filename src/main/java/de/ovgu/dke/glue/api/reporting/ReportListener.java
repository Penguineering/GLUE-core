package de.ovgu.dke.glue.api.reporting;

public interface ReportListener {
	public void onReport(String msg, Throwable cause);
}
