package de.ovgu.dke.glue.api.reporting;

/**
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de), Sebastian Stober
 *         (sebastian.stober@ovgu.de), Thomas Low (thomas.low@ovgu.de)
 * 
 */
public interface Reporter {
	public static enum Level {
		INFO, WARN, ERROR
	}

	public void addReportListener(ReportListener listener);

	public void removeReportListener(ReportListener listener);
}
