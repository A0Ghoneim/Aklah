package com.example.aklah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
         navController = NavHostFragment.findNavController(navHostFragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.page_1){
                    navController.navigate(R.id.homeFragment);
                    return true;
                } else if (item.getItemId()==R.id.page_2) {
                    navController.navigate(R.id.searchFragment);
                    return true;
                }
                else if (item.getItemId()==R.id.page_3) {
                    navController.navigate(R.id.favouriteFragment);
                    return true;
                }
                else if (item.getItemId()==R.id.page_4) {
                    navController.navigate(R.id.scheduleFragment);
                    return true;
                }
                return false;
            }
        });



    }
}