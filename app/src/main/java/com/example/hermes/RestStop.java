package com.example.hermes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestStop {

    @GET("stops")
    Call<List<Stop>> getStops();
}
