package com.example.hermes;

public class Bing {

    private int id_user;
    private int id_stop;
    private int time;
    private String status;
    private String name_stop;

    public Bing() {
    }

    public Bing(int id_user, int id_stop, int time){
        this.id_user = id_user;
        this.id_stop = id_stop;
        this.time = time;
    }

    public Bing(int id_user, int id_stop, int time, String name_stop) {
        this.id_user = id_user;
        this.id_stop = id_stop;
        this.time = time;
        this.name_stop = name_stop;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_stop() {
        return id_stop;
    }

    public int getTime() {
        return time;
    }

    public String getName_stop() { return name_stop; }

    public String getStatus() {
        return status;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_stop(int id_stop) {
        this.id_stop = id_stop;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName_stop(String name_stop) { this.name_stop = name_stop; }
}