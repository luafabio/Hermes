package com.example.hermes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hermes.fragments.ParadaSeleccionadaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main3Activity extends AppCompatActivity {

    private Retrofit retrofit;
    private RestBing restBing;

    private Button boton;
    private NumberPicker np;
    private static final String TAG = "Main3Activity";

    private Fragment paradaFragment;

    private int paradaId;
    private String paradaNombre;
    private double paradaLat = -34.603722;
    private double paradaLong = -58.381592;
    private HorizontalNumberPicker np_channel_nr;

    private String tokenFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Bundle bundle = getIntent().getExtras();
        paradaId = bundle.getInt("parada_id");
        paradaNombre = bundle.getString("parada_nombre");
        paradaLat = bundle.getDouble("parada_latitud");
        paradaLong = bundle.getDouble("parada_longitud");

        boton = (Button) findViewById(R.id.logTokenButton);
        boton.setEnabled(true);

        TextView ubicacionSeleccionada = (TextView) findViewById(R.id.parada_seleccionadaNombre);
        ubicacionSeleccionada.setText(paradaNombre);

        paradaFragment = new ParadaSeleccionadaFragment();
        ((ParadaSeleccionadaFragment) paradaFragment).setUbicacionSeleccionada(new LatLng(paradaLat, paradaLong));
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment_container, paradaFragment).commit();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-18-219-95-88.us-east-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restBing = retrofit.create(RestBing.class);

        np_channel_nr = findViewById(R.id.horinzontal_picker);

        getToken();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void postStop(View v) {

        boton.setEnabled(false);

        Log.d(TAG, tokenFirebase);

        Bing bing = new Bing(tokenFirebase, paradaId, np_channel_nr.getValue());

        Call<Void> call = restBing.createBing(bing);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(Main3Activity.this, "Alarma creada de manera exitosa", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Main3Activity.this, "No se ha podido crear la alarma", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(Main3Activity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Main3Activity.this, "Error al crear la alarma", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main3Activity.this, MainActivity.class));
            }
        });
    }

    public void getToken(){

        FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "getInstanceId failed", task.getException());
                                    return;
                                }

                                tokenFirebase = task.getResult().getToken();
                                Log.d(TAG, tokenFirebase);
                            }
                        });
    }

}
