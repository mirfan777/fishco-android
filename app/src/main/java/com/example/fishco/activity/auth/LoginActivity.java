package com.example.fishco.activity.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.home.HomepageActivity;
import com.example.fishco.model.TokenResponse;
import com.example.fishco.service.LoginInterface;
import com.example.fishco.service.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private LoginInterface loginInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize sharedPreferences
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        loginInterface = RetrofitClient.getClient(this).create(LoginInterface.class);

        TextView textDaftar = findViewById(R.id.textDaftar);
        Button loginButton = findViewById(R.id.login_button);
        TextInputEditText inputEmail = findViewById(R.id.input_email);
        TextInputEditText inputPassword = findViewById(R.id.input_password);

        textDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                String email = inputEmail.getText().toString().trim();
//                String password = inputPassword.getText().toString().trim();
//                String deviceName = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                    Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                    startActivity(intent);

//                login(email, password, deviceName);
            }
        });


    }

    private void login(String email, String password, String deviceName) {
        Call<TokenResponse> call = loginInterface.requestToken(email, password, deviceName);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                String token = response.body().getToken();

                // save ke preferences
                saveToken(token);

                Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                
                startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }


}