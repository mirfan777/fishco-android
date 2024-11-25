package com.example.fishco.activity.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.auth.LoginActivity;
import com.example.fishco.activity.home.HomepageActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Tombol Back
        findViewById(R.id.back_button).setOnClickListener(v -> {
            // Intent ke HomepageActivity
            Intent intent = new Intent(ProfileActivity.this, HomepageActivity.class);
            startActivity(intent);
            finish(); // Agar tidak kembali ke ProfileActivity
        });

        // Handling system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // LinearLayout untuk Informasi Personal
        LinearLayout personalInfoLayout = findViewById(R.id.btnLinearLayout_personal_info);
        personalInfoLayout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, InformasiPersonalActivity.class);
            startActivity(intent);
        });

        // LinearLayout untuk Kebijakan Privasi
        LinearLayout btnPrivacyPolicy = findViewById(R.id.btnPrivacyPolicy);
        btnPrivacyPolicy.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
        });

        // LinearLayout untuk FAQ
        LinearLayout btnFAQ = findViewById(R.id.btnFAQ); // Pastikan ID sesuai dengan XML
        btnFAQ.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, FAQActivity.class);
            startActivity(intent);
        });

        // LinearLayout untuk Term of Service
        LinearLayout btnTermOfService = findViewById(R.id.linearLayout_term_of_service); // Pastikan ID sesuai dengan XML
        btnTermOfService.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, TermOfServiceActivity.class);
            startActivity(intent);
        });

        // Tombol Logout
        LinearLayout logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> logout());
    }

    // Metode Logout
    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Menghapus semua data yang disimpan di SharedPreferences
        editor.apply();

        // Arahkan pengguna ke halaman login
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear semua aktivitas sebelumnya
        startActivity(intent);
        finish(); // Tutup ProfileActivity
    }
}
