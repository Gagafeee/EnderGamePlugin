package com.gagafeee.endergameplugin.lobby.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

public class join implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player)
        {
            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Player senderPlayer = (Player) sender;
            if(args.length != 0)
            {
                if(args[0].equals("lobby"))
                {
                    Score score = scoreboardManager.getMainScoreboard().getObjective("EmeraldTrigger").getScore(senderPlayer.getName());
                    score.setScore(2);
                }
                if(args[0].equals("jump"))
                {
                    Score score = scoreboardManager.getMainScoreboard().getObjective("JumpTrigger").getScore(senderPlayer.getName());
                    score.setScore(1);

                    Score dsp = scoreboardManager.getMainScoreboard().getObjective("Deplacement").getScore(senderPlayer.getName());
                    dsp.setScore(1);
                }
                if(args[0].equals("quickmine"))
                {
                    Score score = scoreboardManager.getMainScoreboard().getObjective("QuickMineTrigger").getScore(senderPlayer.getName());
                    score.setScore(1);

                    Score dsp = scoreboardManager.getMainScoreboard().getObjective("Deplacement").getScore(senderPlayer.getName());
                    dsp.setScore(1);
                }
                if(args[0].equals("prophunt"))
                {
                    Score score = scoreboardManager.getMainScoreboard().getObjective("PropHuntTrigger").getScore(senderPlayer.getName());
                    score.setScore(1);

                    Score dsp = scoreboardManager.getMainScoreboard().getObjective("Deplacement").getScore(senderPlayer.getName());
                    dsp.setScore(1);
                }

            }
        }
        return false;
    }
}
