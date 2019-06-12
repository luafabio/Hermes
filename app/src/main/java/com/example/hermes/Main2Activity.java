package com.example.hermes;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView tx = (TextView)findViewById(R.id.textView3);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/abc.ttf");

        tx.setTypeface(custom_font);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-18-219-95-88.us-east-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restStop = retrofit.create(RestStop.class);


//        timeSpinner = (Spinner) findViewById(R.id.time_spinner);
//        final List<Integer> timeList = new ArrayList<>();
//        timeList.add(5);
//        timeList.add(10);
//        timeList.add(15);
//        timeList.add(20);
//        timeList.add(25);
//
//        ArrayAdapter<Integer> timeAdapter = new ArrayAdapter<>(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item, timeList);
//        timeSpinner.setAdapter(timeAdapter);

//        spinner = (Spinner) findViewById(R.id.stop_spinner);

        getAllStop();

        Button boton = (Button) findViewById(R.id.segundoPaso);

    }

    public void siguiente(View v){
        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
        intent.putExtra("parada_id", getSelectedStop().getNum_stop());
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






}


