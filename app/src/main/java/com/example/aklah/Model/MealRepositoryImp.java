package com.example.aklah.Model;

import androidx.lifecycle.LiveData;

import com.example.aklah.Model.Database.MealLocalDataSource;
import com.example.aklah.Network.CategoryNetworkCallback;
import com.example.aklah.Network.CountryNetworkCallback;
import com.example.aklah.Network.IngredientNetworkCallback;
import com.example.aklah.Network.MealRemoteDataSource;
import com.example.aklah.Network.MealNetworkCallback;
//import com.example.mvparchitecture.Network.NetworkCallback;
//import com.example.mvparchitecture.Network.ProductRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealRepositoryImp implements MealRepository {
    MealRemoteDataSource remote;
    MealLocalDataSource local;
    private static MealRepository repo = null;
    public static MealRepository getInstance(MealRemoteDataSource mrds, MealLocalDataSource mlds){
        if (repo==null){
            repo = new MealRepositoryImp(mrds,mlds);
        }
        return repo;
    }
    private MealRepositoryImp(MealRemoteDataSource mrds, MealLocalDataSource mlds){
        remote=mrds;
        local=mlds;
    }
    @Override
    public Flowable<List<Meal>> getFavouriteMeals(){return local.getFavouriteMeals();}

    @Override
    public Single<Meal> getMealById(String id) {
        return local.getMealById(id);
    }
    @Override
    public Completable insertMeal(Meal meal){return local.insert(meal);}
    @Override
    public void deleteMeal(Meal meal){local.delete(meal);}

    @Override
    public void deletesavedmeal(String idmeal, int day) {
        local.deletesavedmeal(idmeal,day);
    }

    @Override
    public void getMealsInCategory(MealNetworkCallback mealNetworkCallback,String category){remote.getMealsInCategoryNetworkCall(mealNetworkCallback,category);}

    @Override
    public void getMealsInCountry(MealNetworkCallback mealNetworkCallback, String country) {
            remote.getMealsInCountryNetworkCall(mealNetworkCallback,country);
    }

    @Override
    public void getMealsWithIngredient(MealNetworkCallback mealNetworkCallback, String ingredient) {
            remote.getMealsWithIngredientNetworkCall(mealNetworkCallback,ingredient);
    }

    @Override
    public void getAllIngredients(IngredientNetworkCallback ingredientNetworkCallback) {
        remote.getAllIngredientsNetworkCall(ingredientNetworkCallback);
    }



    @Override
    public void getMealsThatContain(MealNetworkCallback mealNetworkCallback, String name) {
        remote.getMealsThatContainNetworkCall(mealNetworkCallback,name);
    }

    @Override
    public void getMealById(MealNetworkCallback mealNetworkCallback, String id) {
            remote.getMealByIdNetworkCall(mealNetworkCallback,id);
    }

    @Override
    public Flowable<List<Meal>> getMealByDay(int day) {
        return local.getMealByDay(day);
    }

    @Override
    public void getRandomMeal(MealNetworkCallback mealNetworkCallback) {
            remote.getRandomMealNetworkCall(mealNetworkCallback);
    }

    @Override
    public void getAllCategories(CategoryNetworkCallback categoryNetworkCallback) {
            remote.getAllCategoriesNetworkCall(categoryNetworkCallback);
    }

    @Override
    public void getAllCountries(CountryNetworkCallback countryNetworkCallback) {
            remote.getAllCountriesNetworkCall(countryNetworkCallback);
    }
}
