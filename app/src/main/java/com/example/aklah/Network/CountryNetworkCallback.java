package com.example.aklah.Network;

import com.example.aklah.Model.Country;

import java.util.ArrayList;

public interface CountryNetworkCallback {
     void onSuccessResultCountry(ArrayList<Country> countries);
     void onFaliureResultCountry(String errormsg);
}
