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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fishco.R;
import com.example.fishco.adapter.ProductAdapter;
import com.example.fishco.model.Fish;
import com.example.fishco.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DetailScannerActivity extends AppCompatActivity {
    private String diseaseName;
    private String diseaseType;
    private String description;
    private String prevention;
    private String symptoms;

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

        // Temukan elemen UI dari layout
        TextView diseaseNameView = findViewById(R.id.disease_name);
        TextView diseaseTypeView = findViewById(R.id.disease_type);
        TextView diseaseDescriptionView = findViewById(R.id.disease_description);
        TextView preventionView = findViewById(R.id.saran_perawatan_text);
        TextView symptomsView = findViewById(R.id.overview_text);

        // Setel data ke elemen UI
        diseaseNameView.setText(diseaseName != null ? diseaseName : "Tidak ada data");
        diseaseTypeView.setText(diseaseType != null ? diseaseType : "Tidak ada data");
        diseaseDescriptionView.setText(description != null ? description : "Tidak ada deskripsi tersedia");
        preventionView.setText(prevention != null ? prevention : "Tidak ada saran perawatan");
        symptomsView.setText(symptoms != null ? symptoms : "Tidak ada gejala");


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

        // RecyclerView untuk daftar produk
        RecyclerView productRecyclerView = findViewById(R.id.product_recycler_view);
        productRecyclerView.setLayoutManager(
                new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        );

//        // Get data produk dari intent
//        ArrayList<Product> products = new Gson().fromJson(
//                getIntent().getStringExtra("DISEASE_PRODUCTS"),
//                new TypeToken<ArrayList<Product>>() {}.getType()
//        );

        // Pasang adapter ke RecyclerView
        if (products != null && !products.isEmpty()) {
            ProductAdapter adapter = new ProductAdapter(this, products);
            productRecyclerView.setAdapter(adapter);
        }
    }
}
