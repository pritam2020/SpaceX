package com.example.info_about_country;

import java.util.List;

class Currency {
    public String code;
    public String name;
    public String symbol;
}

class Language {
    public String iso639_1;
    public String iso639_2;
    public String name;
    public String nativeName;
}

class Translations {
    public String de;
    public String es;
    public String fr;
    public String ja;
    public String it;
    public String br;
    public String pt;
    public String nl;
    public String hr;
    public String fa;
}

class RegionalBloc {
    public String acronym;
    public String name;
    public List<String> otherAcronyms;
    public List<String> otherNames;
}

public class Root {
    public String name;
    public List<String> topLevelDomain;
    public String alpha2Code;
    public String alpha3Code;
    public List<String> callingCodes;
    public String capital;
    public List<String> altSpellings;
    public String region;
    public String subregion;
    public int population;
    public List<Double> latlng;
    public String demonym;
    public double area;
    public double gini;
    public List<String> timezones;
    public List<String> borders;
    public String nativeName;
    public String numericCode;
    public List<Currency> currencies;
    public List<Language> languages;
    public Translations translations;
    public String flag;
    public List<RegionalBloc> regionalBlocs;
    public String cioc;
}
