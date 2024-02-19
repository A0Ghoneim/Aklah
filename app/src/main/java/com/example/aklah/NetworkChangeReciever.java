package com.example.aklah;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.navigation.NavController;

public class NetworkChangeReciever extends BroadcastReceiver {
    NavController navController;

    public NetworkChangeReciever(NavController navController){
        this.navController=navController;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkConnected(context)) {
            // Network is connected, do something if needed
        } else {
            // Network is not connected, show suggestion to open Wi-Fi
            showToast(context, "No internet connection. Open Wi-Fi for better connectivity.");
        }
    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    private void showToast(Context context, String message) {
        navController.navigate(R.id.homeFragment);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
