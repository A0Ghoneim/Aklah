package com.example.aklah.Network;

import com.example.aklah.Model.Meal;

import java.util.ArrayList;

public interface MealNetworkCallback {
    public void onSuccessResult(ArrayList<Meal> meals);
    public void onFaliureResult(String errormsg);
}
