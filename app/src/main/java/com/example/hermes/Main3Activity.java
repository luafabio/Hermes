package com.example.hermes;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class Main3Activity extends AppCompatActivity {

    AnimationDrawable animacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ImageView cargando = (ImageView)findViewById(R.id.imageView);
        animacion = (AnimationDrawable)cargando.getDrawable();

        animacion.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        animacion.stop();

        startActivity(new Intent(Main3Activity.this, MainActivity.class));
    }

    protected void onResume() {
        super.onResume();
        startActivity(new Intent(Main3Activity.this, MainActivity.class));
    }

}
