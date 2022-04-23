package com.gagafeee.endergameplugin.prophunt.commands;

import com.gagafeee.endergameplugin.Main;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boss;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import static java.lang.Math.random;

public class prophunt implements CommandExecutor {
    MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
    MVWorldManager worldManager = core.getMVWorldManager();

    String[][] Blocks = {{"Deepslate","Moss","Oak_log","Clay"},{},{},{},{},{}};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length > 0){

            if(args[0].equals("setNewGame")){
                Boolean[] maps = new Boolean[6];
                for (int i = 0; i < maps.length; i++) {
                    maps[i] = mapIsAvailable(i);
                }
                if (!maps[0] && !maps[1] && !maps[2] && !maps[3] && !maps[4] && !maps[5]){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "function prophunt:closegame");
                    sender.sendMessage("map available not found");
                }else {
                    //TODO: get all player waiting and set tag/score
                    Player[] playerList = new Player[Bukkit.getOnlinePlayers().size()];
                    for (int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) {
                        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
                        Player p = (Player) Bukkit.getOnlinePlayers().toArray()[i];
                        Score Locationscore = scoreboardManager.getMainScoreboard().getObjective("Location").getScore(p.getName());
                        Score IsInGame = scoreboardManager.getMainScoreboard().getObjective("PHIsInGame").getScore(p.getName());
                        if(Locationscore.getScore() == 3 && IsInGame.getScore() == 0){
                            playerList[i] = p;
                        }
                    }

                    playerList = removeNull(playerList);
                    int SelectedMapId = (int) (Math.random() * (maps.length));
                    while (!maps[SelectedMapId]){
                       SelectedMapId = (int) (Math.random() * (maps.length));
                    }

                    Bukkit.getPlayer("gagafeee").sendMessage("map is: " + Arrays.toString(maps));
                    Bukkit.getPlayer("gagafeee").sendMessage("selected map is : " + SelectedMapId);
                    for (int i = 0; i < playerList.length; i++) {
                        playerList[i].addScoreboardTag("PHG."+SelectedMapId);
                    }
                    Player finder = playerList[(int) (Math.random() * playerList.length)];
                    if(!finder.getScoreboardTags().contains("PHFORCENOTFINDER")){
                        finder.addScoreboardTag("finder");
                    }
                    for (int i = 0; i < playerList.length; i++) {
                        if(!playerList[i].getScoreboardTags().contains("finder")){
                            String type = "PHB."+Blocks[SelectedMapId][(int) (Math.random() * Blocks[SelectedMapId].length)];
                            playerList[i].addScoreboardTag(type);
                            Bukkit.getPlayer("gagafeee").sendMessage("Block type of "+playerList[i].getName()+" :  " + type);
                        }
                    }

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "function prophunt:startnewgame");
                }
            }
            if(args[0].equals("loadmap")){

                if(args.length > 1){
                    switch (args[1]) {
                        case "0" -> worldManager.loadWorld("pforest");
                        case "1" -> worldManager.loadWorld("pmontain");
                        case "2" -> worldManager.loadWorld("pnether");
                        case "3" -> worldManager.loadWorld("pocean");
                        case "4" -> worldManager.loadWorld("pmagic");
                        case "5" -> worldManager.loadWorld("ptemple");
                    }
                }
            }
            if(args[0].equals("unloadmap")){

                if(args.length > 1){
                    switch (args[1]) {
                        case "0" -> worldManager.unloadWorld("pforest");
                        case "1" -> worldManager.unloadWorld("pmontain");
                        case "2" -> worldManager.unloadWorld("pnether");
                        case "3" -> worldManager.unloadWorld("pocean");
                        case "4" -> worldManager.unloadWorld("pmagic");
                        case "5" -> worldManager.unloadWorld("ptemple");
                    }
                }
            }
            if(args[0].equals("updateBossBar")){
                ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
                Score score = scoreboardManager.getMainScoreboard().getObjective("PHFounded").getScore("Forest");
                BossBar bar = Bukkit.getServer().getBossBar(new NamespacedKey(Main.getInstance(), "ph_forest"));
                bar.setProgress(score.getScore()*1.0);
            }
        }
        return false;
    }

    public boolean mapIsAvailable(int mapId){
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Score score = scoreboardManager.getMainScoreboard().getObjective("PHMaps").getScore(String.valueOf(mapId));
        switch (score.getScore()){
            case 0:{
                return true;
            }
            case 1:
            default:{
                return false;
            }
        }
    }

    public static Boolean[] addmap(int arraySize, Boolean[] arr, Boolean playerToAdd)
    {
        // create a new array of size n+1
        Boolean[] newarr = new Boolean[arraySize + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (int i = 0; i < arraySize; i++) {
            newarr[i] = arr[i];
        }

        newarr[arraySize] = playerToAdd;

        return newarr;
    }

    public Player[] removeNull(Player[] a) {
        ArrayList<Player> removedNull = new ArrayList<Player>();
        for (Player str : a)
            if (str != null)
                removedNull.add(str);
        return removedNull.toArray(new Player[0]);
    }
}
