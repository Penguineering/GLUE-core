package de.ovgu.dke.glue.api.reporting;

public interface Reporter {
	public static enum Level {
		INFO,
		WARN,
		ERROR
	}
	
	public void addReportListener(ReportListener listener);
	public void removeReportListener(ReportListener listener);
	
	
}
