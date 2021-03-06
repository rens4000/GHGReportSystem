package nl.gewoonhdgaming.report.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.gewoonhdgaming.report.ReportSystem;

public class ReportCommand implements CommandExecutor {
	
	private ReportSystem reportSystem;
	
	public ReportCommand(ReportSystem reportSystem) {
		this.reportSystem = reportSystem;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("report")) {
			if(args.length >= 2) {
			    StringBuilder builder = new StringBuilder();
			    for(int x = 1; x < args.length; x++)
			        builder.append(args[x]).append(" ");
			    
			    if(!(sender instanceof Player)) {
			    	reportSystem.getReportManager().createReport(builder.toString(), "console", args[0]);
					sender.sendMessage(reportSystem.PREFIX + args[0] + " is gereport!");
					sender.sendMessage(reportSystem.PREFIX + args[0] + " is gereport!" + ChatColor.RED + " Let op!"
							+ " het vals rapporteren van spelers is niet toegestaan!");
			    	return false;
			    }
			    
				reportSystem.getReportManager().createReport(builder.toString(), sender.getName(), args[0]);
				sender.sendMessage(reportSystem.PREFIX + args[0] + " is gereport!" + ChatColor.RED + " Let op!"
						+ " het vals rapporteren van spelers is niet toegestaan!");
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.hasPermission("ReportSystem.Admin")) {
						p.sendMessage(reportSystem.PREFIX + sender.getName() + " heeft een report verstuurd over: " + args[0]);
					}
				}
				return false;
			}
			if(!(sender instanceof Player)) {
				sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Je moet een speler zijn om dit uit te voeren.");
				return false;
			}
			if(args.length <3) {
				sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Verkeerd gebruik! Doe: /report <naam> <reden>");
				return false;
			}
		}
		return false;
	}
	
	

}
