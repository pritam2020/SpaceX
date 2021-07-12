package com.example.info_about_country;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DoubleListToStringConverter {
    @TypeConverter
    public String fromCountryLangList(List<Double> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Double>>() {}.getType();
        String json = gson.toJson(countryLang, type);
        return json;
    }

    @TypeConverter
    public List<Double> toCountryLangList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Double>>() {}.getType();
        List<Double> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }
}
