package com.example.Spacex;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpacexService {
    @GET("crew")
    Call<List<Root>> getinfo();

}
