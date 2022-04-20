package com.gagafeee.endergameplugin.quickmine.bat;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class BatEvents implements Listener {

    @EventHandler
    public static void onBatDie(EntityDeathEvent event)
    {
        LivingEntity e = event.getEntity();
        int[] scoreTable = new int[] {0, 10, 20, 40, 70, 500};


        if(e.getScoreboardTags().contains("QMBat"))
        {

            String[] tags = e.getScoreboardTags().toArray(new String[0]);

                int index = -1;
                for (int i = 0; (i < tags.length) && (index == -1); i++) {
                    for (int j = 0; j < scoreTable.length; j++) {
                        if (tags[i].equals(String.valueOf(j))) {
                            index = i;
                        }
                    }

                }

            int rarity = Integer.parseInt(tags[index]);


            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getMainScoreboard();
            Objective objective = board.getObjective("QMScore");
            Score currentScore = objective.getScore(e.getKiller().getName());

            Score score = manager.getMainScoreboard().getObjective("QMScore").getScore(e.getKiller().getName());
            score.setScore(currentScore.getScore() + scoreTable[rarity]);
            e.getKiller().giveExpLevels(scoreTable[rarity]);
        }
    }
}
