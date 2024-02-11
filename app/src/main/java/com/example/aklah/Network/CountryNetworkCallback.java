package com.example.aklah.Network;

import com.example.aklah.Model.Meal;

import java.util.ArrayList;

public interface CountryNetworkCallback {
    public void onSuccessResult(ArrayList<Meal> countriesOnly);
    public void onFaliureResult(String errormsg);
}
