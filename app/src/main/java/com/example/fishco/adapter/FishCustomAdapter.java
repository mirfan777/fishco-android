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

public class FishCustomAdapter extends RecyclerView.Adapter<FishCustomAdapter.FishViewHolder> {

    private final Context context;
    private final List<Fish> fishList;
    private final OnFishClickListener listener;

    public FishCustomAdapter(Context context, List<Fish> fishList, OnFishClickListener listener) {
        this.context = context;
        this.fishList = fishList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_fish_list, parent, false);
        return new FishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        Fish fish = fishList.get(position);
        holder.name.setText(fish.getName());
        holder.species.setText(fish.getSpecies());


        Glide.with(context)
                .load(fish.getThumbnail())
                .into(holder.thumbnail);

        holder.itemView.setOnClickListener(v -> listener.onFishClick(fish));
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    public interface OnFishClickListener {
        void onFishClick(Fish fish);
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
