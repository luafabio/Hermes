package com.example.hermes.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hermes.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParadaSeleccionadaFragment extends Fragment implements OnMapReadyCallback {

    private View rootView;
    private GoogleMap gmap;
    private MapView mapView;
    private LatLng ubicacionSeleccionada;

    public ParadaSeleccionadaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_parada_seleccionada, container,
                false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.mapa);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.addMarker(new MarkerOptions().position(ubicacionSeleccionada).title("Marcador"));

        CameraPosition camara = new CameraPosition.Builder()
                .target(ubicacionSeleccionada)
                .zoom(17)
                .bearing(0)
                .tilt(30)
                .build();

        gmap.animateCamera(CameraUpdateFactory.newCameraPosition(camara));
    }

    public void setUbicacionSeleccionada(LatLng ubicacion){
        this.ubicacionSeleccionada = ubicacion;
    }
}
