package com.drax.draxproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView logodrax;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logodrax = findViewById(R.id.logodrax);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideup);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Splash.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logodrax.startAnimation(anim);
    }
}
