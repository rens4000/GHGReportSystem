package nl.gewoonhdgaming.report.managers;

import java.util.ArrayList;
import java.util.List;

import com.google.common.primitives.Ints;

import nl.gewoonhdgaming.report.ReportSystem;
import nl.gewoonhdgaming.report.utils.Report;
import nl.gewoonhdgaming.report.utils.ReportStates;

public class ReportManager {
	
	List<Report> reports;
	
	private ReportSystem reportSystem;
	
	public ReportManager(ReportSystem reportSystem) {
		this.reportSystem = reportSystem;
		reports = new ArrayList<Report>();
	}
	
	public void createReport(String reason, String reporter, String victim) {
		//Test
		if(reportSystem.getConfigManager().getDataFile().get("id-count") == null) {
			reportSystem.getConfigManager().getDataFile().set("id-count", 1);
			reportSystem.getConfigManager().save();
			} else {
				reportSystem.getConfigManager().getDataFile().set("id-count", reportSystem.getConfigManager().getDataFile().getInt("id-count") +1);
				reportSystem.getConfigManager().save();
			}
		int newId = reportSystem.getConfigManager().getDataFile().getInt("id-count") +1;
		Report report = new Report(newId, reporter, victim, reason);
		reports.add(report);
		saveReports();
		
	}
	
	public List<Report> getReports() {
		return reports;
	}
	
	public Report getReport(int id) {
		for(Report r : reports) {
			if(r.getId() == id)
				return r;
		}
		return null;
	}
	
	public boolean reportExists(int id) {
		return reports.contains(getReport(id));
	}
	
	public  void loadArenas() {
		if(!reportSystem.getConfigManager().
				getDataFile().contains("Reports"))
			return;
		
		for(String key : reportSystem.getConfigManager().
				getDataFile().getConfigurationSection("Reports").getKeys(false)) {
			if(!isInt(key))
				return;
			final Integer min = Ints.tryParse(key);
			String reporter = reportSystem.getConfigManager().
					getDataFile().getString("Reports." + min + ".reporter");
			String victim = reportSystem.getConfigManager().
					getDataFile().getString("Reports." + min + ".victim");
			String reason = reportSystem.getConfigManager().
					getDataFile().getString("Reports." + min + ".reason");
			String taker = reportSystem.getConfigManager().getDataFile()
					.getString("Reports." + min + ".taker");
			
			ReportStates rs = null;
			
			switch(reportSystem.getConfigManager().getDataFile()
					.getString("Reports." + min + ".status")) {
			case "WAITING":
				rs = ReportStates.WAITING;
				break;
			case "INPROGRESS":
				rs = ReportStates.INPROGRESS;
				break;
			case "FINISHED":
				rs = ReportStates.FINISHED;
				break;
			}
			Report r = new Report(min, reporter, victim, reason);
			
			r.setTaker(taker);
			r.setState(rs);
			reports.add(r);
		}
	}
	
	public boolean isInt(String s) {
		return Ints.tryParse(s) != null;
	}

	public void saveReports() {
		if(reports.size() == 0)
			return;
		for(Report r : reports) {
			reportSystem.getConfigManager().
			getDataFile().set("Reports." + r.getId() + ".reporter", r.getReporter());
			reportSystem.getConfigManager().
			getDataFile().set("Reports." + r.getId() + ".victim", r.getVictim());
			reportSystem.getConfigManager().
			getDataFile().set("Reports." + r.getId() + ".reason" , r.getReason());
			reportSystem.getConfigManager().
			getDataFile().set("Reports." + r.getId() + ".status" , r.getState().toString());
			if(r.getTaker() != null)
				reportSystem.getConfigManager().
				getDataFile().set("Reports." + r.getId() + ".taker" , r.getTaker());
			reportSystem.getConfigManager().save();
		}
	}

}
