package com.example.fishco.activity.encyclopedia;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}
