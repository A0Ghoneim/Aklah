package com.example.aklah;

import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.Model.MealRepository;
import com.example.aklah.Network.CategoryNetworkCallback;
import com.example.aklah.Network.CountryNetworkCallback;

import java.util.ArrayList;

public class SearchPresenterImp implements SearchPresenter, CategoryNetworkCallback, CountryNetworkCallback {

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
    public void onSuccessResult(ArrayList<Category> categories) {
        view.getCategories(categories);
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
