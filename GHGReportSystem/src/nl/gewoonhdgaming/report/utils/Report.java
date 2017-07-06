package nl.gewoonhdgaming.report.utils;

public class Report {
	
	private int id;
	private String reporter;
	private String victim;
	private String reason;
	private String taker = null;
	private ReportStates state;
	
	public Report(int id, String reporter, String victim, String reason) {
		this.id = id;
		this.reporter = reporter;
		this.victim = victim;
		this.state = ReportStates.WAITING;
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public ReportStates getState() {
		return state;
	}

	public final int getId() {
		return id;
	}
	
	public final String getTaker() {
		return taker;
	}

	public final String getReporter() {
		return reporter;
	}
	
	public void setTaker(String s) {
		this.taker = s;
	}

	public final String getVictim() {
		return victim;
	}

	public final void setId(int id) {
		this.id = id;
	}
	
	public final void setState(ReportStates state) {
		this.state = state;
	}

	public final void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public final void setVictim(String victim) {
		this.victim = victim;
	}

}
