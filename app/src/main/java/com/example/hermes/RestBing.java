package com.example.hermes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestBing {

    @GET("bing")
    Call<List<Bing>> getBings();

    @POST("bing")
    Call<Void> createBing(@Body Bing bing);

}