package com.example.info_about_country;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = entity.class,version = 1)
public abstract  class CountryDatabase extends RoomDatabase {

    public abstract CountryDao countryDao();

}
