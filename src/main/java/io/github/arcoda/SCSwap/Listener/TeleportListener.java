package io.github.arcoda.SCSwap.Listener;

import com.onarandombox.MultiversePortals.event.MVPortalEvent;
import io.github.arcoda.SCSwap.SCSwap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class TeleportListener implements Listener {
    private static SCSwap plugin = SCSwap.getInstance();
    @EventHandler
    public void OnTeleport(MVPortalEvent event) {
        Player player = event.getTeleportee();
        plugin.devLog(player.getName()+" is being teleported to "+event.getDestination().getName()+" from "+event.getSendingPortal().getName());
        if (event.getSendingPortal().getName().equals(plugin.getConfig().getString("Portal.To"))) {
            plugin.getTeleportLib.teleportLogic(true, player);
            player.setBedSpawnLocation(event.getDestination().getLocation(player).add(2, 0 ,0), true);
        }
        else if (event.getSendingPortal().getName().equals(plugin.getConfig().getString("Portal.From"))) {
            plugin.getTeleportLib.teleportLogic(false, player);
            player.setBedSpawnLocation(event.getDestination().getLocation(player).add(0, 0 ,2), true);
        }
    }
}
