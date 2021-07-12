package com.example.info_about_country;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
public class entity {
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTopLevelDomain(List<String> topLevelDomain) {
        this.topLevelDomain = topLevelDomain;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public void setCallingCodes(List<String> callingCodes) {
        this.callingCodes = callingCodes;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setAltSpellings(List<String> altSpellings) {
        this.altSpellings = altSpellings;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setLatlng(List<Double> latlng) {
        this.latlng = latlng;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setGini(double gini) {
        this.gini = gini;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public void setTranslations(Translations translations) {
        this.translations = translations;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setRegionalBlocs(List<RegionalBloc> regionalBlocs) {
        this.regionalBlocs = regionalBlocs;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getTopLevelDomain() {
        return topLevelDomain;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public List<String> getCallingCodes() {
        return callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public List<String> getAltSpellings() {
        return altSpellings;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public int getPopulation() {
        return population;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public String getDemonym() {
        return demonym;
    }

    public double getArea() {
        return area;
    }

    public double getGini() {
        return gini;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public List<String> getBorders() {
        return borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public Translations getTranslations() {
        return translations;
    }

    public String getFlag() {
        return flag;
    }

    public List<RegionalBloc> getRegionalBlocs() {
        return regionalBlocs;
    }

    public String getCioc() {
        return cioc;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "topLevelDomain")
    @TypeConverters(StringListToStringConverter.class)
    private List<String> topLevelDomain;
    @ColumnInfo(name = "alpha2Code")
    private String alpha2Code;
    @ColumnInfo(name = "alpha3Code")
    private String alpha3Code;
    @ColumnInfo(name = "callingCodes")
    @TypeConverters(StringListToStringConverter.class)
    private List<String> callingCodes;
    @ColumnInfo(name = "capital")
    private String capital;
    @ColumnInfo(name = "altSpellings")
    @TypeConverters(StringListToStringConverter.class)
    private List<String> altSpellings;
    @ColumnInfo(name = "region")
    private String region;
    @ColumnInfo(name = "subregion")
    private String subregion;
    @ColumnInfo(name = "population")
    private int population;
    @ColumnInfo(name = "latlng")
    @TypeConverters(DoubleListToStringConverter.class)
    private List<Double> latlng;
    @ColumnInfo(name = "demonym")
    private String demonym;
    @ColumnInfo(name = "area")
    private double area;
    @ColumnInfo(name = "gini")
    private double gini;
    @ColumnInfo(name = "timezones")
    @TypeConverters(StringListToStringConverter.class)
    private List<String> timezones;
    @ColumnInfo(name = "borders")
    @TypeConverters(StringListToStringConverter.class)
    private List<String> borders;
    @ColumnInfo(name = "nativeName")
    private String nativeName;
    @ColumnInfo(name = "numericCode")
    private String numericCode;
    @ColumnInfo(name = "currencies")
    @TypeConverters(CurrencyConverter.class)
    private List<Currency> currencies;
    @ColumnInfo(name = "languages")
    @TypeConverters(LanguageConverter.class)
    private List<Language> languages;
    @ColumnInfo(name = "translation")
    @TypeConverters(TranslationConverter.class)
    private Translations translations;
    @ColumnInfo(name = "flag")
    private String flag;
    @ColumnInfo(name = "regionalBlocs")
    @TypeConverters(RegionalBlocConverter.class)
    private List<RegionalBloc> regionalBlocs;
    @ColumnInfo(name = "cioc")
    private String cioc;
}
