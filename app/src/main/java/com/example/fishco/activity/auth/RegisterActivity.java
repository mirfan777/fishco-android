package com.example.fishco.activity.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.home.HomepageActivity;
import com.example.fishco.http.RetrofitClient;
import com.example.fishco.service.AuthService;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextInputEditText nameInput, emailInput, passwordInput, confirmPasswordInput, phoneNumberInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Atur padding untuk sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        // Jika token ada, langsung redirect ke homepage
        if (!sharedPreferences.getString("token", "no token").equals("no token")) {
            startActivity(new Intent(this, HomepageActivity.class));
            finish();
        }

        // Inisialisasi input dan tombol
        nameInput = findViewById(R.id.input_name);
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        confirmPasswordInput = findViewById(R.id.input_confirm_password);
        phoneNumberInput = findViewById(R.id.input_telp);

        Button registerButton = findViewById(R.id.filledButton);
        registerButton.setOnClickListener(v -> handleRegister());
    }

    private void handleRegister() {
        // Ambil data input
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();
        String phoneNumber = phoneNumberInput.getText().toString().trim();
        String deviceName = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        // Validasi input
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password dan konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password harus minimal 6 karakter", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPhone(phoneNumber)) {
            Toast.makeText(this, "Nomor telepon tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        // Panggil API untuk registrasi
        AuthService authService = RetrofitClient.getClient(this).create(AuthService.class);
        Call<ResponseBody> call = authService.registerUser(name, email, password, confirmPassword, phoneNumber);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Toast.makeText(RegisterActivity.this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();

                        // Redirect ke login
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        Log.e("RegisterError", "Parsing error: " + e.getMessage());
                        showErrorDialog("Kesalahan saat memproses respons dari server.");
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("RegisterError", "Error response: " + errorBody);
                        showErrorDialog("Gagal registrasi: " + errorBody);
                    } catch (Exception e) {
                        Log.e("RegisterError", "Error body parsing: " + e.getMessage());
                        showErrorDialog("Gagal registrasi, kesalahan tidak diketahui.");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("RegisterError", "Request error: " + t.getMessage());
                showErrorDialog("Kesalahan jaringan: " + t.getMessage());
            }
        });
    }

    // Validasi email
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Validasi password (minimal 6 karakter)
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    // Validasi nomor telepon
    private boolean isValidPhone(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }

    // Menampilkan dialog error
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Kesalahan")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
