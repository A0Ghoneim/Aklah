package com.example.aklah.Network;

import com.example.aklah.Model.Meal;

import java.util.ArrayList;

public interface RandomNetworkCallBack {
    public void onSuccessResultRandom(ArrayList<Meal> meals);
    public void onFaliureResultRandom(String errormsg);
}
