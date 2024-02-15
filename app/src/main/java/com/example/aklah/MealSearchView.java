package com.example.aklah;

import com.example.aklah.Model.Meal;

import java.util.ArrayList;

public interface MealSearchView {
    public void showAllMeals(ArrayList<Meal> meals);
    public void showErrorMsg(String error);
}
