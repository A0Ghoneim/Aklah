package com.example.aklah.Network;

import com.example.aklah.Model.Category;

import java.util.ArrayList;

public interface CategoryNetworkCallback {
     void onSuccessResultCategory(ArrayList<Category> categories);
     void onFaliureResultCategory(String errormsg);
}
