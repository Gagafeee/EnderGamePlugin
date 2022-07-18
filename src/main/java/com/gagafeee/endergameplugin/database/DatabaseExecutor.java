package com.gagafeee.endergameplugin.database;

import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length > 0){
            try {
                sender.sendMessage("§aOpen Connexion...");
                database database = new database();
                if(args[0].equals("test")){

                }
                /*if(args[0].equals("set")){
                    if(args.length >= )
                }*/
                if(args[0].equals("link")){
                    if(args.length > 1){
                        getUserId(args[1], uid -> {
                            get("users/" + uid +"/isLinked", (isLinked) -> {
                                if(!isLinked.equals("true")) {
                                    CreateNotification(uid, "<b>" + sender.getName() + "</b>" + " vous envoie une demande de connexion : si c'est votre compte, cliquez sur lier, sinon, cliquez sur la croix", 3, sender.getName());
                                    sender.sendMessage("§aA link notification was sended to §b" + args[1] + "§e, Go to your account...");
                                }else{
                                    get("users/" + uid + "/linked/to", (userName) -> {
                                        sender.sendMessage("§cThe selected account is already linked to §6"+userName+"§c!");
                                    });

                                }
                            });
                        });

                    }else {
                        sender.sendMessage("§cYou must precise an username");
                    }
                }
                if(args[0].equals("getWaiting")){
                    sender.sendMessage("§bFetching...");
                    getList("waiting/", (waitingDataList) -> {
                        for (String[] waitingData: waitingDataList){
                            switch (waitingData[1].substring(waitingData[1].indexOf(":"))){
                                case ":link" -> {
                                    String uid = waitingData[1].substring(0,waitingData[1].indexOf(":"));
                                    get("users/" + uid + "/linked/to", (mcUName) -> {
                                        get("users/" + uid + "/name", (userName) -> {
                                            Bukkit.getServer().getPlayer(mcUName).sendMessage("§aVotre compte Minecraft (§b" + mcUName +"§a) a été associé à votre compte EnderGame (§b" + userName +"§a)");
                                            Delete("waiting/" + waitingData[0]);
                                        });
                                    });
                                }
                            }
                        }
                    });
                }
            } catch (Exception e) {
                sender.sendMessage("§c"+e.getMessage());
                sender.sendMessage("§c"+e);
            }
            sender.sendMessage("§aClosed Connexion");


        }
        return false;
    }
    /*public static Boolean SetValue(String PlayerName, String path, Object value){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference(path);
        ref.setValue(value,((databaseError, databaseReference) -> Bukkit.getPlayer(PlayerName).sendMessage(databaseError.getMessage())));
        return false;
    }*/

    public void SetValue(String path, Object value){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(path);
        ref.setValue(value,(databaseError, databaseReference) -> {if(databaseError != null){Bukkit.getServer().getConsoleSender().sendMessage("§c" + databaseError);}});
    }

    public interface Callback {
        void onCallback(String value);

    }

    public interface ListCallback {
        void onCallback(List<String[]> value);

    }

    public void Delete(String path){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(path);
        ref.removeValue((databaseError, databaseReference) -> {if(databaseError != null){Bukkit.getServer().getConsoleSender().sendMessage("§c" + databaseError);}});
    }

    public void get(String path, Callback callback){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(path);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onCallback(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    public void getList(String path, ListCallback callback){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(path);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String[]> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String[] data = {ds.getKey(), (String) ds.getValue()};
                    list.add(data);
                }
                callback.onCallback(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }


    public void CreateNotification(String uid, String content, int type, @Nullable String mcUserName){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        getList("users/" + uid + "/notifications", notificationIndexList -> {
            int o = -1;
            for (String[] notificationIndex: notificationIndexList){
                if(Integer.parseInt(notificationIndex[0]) > o) o = Integer.parseInt(notificationIndex[0]);
            }
            //new notif = o+1
            Notification n = new Notification();
            n.id = o+1;
            n.content = content;
            n.date = dtf.format(now);
            n.type = type;
            if(mcUserName != null) n.mcUserName = mcUserName;
            SendNotification(n, uid);
        });

        
    }

    public void SendNotification(Notification n, String reciverUid){
        DatabaseReference NRef = FirebaseDatabase.getInstance().getReference().child("users/" + reciverUid + "/notifications/" + n.id);
        NRef.child("content").setValue(n.content,(databaseError, databaseReference) -> {if(databaseError != null){Bukkit.getServer().getConsoleSender().sendMessage("§c" + databaseError);}});
        NRef.child("date").setValue(n.date,(databaseError, databaseReference) -> {if(databaseError != null){Bukkit.getServer().getConsoleSender().sendMessage("§c" + databaseError);}});
        NRef.child("hasReaded").setValue(n.hasReaded,(databaseError, databaseReference) -> {if(databaseError != null){Bukkit.getServer().getConsoleSender().sendMessage("§c" + databaseError);}});
        NRef.child("type").setValue(n.type,(databaseError, databaseReference) -> {if(databaseError != null){Bukkit.getServer().getConsoleSender().sendMessage("§c" + databaseError);}});
        if(n.mcUserName != null) NRef.child("mcuname").setValue(n.mcUserName,(databaseError, databaseReference) -> {if(databaseError != null){Bukkit.getServer().getConsoleSender().sendMessage("§c" + databaseError);}});
        if(n.type == 3) NRef.child("linked").setValue(n.isLinked,(databaseError, databaseReference) -> {if(databaseError != null){Bukkit.getServer().getConsoleSender().sendMessage("§c" + databaseError);}});
    }
    public  String getUserId(String UserName, Callback myCallback){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = firebaseDatabase.getReference().child("users");
        String[] uidList;
        ValueEventListener valueEventListener = new ValueEventListener() {
            String[] uidList;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get uidList
                List<String> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.getKey();
                    list.add(uid);
                }
                uidList = list.toArray(new String[0]);

                //debug
                /*for (String element: uidList){
                    Bukkit.getPlayer("gagafeee").sendMessage(element);
                }
                //*****/
                //pour chaque id récup
                for(String uid: uidList){
                    DatabaseReference userRef = firebaseDatabase.getReference().child("users/" + uid);

                    ValueEventListener valueEventListener = new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //get uidList
                            List<String[]> l = new ArrayList<>();
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                String[] data = {ds.getKey(),ds.getValue().toString()};
                                l.add(data);
                            }

                            //get if name = name
                            for (String[] element: l){
                                //Bukkit.getPlayer("gagafeee").sendMessage("  "+element[0] + " : " + element[1]);
                                if(element[0].equals("name") && element[1].equals(UserName)){
                                    myCallback.onCallback(uid);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Bukkit.getConsoleSender().sendMessage("§c" + databaseError.getMessage()); //Don't ignore errors!
                        }

                    };
                    userRef.addListenerForSingleValueEvent(valueEventListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Bukkit.getConsoleSender().sendMessage("§c" + databaseError.getMessage()); //Don't ignore errors!
            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);


        //---------------------------------------------
        return "";
    }


    /*public static Boolean SendNotification(String PlayerName, String content, String type){
        SetValue(PlayerName,"/user/" + userId + "/notifications/" + (getNotificationCount(PlayerName) + 1), content);
    }*/

}


