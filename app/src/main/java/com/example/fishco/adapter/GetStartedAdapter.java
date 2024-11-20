package com.example.fishco.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fishco.R;
import com.example.fishco.activity.article.ArticleDetailActivity;
import com.example.fishco.model.Article;

import java.util.List;

public class GetStartedAdapter extends RecyclerView.Adapter<GetStartedAdapter.ViewHolder> {

    private final List<Article> articles;
    private final Context context;

    public GetStartedAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_get_started, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articles.get(position);

        holder.title.setText(article.getTitle());
        holder.category.setText(article.getTitle());

        Glide.with(context)
                .load(article.getUrlThumbnail())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.thumbnail);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleDetailActivity.class);
            intent.putExtra("ARTICLE_TITLE", article.getTitle());
            intent.putExtra("ARTICLE_CONTENT", article.getBody());
            intent.putExtra("ARTICLE_IMAGE", article.getUrlThumbnail());
            intent.putExtra("ARTICLE_DATE", article.getCreatedAt());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title, category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.image_thumbnail);
            title = itemView.findViewById(R.id.text_title);
            category = itemView.findViewById(R.id.text_category);
        }
    }
}

