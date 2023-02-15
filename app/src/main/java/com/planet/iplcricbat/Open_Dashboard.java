package com.planet.iplcricbat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Open_Dashboard extends AppCompatActivity {

    Button play_now,add_cash;
    SharedPreferences preferences;
    String user_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_dashboard);

        play_now = findViewById(R.id.play_now);
        add_cash = findViewById(R.id.add_cash);

        preferences = getSharedPreferences("details", MODE_PRIVATE);





        play_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Open_Dashboard.this,MainActivity.class));
                finish();
            }
        });
        add_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Open_Dashboard.this,Wallet.class));
            }
        });
    }

}