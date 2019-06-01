package com.example.hermes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    private Retrofit retrofit;
    private RestStop restStop;
    private RestBing restBing;

    private Spinner spinner;

    private List<Stop> stops;
    private int time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        NumberPicker np = (NumberPicker) findViewById(R.id.np);

        np.setMinValue(5);
        np.setMaxValue(25);
        np.setWrapSelectorWheel(true);

        time = 5;

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                time = newVal;
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-18-219-95-88.us-east-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restStop = retrofit.create(RestStop.class);
        restBing = retrofit.create(RestBing.class);

        spinner = (Spinner) findViewById(R.id.stop_spinner);

        getAllStop();

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

    public void postStop(View v) {
        Bing bing = new Bing(324, getSelectedStop().getId(), time);

        Call<Void> call = restBing.createBing(bing);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(Main2Activity.this, "Alarma creada de manera exitosa", Toast.LENGTH_SHORT).show();
                //llevar al activityMain
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Main2Activity.this, "Error al crear la alarma", Toast.LENGTH_SHORT).show();
                //llevar al activityMain

            }
        });
    }


}

//TODO:
// consumir los datos correctos. NICO
// Acomodar la visualizacion de las pantallas. NICO
// cuando creo la alarma que lleve para atras y me la visualice. NICO
// Agregar identificador de usuario. HARDCODE
// ver la mejor manera de ser notificado por la aplicacion cuando la alarma se encuentra a emitir. LUIS
// Acomodar las vistas, en manera relativa si vemos que es mejor. o sino ver la mejor manera de renderizar los xml. NICO
