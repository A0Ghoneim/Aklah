package com.example.aklah.Network;

import com.example.aklah.Model.MealPojo;

import java.util.ArrayList;

public interface CountryNetworkCallback {
    public void onSuccessResult(ArrayList<MealPojo> countriesOnly);
    public void onFaliureResult(String errormsg);
}
