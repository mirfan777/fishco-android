package com.example.fishco.activity.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.home.HomepageActivity;
import com.example.fishco.http.RetrofitClient;
import com.example.fishco.model.User;
import com.example.fishco.service.AuthService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private TextInputEditText nameInput, emailInput, passwordInput, phoneNumberInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Atur padding untuk system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        // Redirect jika token ada
        if (!sharedPreferences.getString("token", "no token").equals("no token")) {
            startActivity(new Intent(this, HomepageActivity.class));
            finish();
        }

        // Inisialisasi input dan tombol
        nameInput = findViewById(R.id.input_name); // ID dari TextInputEditText
        emailInput = findViewById(R.id.input_email); // ID dari TextInputEditText
        passwordInput = findViewById(R.id.input_password); // ID dari TextInputEditText
        phoneNumberInput = findViewById(R.id.input_telp); // ID dari TextInputEditText

        Button registerButton = findViewById(R.id.filledButton);
        registerButton.setOnClickListener(v -> handleRegister());
    }

    private void handleRegister() {
        // Ambil data input
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String phoneNumber = phoneNumberInput.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Panggil API register
        AuthService authService = RetrofitClient.getClient(this).create(AuthService.class);
        Call<User> call = authService.registerUser(name, email, password, phoneNumber, "");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Registrasi berhasil. Silakan login.", Toast.LENGTH_SHORT).show();

                    // Redirect ke LoginActivity
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registrasi gagal. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}