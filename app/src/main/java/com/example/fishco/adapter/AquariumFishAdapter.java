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
import com.example.fishco.model.Fish;

import java.util.List;

public class AquariumFishAdapter extends RecyclerView.Adapter<AquariumFishAdapter.FishViewHolder> {

    private final List<Fish> fishList;
    private final Context context;

    public AquariumFishAdapter(Context context, List<Fish> fishList) {
        this.context = context;
        this.fishList = fishList;
    }

    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_aquarium_fish, parent, false);
        return new FishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        Fish fish = fishList.get(position);
        holder.fishName.setText(fish.getName());
        Glide.with(context)
                .load(fish.getUrlThumbnail())
                .placeholder(R.drawable.freshwater)
                .into(holder.fishImage);
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    static class FishViewHolder extends RecyclerView.ViewHolder {
        TextView fishName;
        ImageView fishImage;

        public FishViewHolder(@NonNull View itemView) {
            super(itemView);
            fishName = itemView.findViewById(R.id.fish_name);
            fishImage = itemView.findViewById(R.id.fish_image);
        }
    }
}
