package com.example.aklah.Network;


import com.example.aklah.Model.PojoCategory;
import com.example.aklah.Model.PojoCountry;
import com.example.aklah.Model.PojoMeal;
import com.example.aklah.Model.PojoIng;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("search.php")
    Call<PojoMeal> getMealsThatContain(@Query("s") String name);
    @GET("lookup.php")
    Call<PojoMeal> getMealById(@Query("i") String id);
    @GET("random.php")
    Call<PojoMeal> getRandomMeal();

    @GET("categories.php")
    Call<PojoCategory> getAllCategories();
    @GET("list.php?a=list")
    Call<PojoCountry> getAllCountries();
    @GET("list.php?i=list")
    Call<PojoIng> getAllIngredients();

    @GET("filter.php")
    Call<PojoMeal> getMealsInCategory(@Query("c") String category);
    @GET("filter.php")
    Call<PojoMeal> getMealsInCountry(@Query("a") String country);
    @GET("filter.php")
    Call<PojoMeal> getMealsWithIngredient(@Query("i") String ingredient);

}
