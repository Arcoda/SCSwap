package com.github.arcoda.SCSwap.Commands.Tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SCSwapTabComplete implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> baseList = new ArrayList<String>();
        if (args.length == 1) {
            baseList.add("reload");
            baseList.add("about");
            baseList.add("block");
            return baseList;
        }
        if (args[0] == "block") {
            baseList.add("add");
            baseList.add("list");
            baseList.add("remove");
            return baseList;
        }
        return(new ArrayList<>());
    }
}
