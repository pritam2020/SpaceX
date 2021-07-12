package com.example.info_about_country;

import android.content.Context;


import androidx.room.Room;


public class DatabaseClient {
    private Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private CountryDatabase database;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        database = Room.databaseBuilder(mCtx, CountryDatabase.class,"CountryInfo").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public CountryDatabase getDatabase() {
        return database;
    }
}
