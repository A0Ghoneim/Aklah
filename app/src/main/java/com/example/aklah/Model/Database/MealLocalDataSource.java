package com.example.aklah.Model.Database;

import androidx.lifecycle.LiveData;


import com.example.aklah.Model.Meal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealLocalDataSource {
    Flowable<List<Meal>> getFavouriteMeals();

    Single<Meal> getMealById(String id);

    Flowable<List<Meal>> getMealByDay(int day);

    void delete(Meal meal);

    void deletesavedmeal(String idmeal,int day);

    Completable insert(Meal meal);

    void clearEverything();
}
