package de.ovgu.dke.glue.api.reporting;

// be aware of thread boundaries!
public interface ReportListener {
	//TODO +source
	public void onReport(String msg, Throwable cause, Reporter.Level level);
}
