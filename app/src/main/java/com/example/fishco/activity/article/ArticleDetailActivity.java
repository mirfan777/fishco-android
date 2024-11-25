package com.example.fishco.activity.article;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fishco.R;
import com.example.fishco.adapter.CommentAdapter;
import com.example.fishco.model.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ArticleDetailActivity extends AppCompatActivity {

    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_detail);

        // Inisialisasi view
        EditText commentInput = findViewById(R.id.comment_input);
        ImageButton sendCommentButton = findViewById(R.id.send_comment_button);

        // Inisialisasi RecyclerView untuk komentar
        RecyclerView commentRecyclerView = findViewById(R.id.comment_recycler_view);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi adapter dengan Context dan List<Comment>
        commentAdapter = new CommentAdapter(this, comments);
        commentRecyclerView.setAdapter(commentAdapter);

        // Listener untuk tombol kirim komentar
        sendCommentButton.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString().trim();

            if (!commentText.isEmpty()) {
                addComment(commentText);
                commentInput.setText(""); // Kosongkan input setelah komentar dikirim
            } else {
                Toast.makeText(this, "Komentar tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            }
        });

        // Setup Edge-to-Edge Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Back Button
        ImageButton backButton = findViewById(R.id.back_button); // Ubah dari ImageView ke ImageButton
        backButton.setOnClickListener(v -> finish());

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

    private void addComment(String commentText) {
        // Ambil user ID dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String userIdString = sharedPreferences.getString("user_id", null);

        // Konversi user ID ke Long
        Long userId = null;
        if (userIdString != null) {
            try {
                userId = Long.parseLong(userIdString);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid user ID format!", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "User ID not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buat objek Comment baru
        Comment newComment = new Comment();
        newComment.setUser_id(userId);
        newComment.setBody(commentText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newComment.setCreatedAt(LocalDateTime.now()); // Gunakan LocalDateTime sesuai dengan model
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newComment.setUpdatedAt(LocalDateTime.now());
        }
        newComment.setArticle_id(1L); // Misalnya, ID artikel yang sesuai

        // Tambahkan komentar ke list dan perbarui adapter
        comments.add(0, newComment); // Menambah di awal
        commentAdapter.notifyItemInserted(0);
        commentRecyclerView.scrollToPosition(0); // Scroll ke posisi komentar baru
    }
}
