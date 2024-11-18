package com.example.fishco.http;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    private String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Get the original request
        Request originalRequest = chain.request();

        // Add the Authorization header if the token is available
        Request.Builder newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token); // Add the token here

        // Proceed with the request
        return chain.proceed(newRequest.build());
    }
}
