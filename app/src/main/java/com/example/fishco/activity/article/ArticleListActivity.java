package com.example.fishco.activity.article;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fishco.R;
import com.example.fishco.activity.aquarium.AquariumListActivity;
import com.example.fishco.activity.chatbot.ChatbotActivity;
import com.example.fishco.activity.home.HomepageActivity;
import com.example.fishco.activity.scanner.ScannerActivity;
import com.example.fishco.activity.settings.InformasiPersonalActivity;
import com.example.fishco.activity.settings.ProfileActivity;
import com.example.fishco.model.Article;
import com.example.fishco.service.ApiResponse;
import com.example.fishco.model.Article;
import com.example.fishco.service.ArticleApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.example.fishco.adapter.ArticleAdapter;
import com.example.fishco.http.RetrofitClient;
import com.bumptech.glide.Glide;




public class ArticleListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        // Setup Large Article
        TextView largeArticleTitle = findViewById(R.id.large_article_title);
        ImageView largeArticleImage = findViewById(R.id.large_article_image);
        TextView largeArticleUsername = findViewById(R.id.large_article_username);
        TextView largeArticleTime = findViewById(R.id.large_article_time);

        RecyclerView smallArticlesRecyclerView = findViewById(R.id.small_articles_recycler_view);
        smallArticlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArticleApiService apiService = RetrofitClient.getClient(this).create(ArticleApiService.class);
        apiService.getArticles().enqueue(new Callback<ApiResponse<List<Article>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Article>>> call, Response<ApiResponse<List<Article>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Article> articles = response.body().data;

                    // Set first article as large article
                    if (!articles.isEmpty()) {
                        Article largeArticle = articles.get(0);
                        largeArticleTitle.setText(largeArticle.getTitle());
                        largeArticleUsername.setText("Author Name"); // Replace with actual field if available
                        largeArticleTime.setText(largeArticle.getCreatedAt());

                        Glide.with(ArticleListActivity.this)
                                .load(largeArticle.getThumbnail())
                                .into(largeArticleImage);

                        // Remove the first article from the list
                        articles.remove(0);
                    }

                    // Set remaining articles in RecyclerView
                    ArticleAdapter adapter = new ArticleAdapter(ArticleListActivity.this, articles);
                    smallArticlesRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Article>>> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });


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

        // LinearLayout untuk gambar besar
        LinearLayout largeArticleLayout = findViewById(R.id.large_article_layout);
        largeArticleLayout.setOnClickListener(v -> {
            // Intent ke TermOfServiceActivity
            Intent intent = new Intent(ArticleListActivity.this, ArticleDetailActivity.class);
            startActivity(intent);
        });

        // LinearLayout untuk gambar kecil statis
        RelativeLayout smallArticleLayout = findViewById(R.id.small_article_layout);
        smallArticleLayout.setOnClickListener(v -> {
            // Intent ke TermOfServiceActivity
            Intent intent = new Intent(ArticleListActivity.this, ArticleDetailActivity.class);
            startActivity(intent);
        });
    }


}