package com.example.sanitizerseller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;


public class SplashScreen extends FragmentActivity {


    private Animation animation;
    private ImageView logo;
    private TextView appTitle;
    private TextView appSlogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo);
        appTitle = findViewById(R.id.grocery);
        appSlogan = findViewById(R.id.slogan);


            if (savedInstanceState == null) {
                flyIn();
            }

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    endSplash();
                }
            }, 5000);
    }

    private void flyIn() {
        animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_animation);
        appTitle.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        appSlogan.startAnimation(animation);
    }

    private void endSplash() {
        animation = AnimationUtils.loadAnimation(this,
                R.anim.logo_animation_back);
        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_animation_back);
        appTitle.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.pro_animation_back);
        appSlogan.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {

                Intent intent = new Intent(getApplicationContext(),
                     LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

}
