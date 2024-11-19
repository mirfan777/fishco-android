package com.example.fishco.activity.encyclopedia;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.fishco.R;

public class FishDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_detail);

        // Ambil data dari Intent
        String fishName = getIntent().getStringExtra("NAME");
        String fishScientificName = getIntent().getStringExtra("SPECIES");
        String fishOverview = getIntent().getStringExtra("OVERVIEW");
        String temperature = getIntent().getStringExtra("TEMPERATURE");
        String fishImageUrl = getIntent().getStringExtra("IMAGE_URL");

        // Hubungkan komponen
        TextView nameTextView = findViewById(R.id.fish_name);
        TextView scientificNameTextView = findViewById(R.id.fish_species);
        TextView overviewTextView = findViewById(R.id.overview_text);
        TextView temperatureTextView = findViewById(R.id.temperature_value);
        ImageView fishImageView = findViewById(R.id.fish_image);

        // Masukkan data ke komponen
        nameTextView.setText(fishName);
        scientificNameTextView.setText(fishScientificName);
        overviewTextView.setText(fishOverview);
        temperatureTextView.setText(temperature);

        // Load gambar dengan Glide
        Glide.with(this)
                .load(fishImageUrl)
                .placeholder(R.drawable.fish_detail)
                .into(fishImageView);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup close button
        ImageButton closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> finish()); // Kembali ke aktivitas sebelumnya
    }
}

