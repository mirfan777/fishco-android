package com.example.fishco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fishco.R;
import com.example.fishco.model.Fish;

import java.util.List;

public class FishCustomAdapter extends RecyclerView.Adapter<FishCustomAdapter.FishViewHolder> {

    private final Context context;
    private final List<Fish> fishList;

    public FishCustomAdapter(Context context, List<Fish> fishList) {
        this.context = context;
        this.fishList = fishList;
    }

    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fish, parent, false);
        return new FishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        Fish fish = fishList.get(position);
        holder.name.setText(fish.getName());
        holder.species.setText(fish.getSpecies());

        Glide.with(context)
                .load(fish.getUrlThumbnail())
                .placeholder(R.drawable.blob1_getstarted) // Gambar default
                .into(holder.thumbnail);

        holder.itemView.setOnLongClickListener(v -> {
            Toast.makeText(context, "ID Ikan: " + fish.getId(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    static class FishViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView name, species;

        public FishViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            species = itemView.findViewById(R.id.species);
        }
    }
}
