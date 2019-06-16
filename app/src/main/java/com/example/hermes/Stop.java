package com.example.hermes;

public class Stop {
    private float lat;
    private float lng;
    private int eta_stop;
    private int long_stop;
    private boolean status;
    private int num_stop;
    private String name;

    public Stop() {
    }

    public Stop(float lat, float lng, int eta_stop, int long_stop, boolean status, int num_stop, String name) {
        this.lat = lat;
        this.lng = lng;
        this.eta_stop = eta_stop;
        this.long_stop = long_stop;
        this.status = status;
        this.num_stop = num_stop;
        this.name = name;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
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