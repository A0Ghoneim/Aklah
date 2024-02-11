package com.example.aklah.Network;

import com.example.aklah.Model.Meal;

import java.util.ArrayList;

public interface CategoryNetworkCallback {
    public void onSuccessResult(ArrayList<Meal> categoriesOnly);
    public void onFaliureResult(String errormsg);
}
