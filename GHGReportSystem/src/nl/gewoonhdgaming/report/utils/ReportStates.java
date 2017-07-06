package nl.gewoonhdgaming.report.utils;

import org.bukkit.ChatColor;

public enum ReportStates {
	
	FINISHED(ChatColor.AQUA + "Behandeld", true), 
	WAITING(ChatColor.GRAY + "Nog niet behandeld", false),
	INPROGRESS(ChatColor.GREEN + "In behandeling", true);
	
	private final String displayText;
	private final boolean finished;
	
	ReportStates(String displayText, boolean finished) {
		this.displayText = displayText;
		this.finished = finished;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public String getDisplayText() {
		return displayText;
}

}
