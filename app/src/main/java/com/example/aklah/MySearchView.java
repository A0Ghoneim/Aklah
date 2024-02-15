package com.example.aklah;

import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.Model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public interface MySearchView {

    void getCategories(ArrayList<Category> categories);

    void getCountries(ArrayList<Country> countries);

    void getIngredients(ArrayList<Ingredient> ingredients);

    void showErrorMsg(String error);


}
