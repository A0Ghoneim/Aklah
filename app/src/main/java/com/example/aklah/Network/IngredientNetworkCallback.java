package com.example.aklah.Network;

import com.example.aklah.Model.Ingredient;

import java.util.ArrayList;

public interface IngredientNetworkCallback {
     void onSuccessResultIngredient(ArrayList<Ingredient> ingredients);
     void onFaliureResultIngredient(String errormsg);
}
