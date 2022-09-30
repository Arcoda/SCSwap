package com.github.arcoda.SCSwap.Commands;
import com.github.arcoda.SCSwap.SCSwap;

import net.md_5.bungee.api.ChatColor;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BlockCommand implements CommandExecutor {
	
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    	@SuppressWarnings("unchecked")
		List<String> bannedList = (List<String>) SCSwap.getInstance().getConfig().getList("smp.Blocked");
    	if(args.length >= 1) {
    		if(args[0].equals("add")) {
    			if(args.length == 1) {
    				sender.sendMessage(ChatColor.DARK_RED + "Please send the name of the user to block from using the smp.");
    				return true;
    			}
    			bannedList.add(args[1]);
    			SCSwap.getInstance().getConfig().set("smp.Blocked", bannedList);
    			SCSwap.getInstance().saveConfig();
    			sender.sendMessage(ChatColor.DARK_RED + args[1] + " no longer can acess the SMP.");
    			return true;
    		} else if(args[0].equals("remove")) {
    			if(args.length == 1) {
    				sender.sendMessage(ChatColor.DARK_RED + "Please send the name of the user to unblock from using the smp.");
    				return true;
    			}
    			bannedList.remove(args[1]);
    			SCSwap.getInstance().getConfig().set("smp.Blocked", bannedList);
    			SCSwap.getInstance().saveConfig();
				sender.sendMessage(ChatColor.DARK_GREEN + args[1] + " can now acess the SMP!");
    			return true;
    		} else if(args[0].equals("list")) {
    			sender.sendMessage(ChatColor.GOLD + "The following people are blocked from joining the SMP: " + String.join(", ", bannedList) + ".");
    			return true;
    		}
    	} else {
    		return false;
    	}
    	return false;
    }
}
