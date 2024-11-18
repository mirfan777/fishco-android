package com.example.fishco.activity.encyclopedia;

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
import com.example.fishco.adapter.FishCustomAdapter;
import com.example.fishco.model.Fish;
import com.example.fishco.service.FishInterface;
import com.example.fishco.service.LoginInterface;
import com.example.fishco.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FishListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FishCustomAdapter adapter;
    private List<Fish> fishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fish_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fishList = new ArrayList<>();
        adapter = new FishCustomAdapter(this, fishList, fish -> {
            // Handle fish click
            Log.d("FishListActivity", "Clicked on: " + fish.getName());
        });
        recyclerView.setAdapter(adapter);

       
        fetchFishData();
    }

    private void fetchFishData() {
        FishInterface fishInterface = RetrofitClient.getAuthorizedClient(this).create(FishInterface.class);

        fishInterface.getAllFish().enqueue(new Callback<List<Fish>>() {
            @Override
            public void onResponse(Call<List<Fish>> call, Response<List<Fish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fishList.clear();
                    fishList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("FishListActivity", "Failed to get fish data");
                }
            }

            @Override
            public void onFailure(Call<List<Fish>> call, Throwable t) {
                Log.e("FishListActivity", "API call failed", t);
            }
        });
    }
}
