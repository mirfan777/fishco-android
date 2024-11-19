package com.example.fishco.activity.chatbot;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fishco.R;

public class ChatbotDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot_detail);

        // Tangkap pesan dari intent
        String initialMessage = getIntent().getStringExtra("initial_message");

        // Tambahkan pesan ke chatContainer
        LinearLayout chatContainer = findViewById(R.id.chatContainer);
        if (initialMessage != null) {
            addMessageToContainer(chatContainer, initialMessage, true);
        } else {
            System.err.println("Initial message is null!");
        }
    }

    // Method untuk menambahkan pesan ke chatContainer
    private void addMessageToContainer(LinearLayout container, String message, boolean isAI) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextSize(16);
        textView.setPadding(16, 16, 16, 16);

        if (isAI) {
            textView.setBackgroundResource(R.drawable.chat_ai_background); // Background pesan AI
        } else {
            textView.setBackgroundResource(R.drawable.chat_user_background); // Background pesan user
        }

        // Tambahkan ke container
        container.addView(textView);
    }
}