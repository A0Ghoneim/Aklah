package com.example.aklah.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;
@Entity(tableName = "Meal")
public class Meal {
    //@SerializedName("idMeal")
    @PrimaryKey
    @NonNull
    private int id;

    //@SerializedName("strMeal")
    private String name;

    //@SerializedName("strCategory")
    private String category;

    //@SerializedName("strArea")
    private String country;

    //@SerializedName("strInstructions")
    private String instructions;

    //@SerializedName("strMealThumb")
    private String imageLink;

   // @SerializedName("strYoutube")
    private String videoLink;

  //  @SerializedName("ingredients")
    private List<String> ingredients;

   // @SerializedName("measures")
    private List<String> measures;

    private boolean favourite;

    // Add getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }

    public boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}