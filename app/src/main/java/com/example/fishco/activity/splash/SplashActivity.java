package com.example.fishco.activity.splash;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fishco.R;
import com.example.fishco.activity.auth.GetStartedActivity;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Fishco); // Ganti ke tema utama aplikasi
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progress_bar);

        // Animasi progress bar
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        progressAnimator.setDuration(3000); // Durasi animasi 2 detik
        progressAnimator.start();

        // Berpindah ke activity berikutnya setelah animasi selesai
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, GetStartedActivity.class));
            finish();
        }, 3000); // Sama dengan durasi animasi
    }
}
