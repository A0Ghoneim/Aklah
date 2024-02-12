package com.example.aklah.Network;

import com.example.aklah.Model.Country;

import java.util.ArrayList;

public interface CountryNetworkCallback {
     void onSuccessResult(ArrayList<Country> countries);
     void onFaliureResult(String errormsg);
}
