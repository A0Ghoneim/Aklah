package com.example.aklah.Network;

import com.example.aklah.Model.Category;

import java.util.ArrayList;

public interface CategoryNetworkCallback {
     void onSuccessResult(ArrayList<Category> categories);
     void onFaliureResult(String errormsg);
}
