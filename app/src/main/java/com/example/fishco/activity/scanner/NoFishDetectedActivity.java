package com.example.fishco.activity.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.encyclopedia.FishListActivity;

public class NoFishDetectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mengaktifkan Edge-to-Edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_no_fish_detected);

        // Menangani inset padding untuk kompatibilitas penuh layar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tombol "Go to Search"
        Button btnGoToSearch = findViewById(R.id.btn_go_to_search);
        btnGoToSearch.setOnClickListener(view -> {
            // Intent untuk menuju ke FishListActivity
            Intent intent = new Intent(NoFishDetectedActivity.this, FishListActivity.class);
            startActivity(intent);
        });

        // Tombol "Foto Ulang"
        Button btnFotoUlang = findViewById(R.id.btn_foto_ulang);
        btnFotoUlang.setOnClickListener(view -> {
            // Intent untuk kembali ke ScannerActivity
            Intent intent = new Intent(NoFishDetectedActivity.this, ScannerActivity.class);
            startActivity(intent);
            finish(); // Tutup activity saat ini untuk mencegah tumpukan
        });
    }
}