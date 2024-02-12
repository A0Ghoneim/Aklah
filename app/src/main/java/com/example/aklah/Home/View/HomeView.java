package com.example.aklah.Home.View;

import com.example.aklah.Model.Meal;

import java.util.ArrayList;

public interface HomeView {
    public void showMeal(Meal meal);
    public void showErrorMsg(String error);
}
