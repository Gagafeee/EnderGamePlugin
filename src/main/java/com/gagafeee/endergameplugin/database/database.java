package com.gagafeee.endergameplugin.database;

import com.gagafeee.endergameplugin.Main;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import org.bukkit.Bukkit;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class database {
    public static FirebaseDatabase db;
    public static DatabaseReference dbRef;
    public database() throws IOException{
        FileInputStream serviceAccount = new FileInputStream(Main.getInstance().getDataFolder() + "/ender-game-firebase-adminsdk.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://ender-game-default-rtdb.europe-west1.firebasedatabase.app")
                .build();

        //get if already exist
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        if(firebaseApps!=null && !firebaseApps.isEmpty()){
            for(FirebaseApp app : firebaseApps){
                if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
                    firebaseApp = app;
            }
        } else {
            FirebaseApp.initializeApp(options);
        }
            db = FirebaseDatabase.getInstance();
            dbRef = db.getReference();
            }

    /*public static FirebaseDatabase getDb() {
        return db;
    }*/

}
