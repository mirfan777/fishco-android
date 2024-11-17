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
import com.example.fishco.activity.article.ArticleDetailActivity;
import com.example.fishco.activity.encyclopedia.FishListActivity;
import com.example.fishco.activity.settings.ProfileActivity;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);

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
    }
}