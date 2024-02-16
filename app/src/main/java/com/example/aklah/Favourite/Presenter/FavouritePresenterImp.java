package com.example.aklah.Favourite.Presenter;

import com.example.aklah.Favourite.FavouriteView;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavouritePresenterImp implements FavouritePresenter{

    FavouriteView view;
    MealRepository repo;


    public FavouritePresenterImp(FavouriteView view, MealRepository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getFavourites() {
            view.setFavouritesFlowable(repo.getFavouriteMeals());
    }

    @Override
    public void deleteMeal(Meal meal) {
            repo.deleteMeal(meal);
    }
}
