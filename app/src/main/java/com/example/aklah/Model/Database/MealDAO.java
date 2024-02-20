package com.example.aklah.Model.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.aklah.Model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDAO {
    //group by idMeal
    @Query("Select * from Meal where favourite = 1")
    Flowable<List<Meal>> getFavouriteMeals();
    @Query("Select * from Meal where myid =:id")
    Single<Meal> getMealById(String id);
    //group by idMeal
    @Query("Select * from Meal where day=:day")
    Flowable<List<Meal>> getMealByDay(int day);
    @Insert
    Completable insertProduct(Meal meal);
   /* @Query("Delete from Meal where idMeal=:idmeal and favourite = 1")
    void deletefavmeal(String idmeal);*/
   /* @Query("Delete from Meal where idMeal=:idmeal and day = :day")
    void deletesavedmeal(String idmeal,int day);*/

    @Delete
    void delete(Meal meal);

    @Query("Delete from Meal")
    void clearEverything();
}
