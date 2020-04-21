package com.example.mood;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mood.Activities.ConverseActivity;

public class updateConvos extends Service {

    ConverseActivity converseActivity;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {

        Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service was STARTED", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        Toast.makeText(this, "KILLED ", Toast.LENGTH_LONG).show();

        super.onDestroy();
    }
}
