package com.example.aklah.Network;


import com.example.aklah.Model.IngredientPojo;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.Pojo;
import com.example.aklah.Model.PojoIng;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MealService {
    @GET("search.php")
    Call<Pojo> getMealsThatContain(@Query("s") String name);
    @GET("lookup.php")
    Call<Pojo> getMealById(@Query("i") String id);
    @GET("random.php")
    Call<Pojo> getRandomMeal();

    @GET("list.php?c=list")
    Call<Pojo> getAllCategories();
    @GET("list.php?a=list")
    Call<Pojo> getAllCountries();
    @GET("list.php?i=list")
    Call<PojoIng> getAllIngredients();

    @GET("filter.php")
    Call<Pojo> getMealsInCategory(@Query("c") String category);
    @GET("filter.php")
    Call<Pojo> getMealsInCountry(@Query("a") String country);
    @GET("filter.php")
    Call<Pojo> getMealsWithIngredient(@Query("i") String ingredient);

}
