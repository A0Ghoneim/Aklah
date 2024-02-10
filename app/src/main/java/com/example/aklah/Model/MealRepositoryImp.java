package com.example.aklah.Model;

import androidx.lifecycle.LiveData;

import com.example.aklah.Model.Database.MealLocalDataSource;
import com.example.aklah.Network.IngredientNetworkCallback;
import com.example.aklah.Network.MealRemoteDataSource;
import com.example.aklah.Network.MealNetworkCallback;
//import com.example.mvparchitecture.Network.NetworkCallback;
//import com.example.mvparchitecture.Network.ProductRemoteDataSource;

import java.util.List;

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
    public LiveData<List<Meal>> getFavouriteMeals(){return local.getFavouriteMeals();}
    @Override
    public void getMealsInCategory(MealNetworkCallback mealNetworkCallback,String category){remote.getMealsInCategoryNetworkCall(mealNetworkCallback,category);}

    @Override
    public void getAllIngredients(IngredientNetworkCallback ingredientNetworkCallback) {
        remote.getAllIngredientsNetworkCall(ingredientNetworkCallback);
    }

    @Override
    public void insertMeal(Meal meal){local.insert(meal);}
    @Override
    public void deleteMeal(Meal meal){local.delete(meal);}
}
