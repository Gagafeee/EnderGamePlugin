package com.gagafeee.endergameplugin.group.functions;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class TeamUpdate implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Bukkit.getConsoleSender().sendMessage(event.getPlayer().getName() + "Player was disconnected");
    }

}
