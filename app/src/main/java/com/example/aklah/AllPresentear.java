package com.example.aklah;

import android.util.Log;

import com.example.aklah.Model.IngredientPojo;
import com.example.aklah.Model.MealPojo;
import com.example.aklah.Model.MealRepository;
import com.example.aklah.Network.IngredientNetworkCallback;
import com.example.aklah.Network.MealNetworkCallback;

import java.util.ArrayList;

public class AllPresentear implements MealNetworkCallback {

    MealRepository repository;
    public AllPresentear(MealRepository repo){
        repository=repo;
        repository.getMealsInCategory(this,"beef");
    }




    @Override
    public void onSuccessResult(ArrayList<MealPojo> meals) {
        for (int i = 0; i < meals.size(); i++) {
            Log.i("tagggg", "onSuccessResult: "+ meals.get(i).isFavourite());
        }
    }

    @Override
    public void onFaliureResult(String errormsg) {

    }
}
