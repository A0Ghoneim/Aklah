package com.example.aklah.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PojoCategory implements Serializable {
    @SerializedName("categories")
    private ArrayList<Category> categories;


    public PojoCategory(ArrayList<Category> categories) {
        this.categories = categories;
    }


    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
