package com.example.fishco.activity.chatbot;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fishco.R;

public class ChatbotDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot_detail);

        // Handle Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Set up WebView
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Izinkan SSL yang tidak valid
            }
        });
        webView.setWebChromeClient(new WebChromeClient());

        // Load Botpress Webchat
        String botPressUrl = "https://cdn.botpress.cloud/webchat/v2.2/shareable.html?configUrl=https://files.bpcontent.cloud/2024/11/24/13/20241124130010-BWLBKSB5.json";
        webView.loadUrl(botPressUrl);
    }
}

//import android.os.Bundle;
//import android.webkit.WebChromeClient;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import com.example.fishco.R;
//
//public class ChatbotDetailActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chatbot_detail);
//
//        // Tangkap backButton dan set OnClickListener
//        ImageView backButton = findViewById(R.id.backButton);
//        backButton.setOnClickListener(v -> finish()); // Kembali ke aktivitas sebelumnya
//
//        // Tangkap pesan dari intent
//        String initialMessage = getIntent().getStringExtra("initial_message");
//
//        // Tangkap WebView dan set URL Botpress
//        WebView webView = findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);  // Enable JavaScript
//        webView.setWebViewClient(new WebViewClient());  // Agar link terbuka di dalam WebView
//        webView.setWebChromeClient(new WebChromeClient()); // Untuk menangani beberapa fitur
//
//        // Ganti URL Botpress sesuai kebutuhan
//        String botPressUrl = "https://your-botpress-url.com";
//        webView.loadUrl(botPressUrl);
//
//        // Tangkap pesan dari intent untuk menambah ke chatContainer
//        LinearLayout chatContainer = findViewById(R.id.chatContainer);
//        if (initialMessage != null) {
//            addMessageToContainer(chatContainer, initialMessage, true);
//        } else {
//            System.err.println("Initial message is null!");
//        }
//    }
//
//    // Method untuk menambahkan pesan ke chatContainer
//    private void addMessageToContainer(LinearLayout container, String message, boolean isAI) {
//        TextView textView = new TextView(this);
//        textView.setText(message);
//        textView.setTextSize(16);
//        textView.setPadding(16, 16, 16, 16);
//
//        if (isAI) {
//            textView.setBackgroundResource(R.drawable.chat_ai_background); // Background pesan AI
//        } else {
//            textView.setBackgroundResource(R.drawable.chat_user_background); // Background pesan user
//        }
//
//        // Tambahkan ke container
//        container.addView(textView);
//    }
//}
