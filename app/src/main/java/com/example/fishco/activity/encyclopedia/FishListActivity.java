package com.example.fishco.activity.encyclopedia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.aquarium.AquariumListActivity;
import com.example.fishco.activity.article.ArticleListActivity;
import com.example.fishco.activity.home.HomepageActivity;
import com.example.fishco.activity.scanner.ScannerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FishListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fish_list);

        // Menangkap data kategori dari intent
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        // Menampilkan kategori di header
        TextView categoryHeader = findViewById(R.id.category_name);
        if (categoryName != null) {
            categoryHeader.setText(categoryName);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Inisialisasi tombol back
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            // Kembali ke HomepageActivity
            Intent intent = new Intent(FishListActivity.this, HomepageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); // Menghindari instance baru
            startActivity(intent);
            finish(); // Menutup current activity
        });

    }
}