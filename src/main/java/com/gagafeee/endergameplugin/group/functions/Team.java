package com.gagafeee.endergameplugin.group.functions;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Objects;

public class Team implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        OfflinePlayer[] list = Bukkit.getServer().getOfflinePlayers();
        for (int i = 0; i < list.length; i++) {
            org.bukkit.scoreboard.Team[] p = list[i].getPlayer().getScoreboard().getTeams().toArray(new org.bukkit.scoreboard.Team[0]);
            String playerTeams = Arrays.toString(p);
            if(!Objects.equals(playerTeams, "disconnected")){
                sender.sendMessage("Player: " + list[i].getPlayer().getDisplayName() + " Was not in team disconnected");
            }
        }
        return false;
    }
}
