package com.gagafeee.endergameplugin.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class DatabaseExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length > 0){
            sender.sendMessage("§aOpen Connexion...");
            try {
                if(args[0].equals("test")){
                    /*database db = new database();
                    // Write a message to the database
                    FirebaseDatabase database = db.getDb();
                    // Get the database instance and store into objectDatabaseReference
                    DatabaseReference myRef = database.getReference("test");
                    // getReference() get the refrence if the refrence is already creted… if refrence is not created then it will create a new refrence here myRef.setValue(“Hello, World!”);
                    myRef.setValue("test",(databaseError, databaseReference) -> {
                        sender.sendMessage(String.valueOf(databaseError));
                    });*/

                    //Init
                    database db = new database();
                    FirebaseDatabase database = db.getDb();
                    DatabaseReference myRef = database.getReference("test");

                    myRef.setValue("hello",(databaseError, databaseReference) -> {});
                }
            } catch (Exception e) {
                sender.sendMessage("§c"+e.getMessage());
                sender.sendMessage("§c"+e);
            }


        }
        return false;
    }
}
