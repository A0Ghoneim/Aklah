package com.example.aklah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.MealRepository;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealRemoteDataSourceImp;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash);


        MealRepository mr= MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(this));
        new AllPresentear(mr);

        LottieAnimationView animationView = findViewById(R.id.animationView);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {
                Log.i("TAG", "onAnimationStart: ");

                if (FirebaseAuth.getInstance().getCurrentUser()==null){
                    Log.i("TAG", "onAnimationStart: true");
                    intent = new Intent(SplashActivity.this,Signin.class);
                }
                else {
                    Log.i("TAG", "onAnimationStart: false"+FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {

                startActivity(intent);
                finish();

            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });
    }
}