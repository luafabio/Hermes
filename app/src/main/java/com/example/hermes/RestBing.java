package com.example.hermes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestBing {

    @GET("bing")
    Call<List<Bing>> getBings(@Query("id_user") String id_user);

    @POST("bing")
    Call<Void> createBing(@Body Bing bing);

}