package com.example.fishco.service;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.fishco.R;
import com.example.fishco.util.AuthInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.api_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


    public static Retrofit getAuthorizedClient(Context context) {
        if (retrofit == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("auth_token", null);

            if (token != null) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new AuthInterceptor(token))
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(context.getString(R.string.api_url))
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }

        return retrofit;
    }
}

