package com.opus.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {


    private SharedPreferences preference;
    private String waiter_code="";

ImageView splash_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
//splash_logo=findViewById(R.id.splash_logo);
        preference = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
        waiter_code = preference.getString("waiter_code","");
        AlphaAnimation anim = new AlphaAnimation(0.5f, 0.5f);
        anim.setDuration(1000);
        anim.setRepeatCount(1);
        anim.setRepeatMode(Animation.ABSOLUTE);
        //splash_logo.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

              if (waiter_code!=null && !waiter_code.isEmpty()){
                  Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                  startActivity(intent);
                  finish();
              }
              else {

                  Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                  startActivity(intent);
                  finish();
              }
            }
        },2000);
    }
}
