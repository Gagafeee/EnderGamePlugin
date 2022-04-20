package com.gagafeee.endergameplugin.quickmine.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Push implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player)
        {
            Player senderPlayer = (Player) sender;
            if(args.length != 0)
            {
                Player p = Bukkit.getPlayer(args[0]);
                if(p != null)
                {
                    if(p.isOnline())
                    {
                        if(args.length >= 2 ) {
                            int value = Integer.parseInt(args[1]);
                            if(value == 0)
                            {
                                sender.sendMessage("'Value' is equal to 0 or in not valid");
                            }
                            PushPlayer(p,value);
                        }else{
                            PushPlayer(p,(int)1.3);
                        }
                    }else {
                        sender.sendMessage("Player Was not online");
                    }

                }else {
                    sender.sendMessage("Player do not exist ");
                }
            }else{
               PushPlayer(senderPlayer,(int)1.3);
            }


        }
        return false;
    }

    public void PushPlayer(Player playerToMove, int force)
    {
        Vector unitVector = new Vector((playerToMove.getLocation().getDirection().getX()*(-1)), 0.2, (playerToMove.getLocation().getDirection().getZ()*(-1)));
        unitVector = unitVector.normalize();
        playerToMove.setVelocity(unitVector.multiply(force));

    }




}
