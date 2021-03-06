package com.example.mood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mood.Activities.MainActivity;
import com.example.mood.Activities.MainUi;

public class SplashActivity extends AppCompatActivity {

    int timeout=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainUi.class);
                startActivity(intent);
                finish();

            }
        },timeout);


    }
}
