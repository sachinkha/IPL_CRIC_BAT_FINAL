package com.planet.iplcricbat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Screen extends AppCompatActivity {

    SharedPreferences preferences;
    String user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preferences = getSharedPreferences("details", MODE_PRIVATE);
                user_id = preferences.getString("user_id", null);
                if (user_id != null) {
                    startActivity(new Intent(Splash_Screen.this, Open_Dashboard.class));
                    finish();
                } else {
                    startActivity(new Intent(Splash_Screen.this, Login.class));
                    finish();

                }
            }
        }, 1000);

    }
}