package com.gagafeee.endergameplugin.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class LevelOperator implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender sender,  Command command,  String label, String[] args) {
        Bukkit.getOnlinePlayers().forEach((p) -> {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getMainScoreboard();
            Objective Xp = board.getObjective("xp");
            Score score = Xp.getScore(p.getName());
            int xp = score.getScore();

            Objective objective = board.getObjective("globalLevel");
            Score Level = objective.getScore(p.getName());
            int level = Level.getScore();
            if((xp / 100) >= (level + 1)) {
                //next level

                Score newScore = manager.getMainScoreboard().getObjective("globalLevel").getScore(p.getName());
                newScore.setScore(xp / 100);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute as " + p.getName() + " run function level:up");
            }
        });


        return false;
    }
}
