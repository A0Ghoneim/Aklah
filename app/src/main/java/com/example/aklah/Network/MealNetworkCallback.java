package com.example.aklah.Network;

import com.example.aklah.Model.MealPojo;

import java.util.ArrayList;

public interface MealNetworkCallback {
    public void onSuccessResult(ArrayList<MealPojo> meals);
    public void onFaliureResult(String errormsg);
}
