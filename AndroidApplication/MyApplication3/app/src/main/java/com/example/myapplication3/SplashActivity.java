package com.example.myapplication3;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ProgressBar progressBar = findViewById(R.id.loading_progress_bar);
        new Handler().postDelayed(new Runnable() {
            int progress = 0;
            //Progress bar keeps loading until 100%
            public void run() {
                if (progress < 100) {
                    progressBar.setProgress(progress += 10/3);
                    new Handler().postDelayed(this, 100);
                } else {
                    //once progress hits 100% it opens the main activity
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 100);
    }
}