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
    private final List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.info.setText("User ID: " + article.getUserId() + " • " + article.getCreatedAt());

        // Load image using Glide
        Glide.with(context)
                .load(article.getThumbnail())
                .placeholder(R.drawable.rounded_background_article)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView title, info;
        ImageView image;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.article_title);
            info = itemView.findViewById(R.id.article_info);
            image = itemView.findViewById(R.id.article_image);
        }
    }
}