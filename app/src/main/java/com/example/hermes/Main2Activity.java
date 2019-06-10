package com.example.hermes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class Main2Activity extends AppCompatActivity {

    private Retrofit retrofit;
    private RestStop restStop;
    private RestBing restBing;

    private Spinner spinner;
    private Spinner timeSpinner;

    private List<Stop> stops;

    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-18-219-95-88.us-east-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restStop = retrofit.create(RestStop.class);
        restBing = retrofit.create(RestBing.class);


        timeSpinner = (Spinner) findViewById(R.id.time_spinner);
        final List<Integer> timeList = new ArrayList<>();
        timeList.add(5);
        timeList.add(10);
        timeList.add(15);
        timeList.add(20);
        timeList.add(25);


        ArrayAdapter<Integer> timeAdapter = new ArrayAdapter<>(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item, timeList);
        timeSpinner.setAdapter(timeAdapter);

        spinner = (Spinner) findViewById(R.id.stop_spinner);

        getAllStop();

        Button boton = (Button) findViewById(R.id.logTokenButton);
        boton.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         FirebaseInstanceId.getInstance().getInstanceId()
                                                 .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                         if (!task.isSuccessful()) {
                                                             Log.w(TAG, "getInstanceId failed", task.getException());
                                                             return;
                                                         }

                                                         // Get new Instance ID token
                                                         String token = task.getResult().getToken();

                                                         // Log and toast
                                                         //                        String msg = getString(R.string.msg_token_fmt, token);
                                                         Log.d(TAG, token);
                                                     }
                                                 });
                                     }
            });
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

    public int getSelectedTime() {
        return (int) timeSpinner.getSelectedItem();
    }

    public void postStop(View v) {


        Bing bing = new Bing(324, getSelectedStop().getNum_stop(), getSelectedTime());

        Call<Void> call = restBing.createBing(bing);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(Main2Activity.this, "Alarma creada de manera exitosa", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Main2Activity.this, "Error al crear la alarma", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });
    }


}

//TODO:
// consumir los datos correctos. NICO - HECHO
// Agregar botón fecha para retornar a la lista de alarmas. NICO - HECHO
// cuando creo la alarma que lleve para atras y me la visualice. NICO - HECHO
// Acomodar la visualizacion de las pantallas. NICO - FALTA
// Acomodar las vistas, en manera relativa si vemos que es mejor. o sino ver la mejor manera de renderizar los xml. NICO - HECHO
// Agregar identificador de usuario. HARDCODE
// ver la mejor manera de ser notificado por la aplicacion cuando la alarma se encuentra a emitir. LUIS

