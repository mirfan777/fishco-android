package com.example.fishco.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.aquarium.AquariumListActivity;
import com.example.fishco.activity.article.ArticleDetailActivity;
import com.example.fishco.activity.article.ArticleListActivity;
import com.example.fishco.activity.chatbot.ChatbotActivity;
import com.example.fishco.activity.encyclopedia.FishListActivity;
import com.example.fishco.activity.scanner.ScannerActivity;
import com.example.fishco.activity.settings.ProfileActivity;
import com.example.fishco.model.Aquarium;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.widget.TextView;
import java.util.Calendar;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);

        // Tambahkan kode greeting setelah setContentView
        TextView greetingText = findViewById(R.id.greeting_text);

        // Ambil waktu sekarang
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Tentukan greeting berdasarkan waktu
        String greeting;
        if (hour >= 4 && hour < 12) {
            greeting = "Selamat Pagi! 🌅";  // Subuh sampai menjelang dzuhur
        } else if (hour >= 12 && hour < 15) {
            greeting = "Selamat Siang! ☀️";  // Dzuhur sampai menjelang ashar
        } else if (hour >= 15 && hour < 18) {
            greeting = "Selamat Sore! 🌇";   // Ashar sampai menjelang maghrib
        } else {
            greeting = "Selamat Malam! 🌙";  // Maghrib dan Isya
        }

        // Set greeting ke TextView
        greetingText.setText(greeting);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Intent untuk navigasi ke ProfileActivity
        ImageView profilePicture = findViewById(R.id.profile_picture);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        FrameLayout frameLayout = findViewById(R.id.frame_layout_article);
        frameLayout.setOnClickListener(view -> {
            Intent intent = new Intent(HomepageActivity.this, ArticleDetailActivity.class);
            startActivity(intent);
        });
        // Freshwater Category
        LinearLayout categoryFreshwater = findViewById(R.id.category_freshwater);
        categoryFreshwater.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, FishListActivity.class);
            intent.putExtra("CATEGORY_NAME", "Freshwater Fish");
            startActivity(intent);
        });
        // Brackfish Category
        LinearLayout categoryMarine = findViewById(R.id.category_marine);
        categoryMarine.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, FishListActivity.class);
            intent.putExtra("CATEGORY_NAME", "Marine Fish");
            startActivity(intent);
        });
        // Invertebrates Category
        LinearLayout categoryInvertebrates = findViewById(R.id.category_invertebrates);
        categoryInvertebrates.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, FishListActivity.class);
            intent.putExtra("CATEGORY_NAME", "Freshwater Fish");
            startActivity(intent);
        });
        // Coldwater Category
        LinearLayout categoryColdwater = findViewById(R.id.category_coldwater);
        categoryColdwater.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, FishListActivity.class);
            intent.putExtra("CATEGORY_NAME", "Freshwater Fish");
            startActivity(intent);
        });
        // Predatory Category
        LinearLayout categoryPredatory = findViewById(R.id.category_predatory);
        categoryPredatory.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, FishListActivity.class);
            intent.putExtra("CATEGORY_NAME", "Freshwater Fish");
            startActivity(intent);
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                // Handle home navigation
                return true;
            } else if (itemId == R.id.article) {
                // Navigate to articles
                startActivity(new Intent(HomepageActivity.this, ArticleListActivity.class));
                return true;
            } else if (itemId == R.id.scanner) {
                // Navigate to scanner
                startActivity(new Intent(HomepageActivity.this, ScannerActivity.class));
                return true;
            } else if (itemId == R.id.aquarium) {
                // Navigate to My Aquarium
                startActivity(new Intent(HomepageActivity.this, AquariumListActivity.class));
                return true;
            } else if (itemId == R.id.fishbot) {
                // Navigate to Profile
                startActivity(new Intent(HomepageActivity.this, ChatbotActivity.class));
                return true;
            }

            return false;
        });
    }
}