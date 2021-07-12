package com.example.info_about_country;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CurrencyConverter {
    @TypeConverter
    public String fromCountryLangList(List<Currency> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Currency>>() {}.getType();
        String json = gson.toJson(countryLang, type);
        return json;
    }

    @TypeConverter
    public List<Currency> toCountryLangList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Currency>>() {}.getType();
        List<Currency> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }
}
