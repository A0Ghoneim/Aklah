package com.example.aklah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.MealRepository;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealRemoteDataSourceImp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MealRepository mr= MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(this));
        new AllPresentear(mr);

        LottieAnimationView animationView = findViewById(R.id.animationView);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);

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