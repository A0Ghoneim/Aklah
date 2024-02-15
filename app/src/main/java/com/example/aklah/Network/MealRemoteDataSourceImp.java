package com.example.aklah.Network;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.aklah.Model.PojoCategory;
import com.example.aklah.Model.PojoCountry;
import com.example.aklah.Model.PojoMeal;
import com.example.aklah.Model.PojoIng;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSourceImp implements MealRemoteDataSource {
    Retrofit retrofit;
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private MealRemoteDataSourceImp() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
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
        mealService.getMealsThatContain(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pojoMeal -> mealNetworkCallback.onSuccessResult(pojoMeal.getMeals()),throwable ->mealNetworkCallback.onFaliureResult(throwable.getMessage()));
    }

    @Override
    public void getMealByIdNetworkCall(MealNetworkCallback mealNetworkCallback, String id) {
                mealService.getMealById(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pojoMeal -> mealNetworkCallback.onSuccessResult(pojoMeal.getMeals()),throwable ->mealNetworkCallback.onFaliureResult(throwable.getMessage()));
    }

    @Override
    public void getRandomMealNetworkCall(MealNetworkCallback mealNetworkCallback) {
                mealService.getRandomMeal().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pojoMeal -> mealNetworkCallback.onSuccessResult(pojoMeal.getMeals()),throwable ->mealNetworkCallback.onFaliureResult(throwable.getMessage()));
    }

    @Override
    public void getAllCategoriesNetworkCall(CategoryNetworkCallback categoryNetworkCallback) {
                mealService.getAllCategories().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pojoCategory -> categoryNetworkCallback.onSuccessResult(pojoCategory.getCategories()),throwable -> categoryNetworkCallback.onFaliureResult(throwable.getMessage()));
    }

    @Override
    public void getAllCountriesNetworkCall(CountryNetworkCallback countryNetworkCallback) {
                mealService.getAllCountries().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pojoCountry -> countryNetworkCallback.onSuccessResultCountry(pojoCountry.getCountries()),throwable -> countryNetworkCallback.onFaliureResultCountry(throwable.getMessage()));
    }

    @Override
    public void getAllIngredientsNetworkCall(IngredientNetworkCallback ingredientNetworkCallback) {
                mealService.getAllIngredients().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pojoIng -> ingredientNetworkCallback.onSuccessResult(pojoIng.getIngredients()),throwable -> ingredientNetworkCallback.onFaliureResult(throwable.getMessage()) );
    }

    @Override
    public void getMealsInCategoryNetworkCall(MealNetworkCallback mealNetworkCallback, String category) {
                mealService.getMealsInCategory(category).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pojoMeal -> mealNetworkCallback.onSuccessResult(pojoMeal.getMeals()),throwable ->mealNetworkCallback.onFaliureResult(throwable.getMessage()));
    }

    @Override
    public void getMealsInCountryNetworkCall(MealNetworkCallback mealNetworkCallback, String country) {
                mealService.getMealsInCountry(country).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pojoMeal -> mealNetworkCallback.onSuccessResult(pojoMeal.getMeals()),throwable ->mealNetworkCallback.onFaliureResult(throwable.getMessage()));
    }

    @Override
    public void getMealsWithIngredientNetworkCall(MealNetworkCallback mealNetworkCallback, String ingredient) {
                mealService.getMealsWithIngredient(ingredient).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pojoMeal -> mealNetworkCallback.onSuccessResult(pojoMeal.getMeals()),throwable ->mealNetworkCallback.onFaliureResult(throwable.getMessage()));
    }
}