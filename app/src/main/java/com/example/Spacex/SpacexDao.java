package com.example.Spacex;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SpacexDao {
    @Query("SELECT * FROM entity")
    List<entity> getAll();

    @Insert
    void insert(entity ent);

    @Query("DELETE  FROM entity")
    void deleteAll();


}
