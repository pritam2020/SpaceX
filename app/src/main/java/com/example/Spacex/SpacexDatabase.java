package com.example.Spacex;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = entity.class,version = 1)
public abstract  class SpacexDatabase extends RoomDatabase {

    public abstract SpacexDao countryDao();

}
