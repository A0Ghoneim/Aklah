package com.example.aklah.Network;

import com.example.aklah.Model.IngredientPojo;

import java.util.ArrayList;

public interface IngredientNetworkCallback {
    public void onSuccessResult(ArrayList<IngredientPojo> ingredients);
    public void onFaliureResult(String errormsg);
}
