package com.example.aklah.Model.Database;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.aklah.Model.Meal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDataSourceImp implements MealLocalDataSource {
    private Context context;
    private MealDAO dao;
    private Flowable<List<Meal>> favouriteMeals;

    private Single<Meal> requestedMeal;

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
    public Flowable<List<Meal>> getFavouriteMeals(){
        return favouriteMeals;
    }
    @Override
    public Single<Meal> getMealById(String id) {
        requestedMeal =dao.getMealById(id);
        return requestedMeal;
    }

    @Override
    public Flowable<List<Meal>> getMealByDay(int day) {
        return  dao.getMealByDay(day);
    }


    @Override
    public void delete(Meal meal){
        new Thread(){
            @Override
            public void run() {
                dao.deletefavmeal(meal.getIdMeal());
            }
        }.start();
    }

    @Override
    public void deletesavedmeal(String idmeal, int day) {
        new Thread(){
            @Override
            public void run() {
                dao.deletesavedmeal(idmeal,day);
            }
        }.start();
    }

    @Override
    public Completable insert(Meal meal){
       return dao.insertProduct(meal);
    }
}
