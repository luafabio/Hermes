package com.example.hermes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class StopViewModel extends ViewModel {

    private StopRepository repository= new StopRepository();

    public LiveData<List<Stop>> getAllStops() {
        return repository.getAllStops();
    }
}
