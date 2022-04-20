package com.gagafeee.endergameplugin.group.functions;

import com.gagafeee.endergameplugin.quickmine.commands.Push;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PushUpdate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player[] playerList = new Player[0];
        Player[] serverPlayerList = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        Push push = new Push();
        for (int i = 0; i < serverPlayerList.length; i++) {
            if(serverPlayerList[i].getScoreboardTags().contains("QMPushTrigger"))
            {
                playerList = addPlayer(playerList.length, playerList,serverPlayerList[i]);
            }
        }
        for (int i = 0; i < playerList.length; i++) {
            push.PushPlayer(playerList[i],(int)1.3);
            playerList[i].getScoreboardTags().remove("QMPushTrigger");
            Bukkit.getServer().getPlayer(playerList[i].getName()).sendMessage("§cThis zone is not accessible for you :(");
        }

        return false;
    }


    public static Player[] addPlayer(int s, Player[] t, Player pl)
    {
        Player[] u = new Player[s + 1];
        for (int i = 0; i < s; i++) {u[i] = t[i];}
        u[s] = pl;
        return u;
    }
}
