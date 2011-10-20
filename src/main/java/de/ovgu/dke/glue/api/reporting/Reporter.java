package de.ovgu.dke.glue.api.reporting;

public interface Reporter {
	public void addReportListener(ReportListener listener);
	public void removeReportListener(ReportListener listener);
	
	
}
