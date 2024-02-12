package com.example.aklah.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoCountry {
    @SerializedName("meals")
    private ArrayList<Country> countries;


    public PojoCountry(ArrayList<Country> countries) {
        this.countries = countries;
    }


    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }
}
