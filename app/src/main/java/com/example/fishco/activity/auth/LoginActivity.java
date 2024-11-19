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
import com.example.fishco.model.User;
import com.example.fishco.service.AuthService;
import com.example.fishco.http.RetrofitClient;

import com.example.fishco.service.UserService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private AuthService authService;

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

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        authService = RetrofitClient.getClient(this).create(AuthService.class);

        if (sharedPreferences.getString("token" , "no token").equals("no token")){
            return ;
        }else {
            Intent intent = new Intent(this, HomepageActivity.class);
            startActivity(intent);
            finish();
        }

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
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String deviceName = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                login(email, password, deviceName);
            }
        });


    }

    private void login(String email, String password, String deviceName) {
        Call<TokenResponse> callToken = authService.requestToken(email, password, deviceName);

        callToken.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                String token = "Bearer " + response.body().getToken();

                UserService userService = RetrofitClient.getClient(LoginActivity.this).create(UserService.class);

                Call<User> callProfile = userService.getUserProfile(token);

                callProfile.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();

//                        Toast.makeText(LoginActivity.this,user.getName() , Toast.LENGTH_SHORT).show();
                        saveSharedPreferences(token , user);

                        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable throwable) {
                        Toast.makeText(LoginActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveSharedPreferences(String token , User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.putString("user_id" , user.getId());
        editor.putString("user_name" , user.getName());
        editor.putString("user_email" , user.getEmail());
        editor.putString("user_phone" , String.valueOf(user.getPhone_number()));
        editor.putString("user_role" , String.valueOf(user.getRole()));

        editor.apply();
        editor.commit();
    }


}