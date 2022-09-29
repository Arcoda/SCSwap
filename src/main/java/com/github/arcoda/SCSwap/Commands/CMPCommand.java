package com.github.arcoda.SCSwap.Commands;
import com.github.arcoda.SCSwap.SCSwap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CMPCommand implements CommandExecutor {
	@SuppressWarnings("unused")
	private SCSwap plugin;

	public CMPCommand (SCSwap plugin) {
		this.plugin = plugin;
		plugin.getCommand("cmp").setExecutor(this);
	}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return SCSwap.getInstance().getTeleportLib.teleportTo(sender, "Creative");
    }
}
