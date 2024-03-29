package com.example.hermes;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BingRepository {

    private Retrofit retrofit;
    private MutableLiveData<List<Bing>> allBing = new MutableLiveData<>();
    private RestBing restBing;

    public BingRepository() {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-18-219-95-88.us-east-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restBing = retrofit.create(RestBing.class);
    }

    public LiveData<List<Bing>> getAllBings() {
        restBing.getBings().enqueue(new Callback<List<Bing>>() {
            @Override
            public void onResponse(Call<List<Bing>> call, Response<List<Bing>> response) {
                allBing.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Bing>> call, Throwable t) {
            }
        });
        return allBing;
    }

}
