package com.example.fishco.activity.chatbot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;
import com.example.fishco.activity.aquarium.AquariumListActivity;
import com.example.fishco.activity.article.ArticleListActivity;
import com.example.fishco.activity.home.HomepageActivity;
import com.example.fishco.activity.scanner.ScannerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Handler;

public class ChatbotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chatbot);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ambil TextView
        TextView inputField = findViewById(R.id.inputField);

        // Navigasi ke ChatbotDetailActivity saat TextView di klik
        inputField.setOnClickListener(v -> {
            Intent intent = new Intent(ChatbotActivity.this, ChatbotDetailActivity.class);
//            intent.putExtra("initial_message", "Hi, bagaimana saya bisa membantu anda?");
            startActivity(intent);
        });

        // Navbar
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                startActivity(new Intent(ChatbotActivity.this, HomepageActivity.class));
                finish(); // Close current activity
                return true;
            } else if (itemId == R.id.article) {
                startActivity(new Intent(ChatbotActivity.this, ArticleListActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.scanner) {
                startActivity(new Intent(ChatbotActivity.this, ScannerActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.aquarium) {
                startActivity(new Intent(ChatbotActivity.this, AquariumListActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.fishbot) {
                // Stay on the current page
                return true;
            }

            return false;
        });

        // Highlight item "Fishbot"
        bottomNavigation.setSelectedItemId(R.id.fishbot);
    }

    // Metode dipindahkan ke luar onCreate
    private void addMessageToContainer(LinearLayout container, String message, boolean isAI) {
        TextView textView = new TextView(this);
        textView.setTextSize(16);
        textView.setPadding(16, 16, 16, 16);

        if (isAI) {
            textView.setBackgroundResource(R.drawable.chat_ai_background);
        } else {
            textView.setBackgroundResource(R.drawable.chat_user_background);
        }

        container.addView(textView);

        if (isAI) {
            simulateTypingAnimation(textView, message);
        } else {
            textView.setText(message);
        }
    }

    private void simulateTypingAnimation(TextView textView, String message) {
        Handler handler = new Handler();
        StringBuilder displayedText = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            final int index = i;
            handler.postDelayed(() -> {
                displayedText.append(message.charAt(index));
                textView.setText(displayedText.toString());
            }, 50 * index); // 50ms per karakter
        }
    }
}