package com.example.aklah.Home.Presenter;

import com.example.aklah.Model.Meal;

public interface HomePresenter {
    public void getMealOfTheDay();

    void getMealByID(String id);

    void clearEverything();
   // public void addToFav(Meal meal);
}
