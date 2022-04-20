package com.gagafeee.endergameplugin.update.events;

import com.gagafeee.endergameplugin.main.ressourcesPack;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class connect implements Listener {


    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        //effects
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 255));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999, 255));

        ressourcesPack.getRessourcepack(player.getName());
    }



    @EventHandler
    public static void onPlayerDisconnect(PlayerQuitEvent event){
        Player player = event.getPlayer();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        org.bukkit.scoreboard.Team team = scoreboard.registerNewTeam("disconnected");
        team.addEntry(player.getName());



    }
}
