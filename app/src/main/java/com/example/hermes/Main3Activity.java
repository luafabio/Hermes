package com.example.hermes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Main3Activity extends AppCompatActivity {

    private Retrofit retrofit;
    private RestBing restBing;

    private int parada_seleccionada;

    private NumberPicker np;
    private static final String TAG = "Main3Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        restBing = retrofit.create(RestBing.class);

        np = (NumberPicker) findViewById(R.id.np);
        np.setMinValue(0);
        np.setMaxValue(30);

        np.setWrapSelectorWheel(true);

        Bundle datos = this.getIntent().getExtras();
        parada_seleccionada = datos.getInt("parada_id");

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
                                // String msg = getString(R.string.msg_token_fmt, token);
                                Log.d(TAG, token);
                            }
                        });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public int getSelectedTime() {
        return (int) np.getValue();
    }

    public void postStop(View v) {

        Bing bing = new Bing(324, parada_seleccionada, getSelectedTime());

        Call<Void> call = restBing.createBing(bing);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(Main3Activity.this, "Alarma creada de manera exitosa", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main3Activity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Main3Activity.this, "Error al crear la alarma", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main3Activity.this, MainActivity.class));
            }
        });
    }

}
