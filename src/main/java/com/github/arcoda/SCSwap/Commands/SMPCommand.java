package com.github.arcoda.SCSwap.Commands;
import com.github.arcoda.SCSwap.SCSwap;

import net.md_5.bungee.api.ChatColor;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SMPCommand implements CommandExecutor {
	@SuppressWarnings("unused")
	private SCSwap plugin;

	public SMPCommand (SCSwap plugin) {
		this.plugin = plugin;
		plugin.getCommand("smp").setExecutor(this);
	}	
	@Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    	List<String> bannedList = (List<String>) this.plugin.getConfig().getList("smp.Blocked");
    	if(bannedList.contains(sender.getName())) {
    		sender.sendMessage(ChatColor.RED + "You have been banned from the SMP. Open a ticket if you wish to appeal your ban.");
    		return true;
    	} else {
    		return SCSwap.getInstance().getTeleportLib.teleportTo(sender, "Survival");
    	}
    }
}
