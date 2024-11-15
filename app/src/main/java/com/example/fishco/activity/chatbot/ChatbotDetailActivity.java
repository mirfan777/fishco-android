package com.example.fishco.activity.chatbot;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

public class ChatbotDetailActivity extends AppCompatActivity {

    // Deklarasi LinearLayout dan ScrollView
    private LinearLayout chatContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot_detail); // Pastikan layout XML sesuai

        // Inisialisasi LinearLayout chatContainer dan ScrollView
        chatContainer = findViewById(R.id.chatContainer);

        // Tambahkan beberapa pesan untuk percakapan
        addMessage("Halo! Ada yang bisa saya bantu?", false); // Pesan dari bot
        addMessage("Saya ingin bertanya tentang produk Fishco.", true); // Pesan dari user
    }

    // Fungsi untuk menambahkan pesan ke dalam LinearLayout
    private void addMessage(String message, boolean isUserMessage) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setPadding(16, 8, 16, 8);
        textView.setBackgroundResource(isUserMessage ? R.drawable.user_message_bg : R.drawable.bot_message_bg);
        textView.setTextColor(Color.BLACK);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 8, 0, 8);
        layoutParams.gravity = isUserMessage ? Gravity.END : Gravity.START;

        textView.setLayoutParams(layoutParams);
        chatContainer.addView(textView);

        // Scroll ke bawah ketika pesan baru ditambahkan
        final ScrollView scrollView = findViewById(R.id.chatScrollView);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}
