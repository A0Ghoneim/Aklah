package com.example.aklah.MealSearch.Presenter;

import com.example.aklah.MealSearch.View.MealSearchView;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepository;
import com.example.aklah.Network.MealNetworkCallback;

import java.util.ArrayList;

public class MealSearchPresenterImp implements MealSearchPresenter, MealNetworkCallback {

    MealSearchView view;

    MealRepository repo;

    public MealSearchPresenterImp(MealSearchView view, MealRepository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getMealsInCategory(String category) {
        repo.getMealsInCategory(this,category);
    }

    @Override
    public void getMealsInCountry(String country) {
        repo.getMealsInCountry(this,country);
    }

    @Override
    public void getMealsByIngredient(String ingredient) {
        repo.getMealsWithIngredient(this,ingredient);
    }

    @Override
    public void onSuccessResult(ArrayList<Meal> meals) {
        view.showAllMeals(meals);
    }

    @Override
    public void onFaliureResult(String errormsg) {
        view.showErrorMsg(errormsg);
    }
}
