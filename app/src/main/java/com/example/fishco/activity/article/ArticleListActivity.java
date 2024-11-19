package com.example.fishco.activity.article;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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



public class ArticleListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;

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

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch articles
        fetchArticles();

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

    private void fetchArticles() {
        Retrofit retrofit = RetrofitClient.getClient(this);
        retrofit.create(ArticleApiService.class)
                .getArticles()
                .enqueue(new Callback<List<Article>>() {
                    @Override
                    public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Article> articles = response.body();
                            adapter = new ArticleAdapter(ArticleListActivity.this, articles);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("API_ERROR", "Response not successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Article>> call, Throwable t) {
                        Log.e("API_ERROR", t.getMessage());
                    }
                });
    }
}