package com.example.aklah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    int guest;
    NetworkChangeReciever networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        guest = intent.getIntExtra("guest",0);
        SharedPreferences sharedPreferences = getSharedPreferences("myapp",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("guest",guest);
        editor.commit();
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
         navController = NavHostFragment.findNavController(navHostFragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        networkChangeReceiver = new NetworkChangeReciever(navController);

        // Register the receiver
        registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.page_1){
                    if (navController.getCurrentDestination().getId()!=R.id.homeFragment) {
                        navController.navigate(R.id.homeFragment);
                    }
                    return true;
                } else if (item.getItemId()==R.id.page_2) {
                    if (navController.getCurrentDestination().getId()!=R.id.searchFragment) {
                        navController.navigate(R.id.searchFragment);
                    }
                    return true;
                }
                else if (item.getItemId()==R.id.page_3) {
                    if (navController.getCurrentDestination().getId()!=R.id.favouriteFragment) {
                        navController.navigate(R.id.favouriteFragment);
                    }
                    return true;
                }
                else if (item.getItemId()==R.id.page_4) {
                    if (navController.getCurrentDestination().getId()!=R.id.scheduleFragment) {
                        navController.navigate(R.id.scheduleFragment);
                    }
                    return true;
                }
                return false;            }
        });
       /* if (guest==1){
                bottomNavigationView.getMenu().getItem(2).setEnabled(false);
            bottomNavigationView.getMenu().getItem(2).se
            bottomNavigationView.getMenu().getItem(3).setEnabled(false);

        }*/

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId() == R.id.mealInfoFragment) {

                    bottomNavigationView.setVisibility(View.GONE);
                } else {

                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
                if (navDestination.getId() == R.id.homeFragment){
                    bottomNavigationView.setSelectedItemId(R.id.page_1);
                   // navController.popBackStack(null,1);

                } else if (navDestination.getId() == R.id.searchFragment) {
                    bottomNavigationView.setSelectedItemId(R.id.page_2);


                } else if (navDestination.getId() == R.id.favouriteFragment) {
                    bottomNavigationView.setSelectedItemId(R.id.page_3);


                } else if (navDestination.getId() == R.id.scheduleFragment) {
                    bottomNavigationView.setSelectedItemId(R.id.page_4);
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}