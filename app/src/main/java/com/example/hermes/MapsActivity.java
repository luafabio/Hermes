package com.example.hermes;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Stop paradas;
    private String[] listaNombres = null;
    private double[] listaLatitudes = null;
    private double[] listaLongitudes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getExtras();
        listaNombres = bundle.getStringArray("lista_nombres");
        listaLatitudes = bundle.getDoubleArray("lista_latitudes");
        listaLongitudes = bundle.getDoubleArray("lista_longitudes");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng parada = null;

        int i = 0;
        while (i < listaNombres.length){

            parada = new LatLng(listaLatitudes[i], listaLongitudes[i]);
            mMap.addMarker(new MarkerOptions().position(parada).title(listaNombres[i]));
            i++;
        }

        LatLng puntoPredet = new LatLng(-34.1352, -58.9948);
        CameraPosition camara = new CameraPosition.Builder()
                .target(puntoPredet)
                .zoom(12.2F)
                .bearing(0)
                .tilt(30)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camara));
    }




}
