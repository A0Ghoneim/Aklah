package com.example.aklah.Network;

public interface MealRemoteDataSource {
    void getMealsThatContainNetworkCall(MealNetworkCallback mealNetworkCallback, String name);
    void getMealByIdNetworkCall(MealNetworkCallback mealNetworkCallback, String id);
    void getRandomMealNetworkCall(MealNetworkCallback mealNetworkCallback);
    void getAllCategoriesNetworkCall(CategoryNetworkCallback categoryNetworkCallback);
    void getAllCountriesNetworkCall(CountryNetworkCallback countryNetworkCallback);
    void getAllIngredientsNetworkCall(IngredientNetworkCallback ingredientNetworkCallback);
    void getMealsInCategoryNetworkCall(MealNetworkCallback mealNetworkCallback,String category);
    void getMealsInCountryNetworkCall(MealNetworkCallback mealNetworkCallback,String country);
    void getMealsWithIngredientNetworkCall(MealNetworkCallback mealNetworkCallback,String ingredient);






}
