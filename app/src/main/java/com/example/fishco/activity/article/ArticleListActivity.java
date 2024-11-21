package com.example.fishco.activity.article;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.example.fishco.adapter.ArticleAdapter;
import com.example.fishco.adapter.GetStartedAdapter;
import com.example.fishco.http.RetrofitClient;
import com.example.fishco.model.Article;
import com.example.fishco.service.ArticleService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleListActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ArticleService articleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "no token");

        // Memulai proses fetch data artikel
        fetchArticles(token);

        // Mengatur insets untuk padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Konfigurasi bottom navigation
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
//        LinearLayout largeArticleLayout = findViewById(R.id.large_article_layout);
//        largeArticleLayout.setOnClickListener(v -> {
//            Intent intent = new Intent(ArticleListActivity.this, ArticleDetailActivity.class);
//            startActivity(intent);
//        });

//        // LinearLayout untuk gambar kecil statis
//        RelativeLayout smallArticleLayout = findViewById(R.id.small_article_layout);
//        smallArticleLayout.setOnClickListener(v -> {
//            Intent intent = new Intent(ArticleListActivity.this, ArticleDetailActivity.class);
//            startActivity(intent);
//        });
    }

    private void fetchArticles(String token) {
        articleService = RetrofitClient.getClient(this).create(ArticleService.class);

        Call<List<Article>> call = articleService.getAllArticles(token);
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Article> articleList = response.body();

                    ArticleAdapter adapter = new ArticleAdapter(ArticleListActivity.this, articleList);
                    RecyclerView recyclerView = findViewById(R.id.recycler_view_article);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ArticleListActivity.this));
                    recyclerView.setAdapter(adapter);
                } else {
                    List<Article> articleList = response.body();
                    Log.e("error", articleList.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable throwable) {
                Toast.makeText(ArticleListActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("error", throwable.getMessage());
            }
        });
    }
}
