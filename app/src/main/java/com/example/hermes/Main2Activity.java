package com.example.hermes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class Main2Activity extends AppCompatActivity implements StopAdapter.OnParadaListener {

    private List<Stop> stops;
    private StopViewModel stopViewModel;
    public SwipeRefreshLayout swipeRefreshLayout;
    private StopAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light)
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stopViewModel.getAllStops();

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000); // Delay in millis
            }
        });
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
        Bundle bundle = new Bundle();
        bundle.putInt("parada_id", stops.get(position).getNum_stop());
        bundle.putDouble("parada_latitud", stops.get(position).getLat());
        bundle.putDouble("parada_longitud", stops.get(position).getLng());
        bundle.putString("parada_nombre", stops.get(position).getName());
        intent.putExtras(bundle);
//        System.out.println("ID - " + stops.get(position).getNum_stop());
//        System.out.println("LAT - " + stops.get(position).getLat());
//        System.out.println("LONG - " + stops.get(position).getLng());
        startActivity(intent);
    }
}


