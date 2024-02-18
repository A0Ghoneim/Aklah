package com.example.aklah.Search.View;

import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.Model.Ingredient;
import com.example.aklah.Model.Meal;

import java.util.ArrayList;
import java.util.List;

public interface MySearchView {

    void getCategories(ArrayList<Category> categories);

    void getCountries(ArrayList<Country> countries);

    void getIngredients(ArrayList<Ingredient> ingredients);

    void setAllMeals(ArrayList<Meal> meals);

    void showErrorMsg(String error);


}
