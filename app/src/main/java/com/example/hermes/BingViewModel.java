package com.example.hermes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;

public class BingViewModel extends ViewModel {

    public BingViewModel() {
    }

    private BingRepository repository= new BingRepository();

    private MutableLiveData<String> tokenLiveData = new MutableLiveData<>();

    LiveData<List<Bing>> getAllBings(String token) {
        return repository.getAllBings(token);
    }


    LiveData<String> getToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        tokenLiveData.postValue(task.getResult().getToken());
                        System.out.println("TOKEN:" + task.getResult().getToken());
                    }
                });g
        return tokenLiveData;
    }
}
