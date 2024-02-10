package com.example.aklah.Network;

import com.example.aklah.Model.MealPojo;

import java.util.ArrayList;

public interface CategoryNetworkCallback {
    public void onSuccessResult(ArrayList<MealPojo> categoriesOnly);
    public void onFaliureResult(String errormsg);
}
