package com.gagafeee.endergameplugin.prophunt.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class prophuntClick implements Listener {

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event){
        if(event.getHand() == EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

        Score score = scoreboardManager.getMainScoreboard().getObjective("PHIsInGame").getScore(player.getName());
        Score isEntity = scoreboardManager.getMainScoreboard().getObjective("PHtarget").getScore(event.getPlayer().getName());

        if(score.getScore() == 1 && player.getScoreboardTags().contains("finder")){
            if(event.getRightClicked().getType() == EntityType.FALLING_BLOCK || event.getRightClicked().getType() == EntityType.PLAYER){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute as " + event.getPlayer().getName() +" run function prophunt:map/0/find");
                isEntity.setScore(1);
            }
        }
    }









}
