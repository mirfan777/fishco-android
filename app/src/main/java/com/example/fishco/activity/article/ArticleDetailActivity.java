package com.example.fishco.activity.article;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.fishco.http.RetrofitClient;
import com.example.fishco.model.Comment;
import com.example.fishco.model.TokenResponse;
import com.example.fishco.service.ArticleService;
import com.example.fishco.service.AuthService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleDetailActivity extends AppCompatActivity {

    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private ArticleService articleService;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        articleService = RetrofitClient.getClient(this).create(ArticleService.class);
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        ImageButton commentButton = findViewById(R.id.comment_button);
        EditText commentInput = findViewById(R.id.comment_input);
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Tangkap data yang dikirim dari intent
        String id = getIntent().getStringExtra("ARTICLE_ID");
        String title = getIntent().getStringExtra("ARTICLE_TITLE");
        String content = getIntent().getStringExtra("ARTICLE_CONTENT");
        String imageUrl = getIntent().getStringExtra("ARTICLE_IMAGE");
        String date = getIntent().getStringExtra("ARTICLE_DATE");

        fetchComment(id);

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

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = commentInput.getText().toString().trim();

                if (!commentText.isEmpty()) {
                    addComment(commentText);

                    commentInput.setText("");
                } else {
                    // Tampilkan pesan jika input kosong
                    Toast.makeText(ArticleDetailActivity.this, "Komentar tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void fetchComment(String tolol){
        String token = sharedPreferences.getString("token", null);
        Integer articleId = Integer.valueOf(getIntent().getStringExtra("ARTICLE_ID"));

        Call<List<Comment>> callComment = articleService.getAllCommentsByArticleId(token, Integer.valueOf(articleId));

        callComment.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                RecyclerView commentRecyclerView = findViewById(R.id.comment_recycler_view);
                commentRecyclerView.setLayoutManager(new LinearLayoutManager(ArticleDetailActivity.this));
                commentAdapter = new CommentAdapter(ArticleDetailActivity.this, response.body());
                commentRecyclerView.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable throwable) {
                Log.e("konotol" , throwable.toString());
            }
        });
    }

    private void addComment(String body) {
        String token = sharedPreferences.getString("token", null);
        Integer userId = Integer.valueOf(sharedPreferences.getString("user_id", null));
        Integer articleId = Integer.valueOf(getIntent().getStringExtra("ARTICLE_ID"));

        Call<Comment> callComment = articleService.sendComment(token  , articleId , userId , body);

        callComment.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Toast.makeText(ArticleDetailActivity.this, "Komentar berhasil dikirim!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable throwable) {
                Toast.makeText(ArticleDetailActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();

                Log.e("Comment" , throwable.toString());
            }
        });
    }

    private void addReply(){

    }
}
