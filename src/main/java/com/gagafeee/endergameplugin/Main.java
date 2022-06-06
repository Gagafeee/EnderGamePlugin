package com.gagafeee.endergameplugin;


import com.gagafeee.endergameplugin.database.DatabaseExecutor;
import com.gagafeee.endergameplugin.database.database;
import com.gagafeee.endergameplugin.group.commands.Group;
import com.gagafeee.endergameplugin.group.functions.PushUpdate;
import com.gagafeee.endergameplugin.group.functions.Team;
import com.gagafeee.endergameplugin.lobby.LobbyEvents;
import com.gagafeee.endergameplugin.lobby.commands.join;
import com.gagafeee.endergameplugin.main.MainEvents;
import com.gagafeee.endergameplugin.main.LevelOperator;
import com.gagafeee.endergameplugin.main.ressourcesPack;
import com.gagafeee.endergameplugin.prophunt.commands.prophunt;
import com.gagafeee.endergameplugin.prophunt.event.prophuntClick;
import com.gagafeee.endergameplugin.quickmine.bat.BatEvents;
import com.gagafeee.endergameplugin.quickmine.commands.Push;
import com.gagafeee.endergameplugin.update.events.connect;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        //Init Commands
        getCommand("group").setExecutor(new Group());
        getCommand("g").setExecutor(new Group());
        getCommand("party").setExecutor(new Group());
        getCommand("p").setExecutor(new Group());
        getCommand("push").setExecutor(new Push());
        getCommand("pushupdate").setExecutor(new PushUpdate());
        getCommand("teamupdate").setExecutor(new Team());
        getCommand("join").setExecutor(new join());
        getCommand("leveloperate").setExecutor(new LevelOperator());
        getCommand("packinstall").setExecutor(new ressourcesPack());
        getCommand("prophunt").setExecutor(new prophunt());
        getCommand("db").setExecutor(new DatabaseExecutor());


        //Init Events
        getServer().getPluginManager().registerEvents(new connect(), this);
        getServer().getPluginManager().registerEvents(new BatEvents(),this);
        getServer().getPluginManager().registerEvents(new LobbyEvents(),this);
        getServer().getPluginManager().registerEvents(new prophuntClick(), this);
        getServer().getPluginManager().registerEvents(new MainEvents(), this);



        Bukkit.getConsoleSender().sendMessage("§7[§9"+getDescription().getName()+"§7]  "+ "§b --> §a Enabled Successful");
        Bukkit.getConsoleSender().sendMessage("§7[§9"+getDescription().getName()+"§7]  "+ "§b --> §a Plugin Version is : §6" + getDescription().getVersion());
        instance = this;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        //Init Group Sys
        Group g = new Group();
        ConsoleCommandSender c = Bukkit.getConsoleSender();
        g.ResetAllGroup(c);
        Bukkit.getConsoleSender().sendMessage("§7[§9"+getDescription().getName()+"§7]  "+ "§b --> §a Disabled Successful");
        super.onDisable();
    }

    public static Main getInstance() {
        return instance;
    }




}
