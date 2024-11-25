package com.example.fishco.activity.aquarium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.article.ArticleListActivity;
import com.example.fishco.activity.chatbot.ChatbotActivity;
import com.example.fishco.activity.home.HomepageActivity;
import com.example.fishco.activity.scanner.ScannerActivity;
import com.example.fishco.http.RetrofitClient;
import com.example.fishco.model.Aquarium;
import com.example.fishco.service.AquariumService;
import com.example.fishco.service.FishService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AquariumListActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    AquariumService aquariumService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aquarium_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("AppPreferences" , MODE_PRIVATE);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                startActivity(new Intent(this, HomepageActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.article) {
                startActivity(new Intent(this, ArticleListActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.scanner) {
                startActivity(new Intent(this, ScannerActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.aquarium) {

                return true;
            } else if (itemId == R.id.fishbot) {
                startActivity(new Intent(this, ChatbotActivity.class));
                finish();
                return true;
            }

            return false;
        });
    }

    private void fetchAquarium(String token , Integer userId){
        aquariumService = RetrofitClient.getClient(this).create(AquariumService.class);

        Call<List<Aquarium>> callAquarium = aquariumService.getUserAquarium(token , userId);

        callAquarium.enqueue(new Callback<List<Aquarium>>() {
            @Override
            public void onResponse(Call<List<Aquarium>> call, Response<List<Aquarium>> response) {
                
            }

            @Override
            public void onFailure(Call<List<Aquarium>> call, Throwable throwable) {

            }
        });
    }
}