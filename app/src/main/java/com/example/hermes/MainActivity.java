package com.example.hermes;

import android.app.ActionBar;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BingViewModel bingViewModel;
    public SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "MainActivity";
    private String tokenFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorPrimary)
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bingViewModel.getAllBings(tokenFirebase);

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000); // Delay in millis
            }
        });
        bingViewModel = ViewModelProviders.of(this).get(BingViewModel.class);
        final BingAdapter adapter = new BingAdapter();


        bingViewModel.getToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String token) {
                tokenFirebase = token;
                Log.d(TAG, tokenFirebase);
                bingViewModel.getAllBings(tokenFirebase).observe(MainActivity.this, new Observer<List<Bing>>(){
                    @Override
                    public void onChanged(@Nullable List<Bing> bings) {
                        adapter.setBings(bings);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bingViewModel.getAllBings(tokenFirebase);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.acerca_de_nosotros:
                startActivity(new Intent(MainActivity.this, AcercaDeNosotros.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


}
