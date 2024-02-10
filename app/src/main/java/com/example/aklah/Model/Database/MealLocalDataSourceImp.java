package com.example.aklah.Model.Database;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.aklah.Model.Meal;

import java.util.List;

public class MealLocalDataSourceImp implements MealLocalDataSource {
    private Context context;
    private MealDAO dao;
    private LiveData<List<Meal>> favouriteMeals;

    private static MealLocalDataSource mealLocalDataSource = null;
    private MealLocalDataSourceImp(Context context){
        this.context=context;
         dao = AppDataBase.getInstance(context).getMealDAO();
         favouriteMeals =dao.getFavouriteMeals();

    }
    public static MealLocalDataSource getInstance(Context context){
        if (mealLocalDataSource ==null){
            mealLocalDataSource =new MealLocalDataSourceImp(context);
        }
        return mealLocalDataSource;
    }
    @Override
    public LiveData<List<Meal>> getFavouriteMeals(){
        return favouriteMeals;
    }
    @Override
    public void delete(Meal meal){
        new Thread(){
            @Override
            public void run() {
                dao.deleteProduct(meal);
            }
        }.start();
    }
    @Override
    public void insert(Meal meal){
        new Thread(){
            @Override
            public void run() {
                dao.insertProduct(meal);
            }
        }.start();
    }
}
