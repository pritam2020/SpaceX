package com.example.Spacex;

import android.content.Context;


import androidx.room.Room;


public class DatabaseClient {
    private Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private SpacexDatabase database;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        database = Room.databaseBuilder(mCtx, SpacexDatabase.class,"Spacex").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public SpacexDatabase getDatabase() {
        return database;
    }
}
