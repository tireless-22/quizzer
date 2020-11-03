package com.example.quizzerapp;

import android.util.Log;

public class categorymodel {
    private String name;
private String url;
private int sets;

    public categorymodel(String name, String url, int sets) {
        this.name = name;
        this.url = url;
        this.sets = sets;
    }

    public  categorymodel(){


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
