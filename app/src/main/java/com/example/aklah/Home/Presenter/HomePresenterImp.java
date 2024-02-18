package com.example.aklah.Home.Presenter;

import com.example.aklah.Home.View.HomeView;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepository;
import com.example.aklah.Network.MealNetworkCallback;
import com.example.aklah.Network.RandomNetworkCallBack;

import java.util.ArrayList;


public class HomePresenterImp implements HomePresenter, MealNetworkCallback , RandomNetworkCallBack {

    HomeView view;
    MealRepository repo;

    public HomePresenterImp(HomeView view, MealRepository repo) {
        this.view = view;
        this.repo = repo;
    }


    @Override
    public void getMealOfTheDay() {
            repo.getRandomMeal(this);
    }

    @Override
    public void getMealByID(String id) {
        repo.getMealById(this,id);
    }

    @Override
    public void clearEverything() {
        repo.clearEverything();
    }

    @Override
    public void onSuccessResult(ArrayList<Meal> meals) {
        view.showStoredMeal(meals.get(0));
    }

    @Override
    public void onFaliureResult(String errormsg) {
        view.showErrorMsg(errormsg);
    }

    @Override
    public void onSuccessResultRandom(ArrayList<Meal> meals) {
        view.showRandomMeal(meals.get(0));
    }

    @Override
    public void onFaliureResultRandom(String errormsg) {
        view.showErrorMsg(errormsg);
    }
}
