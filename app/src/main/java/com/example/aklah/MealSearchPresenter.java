package com.example.aklah;

import com.example.aklah.Model.MealRepository;

public interface MealSearchPresenter {
   void getMealsInCategory(String category);
    void getMealsInCountry(String country);
    void getMealsByIngredient(String ingredient);

}
