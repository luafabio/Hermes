package com.example.hermes;

import android.app.ActionBar;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BingViewModel bingViewModel;
    private static final String TAG = "MainActivity";
    private String tokenFirebase = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getToken();

        Log.d(TAG, tokenFirebase);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BingAdapter adapter = new BingAdapter();
        recyclerView.setAdapter(adapter);

        Log.d(TAG, tokenFirebase);

        bingViewModel = ViewModelProviders.of(this).get(BingViewModel.class);
        bingViewModel.getAllBings("dxabB-oIeWI:APA91bHITKMEZ4nAFaVYPiUP9ftKJyteciYyl1iHyXIjrkuQ9EVbjUpmLnrBNTsbjo2qmLL8DfHOs9SmaJvlYGOzK-Y7druRm7HHS3SPQUUhO2O2EkrXKSi7EOkNvmmB_yF3itkOo-vc").observe(this, new Observer<List<Bing>>(){
            @Override
            public void onChanged(@Nullable List<Bing> bings) {
                adapter.setBings(bings);
                adapter.notifyDataSetChanged();
            }
        });

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
        bingViewModel.getAllBings("dxabB-oIeWI:APA91bHITKMEZ4nAFaVYPiUP9ftKJyteciYyl1iHyXIjrkuQ9EVbjUpmLnrBNTsbjo2qmLL8DfHOs9SmaJvlYGOzK-Y7druRm7HHS3SPQUUhO2O2EkrXKSi7EOkNvmmB_yF3itkOo-vc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

                            // Get new Instance ID token
                            tokenFirebase = task.getResult().getToken();

                            // Log and toast
                            Log.d(TAG, tokenFirebase);
                        }
                });
    }
}
