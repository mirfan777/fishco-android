package com.example.fishco.activity.article;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.fishco.R;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_detail);

        // Setup Edge-to-Edge Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Back Button
        ImageButton backButton = findViewById(R.id.back_button); // Ubah dari ImageView ke ImageButton
        backButton.setOnClickListener(v -> {
            System.out.println("Back button pressed"); // Debugging
            finish(); // Tutup aktivitas
        });

        // Tangkap data yang dikirim dari intent
        String title = getIntent().getStringExtra("ARTICLE_TITLE");
        String content = getIntent().getStringExtra("ARTICLE_CONTENT");
        String imageUrl = getIntent().getStringExtra("ARTICLE_IMAGE");
        String date = getIntent().getStringExtra("ARTICLE_DATE");

        // Hubungkan dengan UI
        TextView titleText = findViewById(R.id.article_title);
        TextView contentText = findViewById(R.id.article_content);
        TextView dateText = findViewById(R.id.article_date);
        ImageView articleImage = findViewById(R.id.article_image);

        // Set data ke UI
        titleText.setText(title);
        contentText.setText(content);
        dateText.setText(date);

        // Gunakan Glide untuk memuat gambar
        Glide.with(this)
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery) // Gambar default
                .into(articleImage);
    }
}