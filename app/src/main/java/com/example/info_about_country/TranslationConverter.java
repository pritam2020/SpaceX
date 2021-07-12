package com.example.info_about_country;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TranslationConverter {
    @TypeConverter
    public String fromCountryLangList(Translations countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Translations>() {}.getType();
        String json = gson.toJson(countryLang, type);
        return json;
    }

    @TypeConverter
    public Translations toCountryLangList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Translations>() {}.getType();
        Translations countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }
}
