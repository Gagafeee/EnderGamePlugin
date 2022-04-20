package com.gagafeee.endergameplugin.group.commands;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Tag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;


public class Group implements CommandExecutor {

    PlayerGroup PlayerGroup = new PlayerGroup();
    public PlayerGroup[] Storage = {};





    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player senderPlayer = (sender instanceof Player) ? (Player) sender : null;
            if (args.length != 0) {


                if (args[0].equalsIgnoreCase("create")) {
                    if(!isInAGroup(senderPlayer)){
                        CreateGroup(senderPlayer);
                    }
                }


                if (args[0].equalsIgnoreCase("infos") || args[0].equalsIgnoreCase("info")) {
                    int GroupId = getGroupId(senderPlayer);
                    if (GroupId == -1) {
                        sender.sendMessage("§cYou are not in a group");
                    } else {
                        StringBuilder Players = new StringBuilder();
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(getGroupId(senderPlayer)));
                        Player[] pl = getPlayerList(getGroupId(senderPlayer));
                        for (int i = 0; i < pl.length; i++) {
                            Players.append(", ").append(pl[i].getName());
                        }
                        String msg = ("§3------ \n"
                                + "§bInfos \n" + "Group Id :§a" + GroupId + "§7 pos:(" + (GroupId - 1) + ")" + "\n"
                                + "§bGroup Member :("+ pl.length +") §r" + Players +
                                "\n§3------");
                        sender.sendMessage(msg);
                    }
                }


                if (args[0].equalsIgnoreCase("reset")) {
                    ResetAllGroup(sender);
                }


                if (args[0].equalsIgnoreCase("get")) {
                    sender.sendMessage(String.valueOf(GetGroupCount()));
                    sender.sendMessage(Arrays.toString(getPlayerList(getGroupId(senderPlayer))));
                    sender.sendMessage(String.valueOf(getPlayerList(getGroupId(senderPlayer)).length));
                }


                if (args[0].equalsIgnoreCase("add")) {
                    if (args.length >= 2) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p == null)
                        {
                            sender.sendMessage("§4Oups:§c Player: §b" + args[1] + "§c Do not exist or it was not online");
                        }else if (p.isOnline() && !args[1].equals(sender.getName())) {
                            //TODO: send invite to player
                            Player playerToInvite = Bukkit.getPlayer(args[1]);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                    "tellraw " +
                                            playerToInvite.getName() +
                                            " [\"\",{\"text\":\"---------------------------------\",\"color\":\"gold\"},{\"text\":\"\\n\"},{\"text\":\" You are invited to the \",\"color\":\"yellow\"},{\"text\":\""+
                                            sender.getName()+" \",\"color\":\"green\"},{\"text\":\"party\",\"color\":\"yellow\"}," +
                                            "{\"text\":\"\\n\"},{\"text\":\"         \",\"color\":\"gold\"},{\"text\":\"[\",\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"p accept "+
                                            sender.getName()+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"§b/p accept "+sender.getName()+"\"}},{\"text\":\"Accept\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/p accept "+
                                            sender.getName()+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"§b/p accept "+
                                            sender.getName()+"\"}},{\"text\":\"]\",\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/p accept "+
                                            sender.getName()+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"§b/p accept "+
                                            sender.getName()+"\"}},{\"text\":\"     \",\"color\":\"gold\"},{\"text\":\"[\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/p reject "+
                                            sender.getName()+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"§b/p reject "+
                                            sender.getName()+"\"}},{\"text\":\"Reject\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/p reject "+
                                            sender.getName()+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"§b/p reject "+
                                            sender.getName()+"\"}},{\"text\":\"]\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/p reject "+
                                            sender.getName()+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"§b/p reject "+
                                            sender.getName()+"\"}},{\"text\":\"\\n\"},{\"text\":\"---------------------------------\",\"color\":\"gold\"}]");
                            String c = "tag " + p.getName() + " add " + "InvitedBy." + sender.getName();
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);

                        }else{
                            if(!p.isOnline()) {sender.sendMessage("§cPlayer was not online");}
                            if(args[1].equals(sender.getName())) {sender.sendMessage("§cYou cannot add yourself in your group");}
                            else{sender.sendMessage("§4InternalError");}
                        }
                    } else {
                        sender.sendMessage("§cPlease put a player name");
                    }
                }
                if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("a")){
                    if(args.length >= 2)
                    {
                        Set<String> tags = senderPlayer.getScoreboardTags();
                        Player playerRequest = Bukkit.getPlayer(args[1]);
                        if(tags.contains("InvitedBy."+playerRequest.getName())){
                            //If player has not a Group create it before add other player
                            if (!isInAGroup(senderPlayer)) {

                                if (!isInAGroup(playerRequest)) {
                                    CreateGroup(playerRequest);
                                }
                                Storage[(getGroupId(playerRequest)-(1))] = AddPlayerToGroup(senderPlayer, playerRequest);
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tag " + senderPlayer.getName() + " remove InvitedBy."+ playerRequest.getName());
                                playerRequest.sendMessage("§aPlayer §b"+senderPlayer.getName()+" was added");
                                sender.sendMessage("§aYou have accepted "+ playerRequest.getName()+"'s request");
                            } else {
                                sender.sendMessage("§cYour are already in a group : §e" + getGroupId(senderPlayer));
                            }
                        }else {
                            sender.sendMessage("§c You have not been invited by this player");
                        }
                    }else {
                        sender.sendMessage("§cPlease specifies player Name");
                    }
                }
                if (args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("remove")) {
                    if (args.length >= 2) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p == null)
                        {
                            sender.sendMessage("§4Oups:§c Player: §b" + args[1] + "§c Do not exist or it was not online");
                        }else if (p.isOnline() && !args[1].equals(sender.getName())) {
                            //If player has not a Group create it before add other player
                            if (getGroupId(p) == getGroupId(senderPlayer)) {
                                Storage[(getGroupId(senderPlayer)-1)] = RemovePlayerFromGroup(p,sender);
                            } else {
                                sender.sendMessage("§cPlayer was not in this group : §e" + getGroupId(p));
                            }
                        }else{
                            if(!p.isOnline()) {sender.sendMessage("§cPlayer was not online");}
                            //TODO: if player was = sender run /leave
                            else{sender.sendMessage("§4InternalError");}
                        }
                    } else {
                        sender.sendMessage("§cPlease put a player name");
                    }
                }
            }

        } else {
            sender.sendMessage("no args");
                /*ScoreboardManagerScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard board = manager.getMainScoreboard();
                Objective objective = board.getObjective("test");
                Score score = objective.getScore("John");
                sender.sendMessage(Integer.toString(score.getScore()));*/


        }
        return false;
    }

    public void ResetAllGroup(CommandSender sender)
    {
        //Sync Group before check
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objective = board.getObjective("GroupModule");
        Score number = objective.getScore("Number");
        SetGroupCount(number.getScore());

        if(GetGroupCount() == 0) {
            sender.sendMessage("§cNothing to remove");
        }else{
            SetGroupCount(0);
            for (int i = 0; i < Storage.length; i++) {
                for (int j = 0; j < Storage[i].players.length; j++) {
                    Player p = Storage[i].players[j];
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    String c = "tag " + p.getName() + " remove " + "Group." + (i+1);
                    Bukkit.dispatchCommand(console, c);
                }
            }
            Storage = new PlayerGroup[]{};
            Bukkit.getConsoleSender().sendMessage(String.valueOf(Storage));
            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Score score = scoreboardManager.getMainScoreboard().getObjective("GroupModule").getScore("Number");
            score.setScore(0);
            sender.sendMessage("§aReset Successful");
        }
    }

    public boolean isInAGroup(Player player)
    {
        switch (getGroupId(player))
        {
            case -1 :{return false;}
            default:{return true;}
        }
    }

    public Player[] getPlayerList(int GroupId)
    {

        Player[] list = new Player[Storage[GroupId-1].players.length];

        for (int i = 0; i < Storage[(GroupId-1)].players.length; i++) {
            list = addPlayer(list.length,list, Storage[(GroupId-1)].players[i]);
        }
        list = removeNull(list);
        return list;
    }

    public Player[] removeNull(Player[] a) {
        ArrayList<Player> removedNull = new ArrayList<Player>();
        for (Player str : a)
            if (str != null)
                removedNull.add(str);
        return removedNull.toArray(new Player[0]);
    }



    public int getGroupId(Player player)
    {
        int GroupId = -1;
        Bukkit.getConsoleSender().sendMessage(String.valueOf(GetGroupCount()));
        for (int i = 0; i < GetGroupCount(); i++) {
            for (int j = 0; j < player.getPlayer().getScoreboardTags().size(); j++) {
                String g = "Group." + (i + 1);
                if (player.getPlayer().getScoreboardTags().toArray()[j].toString().equalsIgnoreCase(g)) {
                    Bukkit.getConsoleSender().sendMessage("testing for: " +player.getPlayer().getScoreboardTags().toArray()[j].toString() +" ?= "+g);
                    GroupId = i + 1;
                    break;
                }
            }

        }
        return GroupId;
    }


    public void CreateGroup(Player senderPlayer)
    {
        CommandSender sender = senderPlayer;
        //create group
        PlayerGroup group = new PlayerGroup();
        //set values
        group.Id = GetGroupCount() + 1;
        //convert CommandSender to player
        group.players = addPlayer(group.players.length, group.players, senderPlayer);

        sender.sendMessage("§bNew Group Created | Your Group Id is §a" + group.Id +"\n" + "Member " + "[" + group.players.length +"] : " + group.players[0].getDisplayName() );
        //add group to storage
        Storage = addPlayerGroup(Storage, group);
        //update score
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Score score = scoreboardManager.getMainScoreboard().getObjective("GroupModule").getScore("Number");
        score.setScore(GetGroupCount() + 1);
        //assign player
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String command = "tag " + sender.getName() + " add " + "Group." + group.Id;
        Bukkit.dispatchCommand(console, command);
        //set score
        SetGroupCount(GetGroupCount()+1);
        console.sendMessage("Added Group["+group.Id+"] In storage ("+GetGroupCount()+"):" + Arrays.toString(Storage));
    }

    public PlayerGroup AddPlayerToGroup(Player playerToAdd,Player partyOwner)
    {
        PlayerGroup newGroup = new PlayerGroup();

        newGroup.Id = getGroupId(partyOwner);

        Player[] newPlayerList = getPlayerList(getGroupId(partyOwner));
        newPlayerList = addPlayer(newPlayerList.length,newPlayerList,playerToAdd);
        newGroup.players = newPlayerList;

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String command = "tag " + playerToAdd.getName() + " add " + "Group." + newGroup.Id;
        Bukkit.dispatchCommand(console, command);

        return newGroup;

    }

    public PlayerGroup RemovePlayerFromGroup(Player playerToRemove,CommandSender sender)
    {
        Player senderPlayer = (sender instanceof Player) ? (Player) sender : null;
        PlayerGroup newGroup = new PlayerGroup();

        newGroup.Id = getGroupId(senderPlayer);

        Player[] newPlayerList = getPlayerList(getGroupId(senderPlayer));
        newPlayerList = addPlayer(newPlayerList.length,newPlayerList,playerToRemove);
        newPlayerList = (Player[]) ArrayUtils.remove(newPlayerList, ArrayUtils.indexOf(Storage[(getGroupId(senderPlayer)-1)].players,playerToRemove));
        newGroup.players = newPlayerList;

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String command = "tag " + playerToRemove.getName() + " remove " + "Group." + newGroup.Id;
        Bukkit.dispatchCommand(console, command);

        return newGroup;
    }


    // Function to add x in arr
    public static PlayerGroup[] addPlayerGroup(PlayerGroup[] storage, PlayerGroup groupToAdd)
    {
        Bukkit.getConsoleSender().sendMessage("Length :" + storage.length);

        // create a new array of size n+1
        PlayerGroup[] newarr = new PlayerGroup[storage.length + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (int i = 0; i < storage.length; i++) {
            newarr[i] = storage[i];
        }

        newarr[storage.length] = groupToAdd;

        Bukkit.getConsoleSender().sendMessage("Storage: " +storage + " = new Storage : " + newarr);
        return newarr;
    }

    public static Player[] addPlayer(int arraySize, Player[] arr, Player playerToAdd)
    {
        // create a new array of size n+1
        Player[] newarr = new Player[arraySize + 1];

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

    public int GetGroupCount()
    {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

        Objective objective = board.getObjective("GroupModule");
        Score score = objective.getScore("Number");
        return score.getScore();
    }
    public void SetGroupCount(int value){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

        Objective objective = board.getObjective("GroupModule");
        Score score = objective.getScore("Number");
        score.setScore(value);
    }


}
