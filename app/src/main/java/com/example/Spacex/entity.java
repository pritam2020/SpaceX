package com.example.Spacex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
public class entity {
    @PrimaryKey(autoGenerate = true)
    private int identity;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "agency")
    private String agency;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "launches")
    @TypeConverters(StringListToStringConverter.class)
    private List<String> launches;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "ID")
    private String ID;
    @ColumnInfo(name = "wikipedia")
    private String wikipedia;

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLaunches() {
        return launches;
    }

    public void setLaunches(List<String> launches) {
        this.launches = launches;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
