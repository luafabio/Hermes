package com.example.hermes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Main3Activity extends AppCompatActivity {

    Button botonIniciar;
//    TextView textoInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, String, String> carga = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "done";
            }

            @Override
            protected void onPostExecute(String s){
                if (s.equals("done")){
                    startActivity(new Intent(Main3Activity.this, MainActivity.class));
                }
            }
        };

//        botonIniciar.startAnimation();
        carga.execute();

//        botonIniciar = findViewById(R.id.empecemos);
//        botonIniciar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                @SuppressLint("StaticFieldLeak")
//                AsyncTask<String, String, String> carga = new AsyncTask<String, String, String>() {
//                    @Override
//                    protected String doInBackground(String... params) {
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        return "done";
//                    }
//
//                    @Override
//                    protected void onPostExecute(String s){
//                        if (s.equals("done")){
//                            startActivity(new Intent(Main3Activity.this, MainActivity.class));
//                        }
//                    }
//                };
//
//                botonIniciar.startAnimation();
//                carga.execute();
//
//                startActivity(new Intent(Main3Activity.this, MainActivity.class));
//
//            }
//        });



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
