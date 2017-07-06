package nl.gewoonhdgaming.report.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ReportGUI {
	
	public Inventory inv;
		
	public void openInv(Player p, String victim) { 
		inv = Bukkit.createInventory(null, 9,ChatColor.RED +  "Report");
		ItemStack swearing = new ItemStack(Material.ANVIL);
		ItemMeta swearingMeta = swearing.getItemMeta();
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(victim);
		swearingMeta.setLore(Lore);
		swearingMeta.setDisplayName(ChatColor.RED + "Schelden/Pesten");
		swearing.setItemMeta(swearingMeta);
		
		ItemStack hacking = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta hackingMeta = hacking.getItemMeta();
		hackingMeta.setLore(Lore);
		hackingMeta.setDisplayName(ChatColor.RED + "Hacken");
		hacking.setItemMeta(hackingMeta);
		
		ItemStack norp = new ItemStack(Material.BARRIER);
		ItemMeta norpMeta = norp.getItemMeta();
		norpMeta.setLore(Lore);
		norpMeta.setDisplayName(ChatColor.RED + "No roleplay");
		norp.setItemMeta(norpMeta);
		
		inv.setItem(3, swearing);
		inv.setItem(4, hacking);
		inv.setItem(5, norp);
		p.openInventory(inv);
	}

}
