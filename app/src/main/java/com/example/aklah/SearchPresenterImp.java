package com.example.aklah;

import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.Model.Ingredient;
import com.example.aklah.Model.MealRepository;
import com.example.aklah.Network.CategoryNetworkCallback;
import com.example.aklah.Network.CountryNetworkCallback;
import com.example.aklah.Network.IngredientNetworkCallback;

import java.util.ArrayList;

public class SearchPresenterImp implements SearchPresenter, CategoryNetworkCallback, CountryNetworkCallback , IngredientNetworkCallback {

    MySearchView view;

    MealRepository repo;

    public SearchPresenterImp(MySearchView view, MealRepository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getCategories() {
        repo.getAllCategories(this);
    }

    @Override
    public void getCountries() {
        repo.getAllCountries(this);
    }

    @Override
    public void getIngredients() {
        repo.getAllIngredients(this);
    }

    @Override
    public void onSuccessResult(ArrayList<Category> categories) {
        view.getCategories(categories);
    }


    @Override
    public void onSuccessResultIngredient(ArrayList<Ingredient> ingredients) {
        view.getIngredients(ingredients);
    }

    @Override
    public void onFaliureResult(String errormsg) {
        view.showErrorMsg(errormsg);
    }

    @Override
    public void onSuccessResultCountry(ArrayList<Country> countries) {
        view.getCountries(countries);
    }

    @Override
    public void onFaliureResultCountry(String errormsg) {
            view.showErrorMsg(errormsg);
    }
}
