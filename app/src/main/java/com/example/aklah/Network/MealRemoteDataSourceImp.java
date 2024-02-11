package com.example.aklah.Network;

import android.util.Log;

import com.example.aklah.Model.Pojo;
import com.example.aklah.Model.PojoIng;;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSourceImp implements MealRemoteDataSource {
    Retrofit retrofit;
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private MealRemoteDataSourceImp() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        mealService = retrofit.create(MealService.class);
    }

    private MealService mealService;

    private static MealRemoteDataSourceImp productRemoteDataSourceImp = null;

    public static MealRemoteDataSourceImp getInstance() {
        if (productRemoteDataSourceImp == null) {
            productRemoteDataSourceImp = new MealRemoteDataSourceImp();
        }
        return productRemoteDataSourceImp;
    }


    @Override
    public void getMealsThatContainNetworkCall(MealNetworkCallback mealNetworkCallback, String name) {
        mealService.getMealsThatContain(name).enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                mealNetworkCallback.onSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                mealNetworkCallback.onFaliureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getMealByIdNetworkCall(MealNetworkCallback mealNetworkCallback, String id) {
                mealService.getMealById(id).enqueue(new Callback<Pojo>() {
                    @Override
                    public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                        mealNetworkCallback.onSuccessResult(response.body().getMeals());
                    }

                    @Override
                    public void onFailure(Call<Pojo> call, Throwable t) {
                        mealNetworkCallback.onFaliureResult(t.getMessage());
                    }
                });
    }

    @Override
    public void getRandomMealNetworkCall(MealNetworkCallback mealNetworkCallback) {
                mealService.getRandomMeal().enqueue(new Callback<Pojo>() {
                    @Override
                    public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                        mealNetworkCallback.onSuccessResult(response.body().getMeals());
                    }

                    @Override
                    public void onFailure(Call<Pojo> call, Throwable t) {
                        mealNetworkCallback.onFaliureResult(t.getMessage());
                    }
                });
    }

    @Override
    public void getAllCategoriesNetworkCall(CategoryNetworkCallback categoryNetworkCallback) {
                mealService.getAllCategories().enqueue(new Callback<Pojo>() {
                    @Override
                    public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                        categoryNetworkCallback.onSuccessResult(response.body().getMeals());
                    }

                    @Override
                    public void onFailure(Call<Pojo> call, Throwable t) {
                        categoryNetworkCallback.onFaliureResult(t.getMessage());
                    }
                });
    }

    @Override
    public void getAllCountriesNetworkCall(CountryNetworkCallback countryNetworkCallback) {
                mealService.getAllCountries().enqueue(new Callback<Pojo>() {
                    @Override
                    public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                        countryNetworkCallback.onSuccessResult(response.body().getMeals());
                    }

                    @Override
                    public void onFailure(Call<Pojo> call, Throwable t) {
                        countryNetworkCallback.onFaliureResult(t.getMessage());
                    }
                });
    }

    @Override
    public void getAllIngredientsNetworkCall(IngredientNetworkCallback ingredientNetworkCallback) {
                mealService.getAllIngredients().enqueue(new Callback<PojoIng>() {
                    @Override
                    public void onResponse(Call<PojoIng> call, Response<PojoIng> response) {
                        Log.i("TAG", "onResponse: "+response.toString());
                        Log.i("TAG", "onResponse: "+response.body().toString());
                        ingredientNetworkCallback.onSuccessResult(response.body().getIngredients());
                    }

                    @Override
                    public void onFailure(Call<PojoIng> call, Throwable t) {
                        ingredientNetworkCallback.onFaliureResult(t.getMessage());
                    }
                });
    }

    @Override
    public void getMealsInCategoryNetworkCall(MealNetworkCallback mealNetworkCallback, String category) {
                mealService.getMealsInCategory(category).enqueue(new Callback<Pojo>() {
                    @Override
                    public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                        mealNetworkCallback.onSuccessResult(response.body().getMeals());
                    }

                    @Override
                    public void onFailure(Call<Pojo> call, Throwable t) {
                        mealNetworkCallback.onFaliureResult(t.getMessage());
                    }
                });
    }

    @Override
    public void getMealsInCountryNetworkCall(MealNetworkCallback mealNetworkCallback, String country) {
                mealService.getMealsInCountry(country).enqueue(new Callback<Pojo>() {
                    @Override
                    public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                        mealNetworkCallback.onSuccessResult(response.body().getMeals());
                    }

                    @Override
                    public void onFailure(Call<Pojo> call, Throwable t) {
                        mealNetworkCallback.onFaliureResult(t.getMessage());
                    }
                });
    }

    @Override
    public void getMealsWithIngredientNetworkCall(MealNetworkCallback mealNetworkCallback, String ingredient) {
                mealService.getMealsWithIngredient(ingredient).enqueue(new Callback<Pojo>() {
                    @Override
                    public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                        mealNetworkCallback.onSuccessResult(response.body().getMeals());
                    }

                    @Override
                    public void onFailure(Call<Pojo> call, Throwable t) {
                        mealNetworkCallback.onFaliureResult(t.getMessage());
                    }
                });
    }
}