package com.example.aklah.Model;

import androidx.lifecycle.LiveData;


import com.example.aklah.Network.CategoryNetworkCallback;
import com.example.aklah.Network.CountryNetworkCallback;
import com.example.aklah.Network.IngredientNetworkCallback;
import com.example.aklah.Network.MealNetworkCallback;
import com.example.aklah.Network.RandomNetworkCallBack;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {
    Flowable<List<Meal>> getFavouriteMeals();

    Single<Meal> getMealById(String id);

    Completable insertMeal(Meal meal);

    void deleteMeal(Meal meal);

    void deletesavedmeal(String idmeal,int day);

    void clearEverything();

    void getMealsThatContain(MealNetworkCallback mealNetworkCallback, String name);
    void getMealById(MealNetworkCallback mealNetworkCallback, String id);

    Flowable<List<Meal>> getMealByDay(int day);
    void getRandomMeal(RandomNetworkCallBack randomNetworkCallBack);
    void getAllCategories(CategoryNetworkCallback categoryNetworkCallback);
    void getAllCountries(CountryNetworkCallback countryNetworkCallback);
    void getAllIngredients(IngredientNetworkCallback ingredientNetworkCallback);
    void getMealsInCategory(MealNetworkCallback mealNetworkCallback,String category);
    void getMealsInCountry(MealNetworkCallback mealNetworkCallback,String country);
    void getMealsWithIngredient(MealNetworkCallback mealNetworkCallback,String ingredient);
}
