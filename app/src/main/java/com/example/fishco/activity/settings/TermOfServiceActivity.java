package com.example.fishco.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.settings.ProfileActivity;

public class TermOfServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_term_of_service);

        // Handle insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Back button functionality
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            // Navigate back to ProfileActivity
            Intent intent = new Intent(TermOfServiceActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish(); // Close current activity
        });
    }
}
