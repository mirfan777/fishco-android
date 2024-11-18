package com.example.fishco.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.home.HomepageActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Tombol Back
        findViewById(R.id.back_button).setOnClickListener(v -> {
            // Intent ke HomepageActivity
            Intent intent = new Intent(ProfileActivity.this, HomepageActivity.class);
            startActivity(intent);
            finish(); // Agar tidak kembali ke ProfileActivity
        });

        // Handling system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // LinearLayout untuk Informasi Personal
        LinearLayout personalInfoLayout = findViewById(R.id.linearLayout_personal_info);
        personalInfoLayout.setOnClickListener(v -> {
            // Intent ke TermOfServiceActivity
            Intent intent = new Intent(ProfileActivity.this, TermOfServiceActivity.class);
            startActivity(intent);
        });

    }
}