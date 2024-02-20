package com.example.aklah.Favourite;

import com.example.aklah.Model.Meal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavouriteView {
     void setFavouritesFlowable(Flowable<List<Meal>> flowable);
}
