package com.example.hermes;

import com.google.gson.annotations.SerializedName;

public class Stop {
    private double lat;
    @SerializedName("long")
    private double lng;
    private int eta_stop;
    private int long_stop;
    private boolean status;
    private int num_stop;
    private String name;

    public Stop() {
    }

    public Stop(double lat, double lng, int eta_stop, int long_stop, boolean status, int num_stop, String name) {
        this.lat = lat;
        this.lng = lng;
        this.eta_stop = eta_stop;
        this.long_stop = long_stop;
        this.status = status;
        this.num_stop = num_stop;
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getEta_stop() {
        return eta_stop;
    }

    public int getLong_stop() {
        return long_stop;
    }

    public boolean isStatus() {
        return status;
    }

    public int getNum_stop() {
        return num_stop;
    }

    public void setNum_stop(int num_stop) {
        this.num_stop = num_stop;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getNum_stop() + " - " + getName();
    }
}