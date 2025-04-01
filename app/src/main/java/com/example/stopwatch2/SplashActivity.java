package com.example.stopwatch2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay splash screen for 2 seconds
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, com.example.stopwatch2.MainActivity.class));
            finish(); // Close splash screen
        }, 2000);
    }
}
