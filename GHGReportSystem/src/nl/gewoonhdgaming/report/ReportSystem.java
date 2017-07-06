package nl.gewoonhdgaming.report;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import nl.gewoonhdgaming.report.commands.ReportCommand;
import nl.gewoonhdgaming.report.commands.ReportsCommand;
import nl.gewoonhdgaming.report.gui.ReportGUI;
import nl.gewoonhdgaming.report.gui.ReportGUIListener;
import nl.gewoonhdgaming.report.managers.ConfigManager;
import nl.gewoonhdgaming.report.managers.ReportManager;

public class ReportSystem extends JavaPlugin {
	
	public final String PREFIX = ChatColor.RED + "Report" + ChatColor.DARK_RED + "System " + ChatColor.WHITE;
	
	//Main variables
	private ReportManager reportManager;
	
	private ConfigManager configManager;
	
	private ReportGUI reportGUI;
	
	private static ReportSystem reportSystem;
	
	
	//Getters
	public ReportGUI getReportGUI() {
		return reportGUI;
	}
	public static ReportSystem getReportSystem() {
		return reportSystem;
	}
	public ReportManager getReportManager() {
		return reportManager;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	@Override
	public void onEnable() {
		this.reportManager = new ReportManager(this);
		this.configManager = new ConfigManager();
		this.reportGUI = new ReportGUI();
		
		reportManager.loadArenas();
		
		getCommand("report").setExecutor(new ReportCommand(this));
		getCommand("reports").setExecutor(new ReportsCommand(this));
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ReportGUIListener(this), this);
	}
	
	@Override
	public void onDisable() {
		reportManager.saveReports();
	}

}
