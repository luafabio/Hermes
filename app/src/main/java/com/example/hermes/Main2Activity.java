package com.example.hermes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.hermes.fragments.ParadaSeleccionadaFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;

public class Main2Activity extends AppCompatActivity {

    private Retrofit retrofit;
    private RestStop restStop;

    private Spinner spinner;
    private Spinner timeSpinner;

    private List<Stop> stops;
    private StopViewModel stopViewModel;

//    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        currentFragment = new ParadaSeleccionadaFragment();

        RecyclerView recyclerViewParadas = findViewById(R.id.recycler_view);
        recyclerViewParadas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewParadas.setHasFixedSize(true);

        final StopAdapter adapter = new StopAdapter();
        recyclerViewParadas.setAdapter(adapter);

        stopViewModel = ViewModelProviders.of(this).get(StopViewModel.class);
        stopViewModel.getAllBings().observe(this, new Observer<List<Stop>>(){
            @Override
            public void onChanged(@Nullable List<Stop> parada) {
                adapter.setStops(parada);
                adapter.notifyDataSetChanged();
            }
        });

//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://ec2-18-219-95-88.us-east-2.compute.amazonaws.com:3000/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        restStop = retrofit.create(RestStop.class);

//        spinner = (Spinner) findViewById(R.id.stop_spinner);

//        getAllStop();

    }

    public void siguiente(View v){
        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
//        intent.putExtra("parada_id", this.getSelectedStop().getNum_stop());
//        intent.putExtra("retrofit_objeto", this.getRetrofit());
        startActivity(intent);
    }

    public void getAllStop() {
        restStop.getStops().enqueue(new Callback<List<Stop>>() {
            @Override
            public void onResponse(Call<List<Stop>> call, Response<List<Stop>> response) {
                stops = response.body();

                ArrayAdapter<Stop> adapter = new ArrayAdapter<>(Main2Activity.this, android.R.layout.simple_spinner_item, stops);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Stop>> call, Throwable t) {
                System.out.println("Error");
            }
        });
    }

    public Stop getSelectedStop() {
        return (Stop) spinner.getSelectedItem();
    }

    public Retrofit getRetrofit(){
        return (Retrofit) this.retrofit;
    }

}


