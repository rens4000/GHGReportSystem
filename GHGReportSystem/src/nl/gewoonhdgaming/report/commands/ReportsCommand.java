package nl.gewoonhdgaming.report.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.primitives.Ints;

import nl.gewoonhdgaming.report.ReportSystem;
import nl.gewoonhdgaming.report.utils.Report;
import nl.gewoonhdgaming.report.utils.ReportStates;

public class ReportsCommand implements CommandExecutor {
	
	private ReportSystem reportSystem;
	
	public ReportsCommand(ReportSystem reportSystem) {
		this.reportSystem = reportSystem;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("reports")) {
			if(args.length == 0) {
				sender.sendMessage(ChatColor.AQUA + "--------+" + ChatColor.GOLD + "ReportSystem" + ChatColor.AQUA + "+--------");
				sender.sendMessage(ChatColor.AQUA + "Created by: Rens4000.");
				sender.sendMessage(ChatColor.AQUA + "Do /reports help for the command list.");
				return false;
			}
			if(args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(ChatColor.AQUA + "--------+" + ChatColor.GOLD + "Commands" + ChatColor.AQUA + "+--------");
				sender.sendMessage(ChatColor.AQUA + "/reports all [noexempt] - Laat alle reports zien");
				sender.sendMessage(ChatColor.AQUA + "/reports take <id> - Zet de status van een report in behandeling");
				sender.sendMessage(ChatColor.AQUA + "/reports show <id> - Laat een report zien");
				sender.sendMessage(ChatColor.AQUA + "/reports finish <id> - Zet de status van een report als behandeld");
				sender.sendMessage(ChatColor.AQUA + "/reports see <id> - Laat je de informatie van de gereporte speler zien");

			}
			if(args[0].equalsIgnoreCase("take")) {
				if(!sender.hasPermission("ReportSystem.Admin")) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Je hebt geen permissie om dat te doen.");
					return false;
				}
				if(args.length != 2) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Verkeerd gebruik! Doe: /reports take <id>.");
					return false;
				}
				if(!isInt(args[1])) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Dat is geen getal!");
					return false;
				}
				int min = Ints.tryParse(args[1]);
				Report r = reportSystem.getReportManager().getReport(min);
				r.setState(ReportStates.INPROGRESS);
				if(!(sender instanceof Player)) {
					r.setTaker("console");
				reportSystem.getReportManager().saveReports();
				sender.sendMessage(reportSystem.PREFIX + "U behandeld de report nu!");
				} else {
					r.setTaker(sender.getName());
					reportSystem.getReportManager().saveReports();
					sender.sendMessage(reportSystem.PREFIX + "U behandeld de report nu!");
				}
			}
			if(args[0].equalsIgnoreCase("see")) {
				if(!sender.hasPermission("ReportSystem.Admin")) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Je hebt geen permissie om dat te doen.");
					return false;
				}
				if(args.length != 2) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Verkeerd gebruik! Doe: /reports take <id>.");
					return false;
				}
				if(!isInt(args[1])) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Dat is geen getal!");
					return false;
				}
				int min = Ints.tryParse(args[1]);
				Report r = reportSystem.getReportManager().getReport(min);
				if(Bukkit.getPlayer(r.getVictim()) == null) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Speler is offline!");
					return false;
				}
				Player p = Bukkit.getPlayer(r.getVictim());
				sender.sendMessage(ChatColor.AQUA + "--------+" + ChatColor.GOLD + "Speler informatie" + ChatColor.AQUA + "+--------");
				sender.sendMessage(reportSystem.PREFIX + "Naam: " + p.getName());
				sender.sendMessage(reportSystem.PREFIX + "Ip-adres: " + p.getAddress());
				sender.sendMessage(reportSystem.PREFIX + "Vliegt: " + p.isFlying());
				sender.sendMessage(reportSystem.PREFIX + "OP: " + p.isOp());
				sender.sendMessage(reportSystem.PREFIX + "Wereld: " + p.getLocation().getWorld().getName());
				sender.sendMessage(reportSystem.PREFIX + "X: " + p.getLocation().getX());
				sender.sendMessage(reportSystem.PREFIX + "Y: " + p.getLocation().getY());
				sender.sendMessage(reportSystem.PREFIX + "Z: " + p.getLocation().getZ());
				
			}
			if(args[0].equalsIgnoreCase("all")) {
				if(!sender.hasPermission("ReportSystem.Admin")) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Je hebt geen permissie om dat te doen.");
					return false;
				}
				if(args.length == 2)
				if(args[1].equalsIgnoreCase("noexempt")) {
					sender.sendMessage(ChatColor.AQUA + "--------+" + ChatColor.GOLD + "Reports(geen uitzonderingen)" + ChatColor.AQUA + "+--------");

					for(Report r : reportSystem.getReportManager().getReports()) {
							sender.sendMessage(ChatColor.YELLOW + "" + r.getId() + ChatColor.AQUA + ". " + r.getVictim() + ": " + ChatColor.RED + r.getReason() + ChatColor.WHITE + " - " + r.getState().getDisplayText());
					}
					return false;
				}
				sender.sendMessage(ChatColor.AQUA + "--------+" + ChatColor.GOLD + "Reports" + ChatColor.AQUA + "+--------");

				for(Report r : reportSystem.getReportManager().getReports()) {
					if(!r.getState().isFinished()) {
						sender.sendMessage(ChatColor.YELLOW + "" + r.getId() + ChatColor.AQUA + ". " + r.getVictim() + ": " + ChatColor.RED + r.getReason() + ChatColor.WHITE + " - " + r.getState().getDisplayText());
					}
				}
				
			}
			if(args[0].equalsIgnoreCase("finish")) {
				if(!sender.hasPermission("ReportSystem.Admin")) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Je hebt geen permissie om dat te doen.");
					return false;
				}
				if(args.length != 2) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Verkeerd gebruik! Doe: /reports finish <id>.");
					return false;
				}
				if(!isInt(args[1])) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Dat is geen getal!");
					return false;
				}
				int min = Ints.tryParse(args[1]);
				if(!reportSystem.getReportManager().getReports().contains(reportSystem.getReportManager().getReport(min))) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Report id bestaat niet!");
					return false;
				}
				Report r = reportSystem.getReportManager().getReport(min);
				r.setState(ReportStates.FINISHED);
				sender.sendMessage(reportSystem.PREFIX + "Report is behandeld!");

			}
			if(args[0].equalsIgnoreCase("show")) {
				if(!sender.hasPermission("ReportSystem.Admin")) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Je hebt geen permissie om dat te doen.");
					return false;
				}
				if(args.length != 2) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Verkeerd gebruik! Doe: /reports show <id>.");
					return false;
				}
				if(!isInt(args[1])) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Dat is geen getal!");
					return false;
				}
				int min = Ints.tryParse(args[1]);
				if(!reportSystem.getReportManager().getReports().contains(reportSystem.getReportManager().getReport(min))) {
					sender.sendMessage(reportSystem.PREFIX + ChatColor.RED + "Report id bestaat niet!");
					return false;
				}
				
				Report r = reportSystem.getReportManager().getReport(min);
				
				sender.sendMessage(ChatColor.AQUA + "--------+" + ChatColor.GOLD + "Reports" + ChatColor.AQUA + "+--------");
				sender.sendMessage(reportSystem.PREFIX + "ID: " + r.getId());
				sender.sendMessage(reportSystem.PREFIX + "Reporter: " + r.getReporter());
				sender.sendMessage(reportSystem.PREFIX + "Slachtoffer: " + r.getVictim());
				sender.sendMessage(reportSystem.PREFIX + "Reden: " + r.getReason());
				sender.sendMessage(reportSystem.PREFIX + "Status: " + r.getState().getDisplayText());
				if(r.getTaker() != null)
					sender.sendMessage(reportSystem.PREFIX + "Behandelaar: " + r.getTaker());
			}
		}
		return false;
	}
	
	public boolean isInt(String min) {
		return Ints.tryParse(min) != null;
	}

}
