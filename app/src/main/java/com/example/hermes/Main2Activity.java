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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class Main2Activity extends AppCompatActivity implements StopAdapter.OnParadaListener {

    private List<Stop> stops;
    private StopViewModel stopViewModel;
    public SwipeRefreshLayout swipeRefreshLayout;
    private StopAdapter adapter;

    private String[] listaDeNombres = null;
    private double[] listaDeLatitudes = null;
    private double[] listaDeLongitudes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorPrimary)
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stopViewModel.getAllStops();

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
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

    @Override
    public void onParadaClick(int position) {
        Intent intent = new Intent(this, Main3Activity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("parada_id", stops.get(position).getNum_stop());
        bundle.putDouble("parada_latitud", stops.get(position).getLat());
        bundle.putDouble("parada_longitud", stops.get(position).getLng());
        bundle.putString("parada_nombre", stops.get(position).getName());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_paradas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mapa_de_paradas:

                if (stops != null) {

                    cargaDeListas();

                    Intent intentMapa = new Intent(this, MapsActivity.class);
                    Bundle bundleMapa = new Bundle();
                    bundleMapa.putStringArray("lista_nombres", this.listaDeNombres);
                    bundleMapa.putDoubleArray("lista_latitudes", this.listaDeLatitudes);
                    bundleMapa.putDoubleArray("lista_longitudes", this.listaDeLongitudes);
                    intentMapa.putExtras(bundleMapa);
                    startActivity(intentMapa);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void cargaDeListas(){

//        if (stops.size() != listaDeNombres.length ||
//                stops.size() != listaDeLatitudes.length ||
//                stops.size() != listaDeLongitudes.length){
            this.listaDeNombres = new String[stops.size()];
            this.listaDeLatitudes = new double[stops.size()];
            this.listaDeLongitudes = new double[stops.size()];
//        }

        int i = 0;
        for (Stop parada : stops) {

            this.listaDeNombres[i] = parada.getName();
            this.listaDeLatitudes[i] = parada.getLat();
            this.listaDeLongitudes[i] = parada.getLng();

            i++;
        }

        return;
    }

}


