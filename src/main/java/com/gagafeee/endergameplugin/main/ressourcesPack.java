package com.gagafeee.endergameplugin.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;

public class ressourcesPack implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length > 1){
            Player player = Bukkit.getPlayer(args[1]);
            if(player != null){
                switch (args[0]) {
                    case "install" -> {
                        try {
                            player.setResourcePack("http://resourcepack.host/dl/v03Rf4RWLXFk2eazgF0DTYafMrVF51kB/EnderGame+pack+V4.zip");
                        } catch (Exception e) {
                            player.sendMessage("§c" + e.getMessage());
                        }
                        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
                        Score score = scoreboardManager.getMainScoreboard().getObjective("Allowpack").getScore(sender.getName());
                        if (score.getScore() == 0) {
                            score.setScore(1);
                        }
                    }
                    case "disable" -> {
                        try {
                            player.setResourcePack("http://resourcepack.host/dl/4SRpmXaDQhCNZtrCXHNKdCZKxns0FQPM/emptypack.zip");
                        } catch (Exception e) {
                            player.sendMessage("§c" + e.getMessage());
                        }
                        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
                        Score score = scoreboardManager.getMainScoreboard().getObjective("Allowpack").getScore(sender.getName());
                        if (score.getScore() == 1) {
                            score.setScore(0);
                            player.sendMessage("§aPack Disabled");
                        } else {
                            player.sendMessage("§cPack is already disabled");
                        }
                    }
                }
            }else {
                sender.sendMessage("Player don't exist");
            }


        }
        return false;
    }


    public static void getRessourcepack(String playerName){
        Player player = Bukkit.getPlayer(playerName);
        if(player != null)
        {
            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Score score = scoreboardManager.getMainScoreboard().getObjective("Allowpack").getScore(playerName);
            if(score.getScore() == 0){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + playerName +" [\"\",{\"text\":\"----------------------------------\",\"color\":\"yellow\"},{\"text\":\"\\n\\n\"},{\"text\":\"\\u25b6\",\"color\":\"dark_red\"},{\"text\":\" Auto Resource pack install is \",\"color\":\"aqua\"},{\"text\":\"disabled\",\"color\":\"red\"},{\"text\":\" \\u25c0\",\"color\":\"dark_red\"},{\"text\":\"\\n\"},{\"text\":\"         Please\",\"color\":\"aqua\"},{\"text\":\" \",\"color\":\"green\"},{\"text\":\"[Active it]\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/packinstall\"}},{\"text\":\"\\n\\n\"},{\"text\":\"----------------------------------\",\"color\":\"yellow\"}]");
            }
            if(score.getScore() == 1){
                try {
                    player.setResourcePack("http://resourcepack.host/dl/v03Rf4RWLXFk2eazgF0DTYafMrVF51kB/EnderGame+pack+V4.zip");
                }catch (Exception e){
                    player.sendMessage("§c" + e.getMessage());
                }
            }
        }else{
            Bukkit.getConsoleSender().sendMessage("§eThe player is not online");
        }

    }

}
