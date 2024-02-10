package com.example.aklah.Model;

import androidx.lifecycle.LiveData;


import com.example.aklah.Network.IngredientNetworkCallback;
import com.example.aklah.Network.MealNetworkCallback;

import java.util.List;

public interface MealRepository {
    LiveData<List<Meal>> getFavouriteMeals();

    void getMealsInCategory(MealNetworkCallback mealNetworkCallback,String category);

    void getAllIngredients(IngredientNetworkCallback ingredientNetworkCallback);

    void insertMeal(Meal meal);

    void deleteMeal(Meal meal);
}
