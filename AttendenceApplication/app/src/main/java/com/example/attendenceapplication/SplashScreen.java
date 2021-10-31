package com.example.attendenceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    Animation top_animation;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        top_animation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        logo = findViewById(R.id.logo);
        logo.setAnimation(top_animation);
        new Handler().postDelayed(() -> {
            Intent intent= new Intent(SplashScreen.this, login.class);
//                startActivity(intent);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this).toBundle());


            finish();
        },5000);
    }
}