package com.example.aklah.Model.Database;

import androidx.lifecycle.LiveData;


import com.example.aklah.Model.Meal;

import java.util.List;

public interface MealLocalDataSource {
    LiveData<List<Meal>> getFavouriteMeals();

    void delete(Meal meal);

    void insert(Meal meal);
}
