package com.example.info_about_country;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CountryDao {
    @Query("SELECT * FROM entity")
    List<entity> getAll();

    @Insert
    void insert(entity ent);


}
