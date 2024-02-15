package com.example.aklah;

import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;

import java.util.ArrayList;
import java.util.List;

public interface MySearchView {

    void getCategories(ArrayList<Category> categories);

    void getCountries(ArrayList<Country> countries);

    void showErrorMsg(String error);


}
