package com.example.hermes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class BingViewModel extends ViewModel {
    private BingRepository repository= new BingRepository();

    public LiveData<List<Bing>> getAllBings(String token) {
        return repository.getAllBings(token);
    }

//    public void createBing(Bing bing) {
//        repository.createBing(bing);
//    }
//
//    public void deleteBing(Bing bing) {
//        repository.deleteBing(bing);
//    }

}
