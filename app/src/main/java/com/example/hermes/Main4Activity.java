package com.example.hermes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, String, String> carga = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "done";
            }

            @Override
            protected void onPostExecute(String s) {
                if (s.equals("done")) {
                    startActivity(new Intent(Main4Activity.this, MainActivity.class));
                    finish();
                }
            }
        };

        carga.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
