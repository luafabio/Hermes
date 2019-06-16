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

public class Main2Activity extends AppCompatActivity implements StopAdapter.OnParadaListener {

    private List<Stop> stops;
    private StopViewModel stopViewModel;

    private StopAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RecyclerView recyclerViewParadas = findViewById(R.id.recycler_view);
        recyclerViewParadas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewParadas.setHasFixedSize(true);

        adapter = new StopAdapter(this);
        recyclerViewParadas.setAdapter(adapter);

        stopViewModel = ViewModelProviders.of(this).get(StopViewModel.class);
        stopViewModel.getAllStops().observe(this, new Observer<List<Stop>>(){
            @Override
            public void onChanged(@Nullable List<Stop> paradas) {
                Main2Activity.this.stops = paradas;
                adapter.setStops(paradas);
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void siguiente(View v){
        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
//        intent.putExtra("parada_id", this.getSelectedStop().getNum_stop());
//        intent.putExtra("retrofit_objeto", this.getRetrofit());
        startActivity(intent);
    }

    @Override
    public void onParadaClick(int position) {
        Intent intent = new Intent(this, Main3Activity.class);
        intent.putExtra("parada_id", stops.get(position).getNum_stop());
//        intent.putExtra("parada_latitud", stops.get(position).getLat());
//        intent.putExtra("parada_longitud", stops.get(position).getLng());
        System.out.println("ID - " + stops.get(position).getNum_stop());
        System.out.println("LAT - " + stops.get(position).getLat());
        System.out.println("LONG - " + stops.get(position).getLng());
        startActivity(intent);
    }
}


