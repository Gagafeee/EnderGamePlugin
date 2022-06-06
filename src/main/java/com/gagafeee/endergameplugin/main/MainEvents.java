package com.gagafeee.endergameplugin.main;

import me.rockyhawk.commandpanels.CommandPanels;
import me.rockyhawk.commandpanels.api.CommandPanelsAPI;
import me.rockyhawk.commandpanels.api.Panel;
import me.rockyhawk.commandpanels.openpanelsmanager.PanelPosition;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class MainEvents implements Listener {
    @EventHandler
    public static void OnPlayerClick(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            //Emerald
            if(event.hasItem()){
                if(event.getItem().getType() == Material.EMERALD){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute as " + event.getPlayer().getName() +" run function utils:spawn");
                }
            }
        }



    }
}
