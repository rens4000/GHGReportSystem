package nl.gewoonhdgaming.report.gui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import nl.gewoonhdgaming.report.ReportSystem;
import nl.gewoonhdgaming.report.utils.Report;

public class ReportGUIListener implements Listener {
	
	private ReportSystem reportSystem;
	
	public ReportGUIListener(ReportSystem reportSystem) {
		this.reportSystem = reportSystem;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked(); // The player that clicked the item
		ItemStack clicked = event.getCurrentItem(); // The item that was clicked
		Inventory inventory = event.getInventory(); // The inventory that was clicked in
		if (inventory.getName().equals(ChatColor.RED +  "Report")) {
			event.setCancelled(true);
			switch(clicked.getItemMeta().getDisplayName()) {
			case "Schelden/Pesten":
				player.closeInventory();
				reportSystem.getReportManager().createReport("Scheld/Pesten", player.getName(), clicked.getItemMeta().getLore().get(0));
				player.sendMessage(reportSystem.PREFIX + "Je hebt: " + clicked.getItemMeta().getLore().get(0) + ""
						+ " gereport voor: " + ChatColor.AQUA + "Schelden/Pesten");
				break;
			case "Hacken":
				player.closeInventory();
				reportSystem.getReportManager().createReport("Hacken", player.getName(), clicked.getItemMeta().getLore().get(0));
				player.sendMessage(reportSystem.PREFIX + "Je hebt: " + clicked.getItemMeta().getLore().get(0) + ""
						+ " gereport voor: " + ChatColor.AQUA + "Hacken");
				break;
			case "No roleplay":
				player.closeInventory();
				reportSystem.getReportManager().createReport("No roleplay", player.getName(), clicked.getItemMeta().getLore().get(0));
				player.sendMessage(reportSystem.PREFIX + "Je hebt: " + clicked.getItemMeta().getLore().get(0) + ""
						+ " gereport voor: " + ChatColor.AQUA + "No roleplay");
				break;
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPermission("ReportSystem.Admin"))
			return;
		
		int count = 0;
		
		for(Report r : reportSystem.getReportManager().getReports()) {
			if(!r.getState().isFinished()) count = count + 1;
		}
		if(count != 0)
		p.sendMessage(reportSystem.PREFIX + "Er staat: " + count + " reports open die nog behandeld moeten worden! Doe /reports all om ze te bekijken!");
	}

}
