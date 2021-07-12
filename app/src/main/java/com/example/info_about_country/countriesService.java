package com.example.info_about_country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface countriesService {
    @GET("region/asia")
    Call<List<Root>> getinfo();

}
