package com.example.fishco.activity.scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.model.Fish;
import com.example.fishco.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DetailScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scanner_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Long diseaseId = getIntent().getLongExtra("DISEASE_ID", -1);
        String diseaseName = getIntent().getStringExtra("DISEASE_NAME");
        String diseaseType = getIntent().getStringExtra("DISEASE_TYPE");
        String causeAgent = getIntent().getStringExtra("DISEASE_CAUSE_AGENT");
        String affectedPart = getIntent().getStringExtra("DISEASE_AFFECTED_PART");
        String prevention = getIntent().getStringExtra("DISEASE_PREVENTION");
        String description = getIntent().getStringExtra("DISEASE_DESCRIPTION");
        String symptoms = getIntent().getStringExtra("DISEASE_SYMPTOMS");
        String note = getIntent().getStringExtra("DISEASE_NOTE");
        String createdAt = getIntent().getStringExtra("DISEASE_CREATED_AT");
        String updatedAt = getIntent().getStringExtra("DISEASE_UPDATED_AT");

        ArrayList<Product> products = new Gson().fromJson(
                getIntent().getStringExtra("DISEASE_PRODUCTS"),
                new TypeToken<ArrayList<Product>>(){}.getType()
        );

        ArrayList<Fish> affectedFish = new Gson().fromJson(
                getIntent().getStringExtra("DISEASE_AFFECTED_FISH"),
                new TypeToken<ArrayList<Fish>>(){}.getType()
        );

        TextView fish_name = findViewById(R.id.fish_name);
        ImageView fish_image = findViewById(R.id.fish_image);

        byte[] imageBytes = getIntent().getByteArrayExtra("IMAGE_BITMAP");
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            fish_image.setImageBitmap(bitmap);
        }
        fish_name.setText(getIntent().getStringExtra("FISH_SPECIES"));

    }
}
