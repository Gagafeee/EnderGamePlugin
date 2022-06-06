package com.gagafeee.endergameplugin.database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class database {
    FirebaseDatabase db;
    public database() throws IOException {

        FirebaseOptions options = new FirebaseOptions.Builder()

                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setDatabaseUrl("https://ender-game-default-rtdb.europe-west1.firebasedatabase.app/")
                .build();

        FirebaseApp.initializeApp(options);

        db = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDb() {
        return db;
    }
}
