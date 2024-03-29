package com.example.aklah.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PojoIng implements Serializable {
    @SerializedName("meals")
    private ArrayList<Ingredient> ingredients;


    public PojoIng(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
