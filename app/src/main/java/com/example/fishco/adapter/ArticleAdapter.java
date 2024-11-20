package com.example.fishco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fishco.R;
import com.example.fishco.model.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final Context context;
    private final List<Article> articleList;

    public ArticleAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.title.setText(article.getTitle());
        holder.createdAt.setText(article.getCreatedAt());

        Glide.with(context)
                .load(article.getUrlThumbnail())
                .placeholder(android.R.drawable.ic_menu_gallery) // Gambar default bawaan Android
                .into(holder.thumbnail);

        holder.itemView.setOnClickListener(v -> {
            // Tambahkan logic untuk berpindah ke detail artikel (jika perlu)
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title, createdAt;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            createdAt = itemView.findViewById(R.id.created_at);
        }
    }
}