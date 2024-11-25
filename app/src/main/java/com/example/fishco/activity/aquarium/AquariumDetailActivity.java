package com.example.fishco.activity.aquarium;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fishco.R;
import com.example.fishco.adapter.AquariumFishAdapter;
import com.example.fishco.model.Fish;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AquariumDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aquarium_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        Long id = intent.getLongExtra("id", -1);
        Long userId = intent.getLongExtra("user_id", -1);
        String name = intent.getStringExtra("name");
        Float volumeSize = intent.getFloatExtra("volume_size", 0.0f);
        String type = intent.getStringExtra("type");
        String filterType = intent.getStringExtra("filter_type");
        String filterCapacity = intent.getStringExtra("filter_capacity");
        String filterMedia = intent.getStringExtra("filter_media");
        Float minTemperature = intent.getFloatExtra("min_temperature", 0.0f);
        Float maxTemperature = intent.getFloatExtra("max_temperature", 0.0f);
        Float minPh = intent.getFloatExtra("min_ph", 0.0f);
        Float maxPh = intent.getFloatExtra("max_ph", 0.0f);
        Float minSalinity = intent.getFloatExtra("min_salinity", 0.0f);
        Float maxSalinity = intent.getFloatExtra("max_salinity", 0.0f);
        Float turbidity = intent.getFloatExtra("turbidity", 0.0f);
        String aquariumFishesJson = intent.getStringExtra("aquarium_fishes");

        Gson gson = new Gson();
        Type fishListType = new TypeToken<List<Fish>>() {}.getType();
        List<Fish> aquariumFishes = gson.fromJson(aquariumFishesJson, fishListType);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_aquarium);
        AquariumFishAdapter adapter = new AquariumFishAdapter(this, aquariumFishes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }
}