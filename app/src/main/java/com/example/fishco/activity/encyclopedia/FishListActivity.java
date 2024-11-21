package com.example.fishco.activity.encyclopedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fishco.R;
import com.example.fishco.activity.aquarium.AquariumListActivity;
import com.example.fishco.activity.article.ArticleListActivity;
import com.example.fishco.activity.home.HomepageActivity;
import com.example.fishco.activity.scanner.ScannerActivity;
import com.example.fishco.adapter.FishCustomAdapter;
import com.example.fishco.http.RetrofitClient;
import com.example.fishco.model.Fish;
import com.example.fishco.service.AuthService;
import com.example.fishco.service.FishService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FishListActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    FishService fishService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fish_list);

        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        fetchFish(sharedPreferences.getString("token" , "no token"), Optional.empty());


        TextView categoryHeader = findViewById(R.id.category_name);
        if (categoryName != null) {
            categoryHeader.setText(categoryName);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(FishListActivity.this, HomepageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); // Menghindari instance baru
            startActivity(intent);
            finish();
        });

    }

    private void fetchFish(String token, Optional<String> category) {
        fishService = RetrofitClient.getClient(this).create(FishService.class);

        Call<List<Fish>> callFish = fishService.getAllFish(token, category);
        callFish.enqueue(new Callback<List<Fish>>() {
            @Override
            public void onResponse(Call<List<Fish>> call, Response<List<Fish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Fish> fishList = response.body();
                    FishCustomAdapter adapter = new FishCustomAdapter(FishListActivity.this, response.body());
                    RecyclerView recyclerView = findViewById(R.id.recycler_view_fish);
                    recyclerView.setLayoutManager(new LinearLayoutManager(FishListActivity.this));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Fish>> call, Throwable throwable) {
                Toast.makeText(FishListActivity.this, throwable.toString(), Toast.LENGTH_LONG).show();
                Log.d("data" , throwable.toString());
            }
        });
    }
}