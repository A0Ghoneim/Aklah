package com.example.aklah.Network;

import com.example.aklah.Model.Ingredient;

import java.util.ArrayList;

public interface IngredientNetworkCallback {
     void onSuccessResult(ArrayList<Ingredient> ingredients);
     void onFaliureResult(String errormsg);
}
