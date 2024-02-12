package com.example.aklah.Model;

import androidx.lifecycle.LiveData;


import com.example.aklah.Network.CategoryNetworkCallback;
import com.example.aklah.Network.CountryNetworkCallback;
import com.example.aklah.Network.IngredientNetworkCallback;
import com.example.aklah.Network.MealNetworkCallback;

import java.util.List;

public interface MealRepository {
    LiveData<List<Meal>> getFavouriteMeals();

    LiveData<Meal> getMealById(String id);

    void insertMeal(Meal meal);

    void deleteMeal(Meal meal);

    void getMealsThatContain(MealNetworkCallback mealNetworkCallback, String name);
    void getMealById(MealNetworkCallback mealNetworkCallback, String id);
    void getRandomMeal(MealNetworkCallback mealNetworkCallback);
    void getAllCategories(CategoryNetworkCallback categoryNetworkCallback);
    void getAllCountries(CountryNetworkCallback countryNetworkCallback);
    void getAllIngredients(IngredientNetworkCallback ingredientNetworkCallback);
    void getMealsInCategory(MealNetworkCallback mealNetworkCallback,String category);
    void getMealsInCountry(MealNetworkCallback mealNetworkCallback,String country);
    void getMealsWithIngredient(MealNetworkCallback mealNetworkCallback,String ingredient);
}
