package com.gagafeee.endergameplugin.lobby;

import com.gagafeee.endergameplugin.Main;
import me.rockyhawk.commandpanels.CommandPanels;
import me.rockyhawk.commandpanels.api.CommandPanelsAPI;
import me.rockyhawk.commandpanels.api.Panel;
import me.rockyhawk.commandpanels.api.PanelClosedEvent;
import me.rockyhawk.commandpanels.openpanelsmanager.PanelPosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Objects;

public class LobbyEvents implements Listener{

    @EventHandler
    public static void OnPlayerClick(PlayerInteractEvent event){
        CommandPanelsAPI api = CommandPanels.getAPI();
        Player player = event.getPlayer();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

        Objective objective = board.getObjective("Location");
        Score score = objective.getScore(player.getName());
        if(score.getScore() == 0){
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                //EnderPearl
                if(event.hasItem()){
                    if(event.getItem().getType() == Material.ENDER_PEARL){
                        Panel panel = api.getPanel("selectgame");
                        if(!api.isPanelOpen(player)){
                            panel.open(player, PanelPosition.Top);

                        }

                    }
                }
                //player head
                if(event.hasItem()){
                    if(event.getItem().getType() == Material.PLAYER_HEAD && event.getItem().containsEnchantment(Enchantment.KNOCKBACK)){
                        Panel panel = api.getPanel("profile");
                        if(!api.isPanelOpen(player)){
                            panel.open(player, PanelPosition.Top);
                        }
                    }
                }


            }
        }
    }

    @EventHandler
    public void onPanelClose(PanelClosedEvent event){

        if(event.getPanel().getName().equals("selectgame") || event.getPanel().getName().equals("profile")){
            Bukkit.getConsoleSender().sendMessage(event.getPanel().getName());
            Bukkit.dispatchCommand (Bukkit.getConsoleSender(), "execute as " + event.getPlayer().getName() +" run function utils:setitems");
        }
    }

    @EventHandler
    public void onPlayerDie(PlayerRespawnEvent event){
        Player p = event.getPlayer();
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Score score = scoreboardManager.getMainScoreboard().getObjective("Location").getScore(p.getName());
        //if player is in Lobby [L:0]
        if(score.getScore() == 0){
            Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
                p.setHealth(20);
                p.teleport(new Location(Bukkit.getWorld("spawn"),-262, 172, 73));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute as "+ p.getName()+" run function utils:spawn");
            }, 0L);




        }
    }

    @EventHandler
    public void onPlayerTakeDamage(EntityDamageEvent event) {
        if(event.getEntityType() == EntityType.PLAYER) {
            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Score score = scoreboardManager.getMainScoreboard().getObjective("Location").getScore(event.getEntity().getName());
            if(score.getScore() == 0 || score.getScore() == 1 || score.getScore() == 3) event.setCancelled(true);
        }
    }

    public static void UpdateSkins()
    {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(board.getObjective("Location").getScore(onlinePlayer.getName()).getScore() == 0){
                Inventory inventory = onlinePlayer.getInventory();
                for (int i = 0; i < inventory.getStorageContents().length; i++) {
                    ItemStack itemStack = inventory.getStorageContents()[i];
                    if(itemStack != null) {
                        if(itemStack.getType() == Material.PLAYER_HEAD && itemStack.getItemMeta().hasDisplayName()){
                            if(itemStack.containsEnchantment(Enchantment.MENDING)){
                                SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
                                meta.setOwningPlayer(onlinePlayer);
                                meta.removeEnchant(Enchantment.MENDING);
                                meta.addEnchant(Enchantment.KNOCKBACK,1,false);
                                itemStack.setItemMeta(meta);
                            }
                        }
                    }
                }
            }
        }
    }
}
