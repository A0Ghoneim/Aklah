package com.example.aklah.Network;


import com.example.aklah.Model.PojoCategory;
import com.example.aklah.Model.PojoCountry;
import com.example.aklah.Model.PojoMeal;
import com.example.aklah.Model.PojoIng;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("search.php")
    Single<PojoMeal> getMealsThatContain(@Query("s") String name);
    @GET("lookup.php")
    Single<PojoMeal> getMealById(@Query("i") String id);
    @GET("random.php")
    Single<PojoMeal> getRandomMeal();

    @GET("categories.php")
    Single<PojoCategory> getAllCategories();
    @GET("list.php?a=list")
    Single<PojoCountry> getAllCountries();
    @GET("list.php?i=list")
    Single<PojoIng> getAllIngredients();

    @GET("filter.php")
    Single<PojoMeal> getMealsInCategory(@Query("c") String category);
    @GET("filter.php")
    Single<PojoMeal> getMealsInCountry(@Query("a") String country);
    @GET("filter.php")
    Single<PojoMeal> getMealsWithIngredient(@Query("i") String ingredient);

}
