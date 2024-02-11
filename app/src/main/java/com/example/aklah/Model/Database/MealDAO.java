package com.example.aklah.Model.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.aklah.Model.Meal;

import java.util.List;

@Dao
public interface MealDAO {
    @Query("Select * from Meal where favourite = true")
    LiveData<List<Meal>> getFavouriteMeals();
    @Query("Select * from Meal where idMeal =:id")
    LiveData<Meal> getMealById(int id);
    @Insert
    void insertProduct(Meal meal);
    @Delete
    void deleteProduct(Meal meal);
}
