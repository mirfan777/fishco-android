package com.example.fishco.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.fishco.activity.encyclopedia.FishDetailActivity;
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

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, FishDetailActivity.class);
            intent.putExtra("ID", fish.getId());
            intent.putExtra("NAME", fish.getName());
            intent.putExtra("KINGDOM", fish.getKingdom());
            intent.putExtra("PHYLUM", fish.getPhylum());
            intent.putExtra("CLASS", fish.getFish_class());
            intent.putExtra("ORDER", fish.getOrder());
            intent.putExtra("FAMILY", fish.getFamily());
            intent.putExtra("GENUS", fish.getGenus());
            intent.putExtra("SPECIES", fish.getSpecies());
            intent.putExtra("COLOUR", fish.getColour());
            intent.putExtra("FOOD_TYPE", fish.getFoodType());
            intent.putExtra("FOOD", fish.getFood());
            intent.putExtra("MIN_TEMPERATURE", String.valueOf(fish.getMinTemperature()));
            intent.putExtra("MAX_TEMPERATURE", String.valueOf(fish.getMaxTemperature()));
            intent.putExtra("MIN_PH", String.valueOf(fish.getMinPh()));
            intent.putExtra("MAX_PH", String.valueOf(fish.getMaxPh()));
            intent.putExtra("HABITAT", fish.getHabitat());
            intent.putExtra("OVERVIEW", fish.getOverview());
            intent.putExtra("THUMBNAIL", fish.getThumbnail());
            intent.putExtra("THUMBNAIL_URL", fish.getUrlThumbnail());
            intent.putExtra("AVERAGE_SIZE", fish.getAverageSize());
            intent.putExtra("CREATED_AT", fish.getCreatedAt());
            intent.putExtra("UPDATED_AT", fish.getUpdatedAt());

            context.startActivity(intent);
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
