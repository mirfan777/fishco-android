package com.example.fishco.activity.article;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.aquarium.AquariumListActivity;
import com.example.fishco.activity.chatbot.ChatbotActivity;
import com.example.fishco.activity.home.HomepageActivity;
import com.example.fishco.activity.scanner.ScannerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ArticleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_list);

        // Inset handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                startActivity(new Intent(ArticleListActivity.this, HomepageActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.article) {
                // Stay on the current activity
                return true;
            } else if (itemId == R.id.scanner) {
                startActivity(new Intent(ArticleListActivity.this, ScannerActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.aquarium) {
                startActivity(new Intent(ArticleListActivity.this, AquariumListActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.fishbot) {
                startActivity(new Intent(ArticleListActivity.this, ChatbotActivity.class));
                finish();
                return true;
            }

            return false;
        });

        // Set the default selected item
        bottomNavigation.setSelectedItemId(R.id.article);
    }
}