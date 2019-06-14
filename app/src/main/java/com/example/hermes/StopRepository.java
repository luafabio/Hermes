package com.example.hermes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StopRepository {

    private Retrofit retrofit;
    private MutableLiveData<List<Stop>> allStops = new MutableLiveData<>();
    private RestStop restStop;

    public StopRepository() {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-18-219-95-88.us-east-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restStop = retrofit.create(RestStop.class);
    }

    public LiveData<List<Stop>> getAllStops() {
        restStop.getStops().enqueue(new Callback<List<Stop>>() {
            @Override
            public void onResponse(Call<List<Stop>> call, Response<List<Stop>> response) {
                allStops.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Stop>> call, Throwable t) {
            }
        });
        return allStops;
    }
}
