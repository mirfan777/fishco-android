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

import com.example.fishco.R;
import com.example.fishco.activity.aquarium.AquariumDetailActivity;
import com.example.fishco.activity.article.ArticleDetailActivity;
import com.example.fishco.model.Aquarium;
import com.google.gson.Gson;

import java.util.List;

public class AquariumAdapter extends RecyclerView.Adapter<AquariumAdapter.AquariumViewHolder> {

    private final List<Aquarium> aquariumList;
    private final Context context;

    public AquariumAdapter(Context context, List<Aquarium> aquariumList) {
        this.context = context;
        this.aquariumList = aquariumList;
    }

    @NonNull
    @Override
    public AquariumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_aquarium, parent, false);
        return new AquariumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AquariumViewHolder holder, int position) {
        Aquarium aquarium = aquariumList.get(position);

        holder.aquariumName.setText(aquarium.getName());
        holder.aquariumReminderStatus.setText("Reminder belum diatur"); // Contoh teks

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AquariumDetailActivity.class);

            // Menggunakan Gson untuk mengonversi aquariumFishes ke JSON string
            Gson gson = new Gson();
            String aquariumFishesJson = gson.toJson(aquarium.getAquariumFishes());

            // Menambahkan data ke Intent
            intent.putExtra("id", aquarium.getId());
            intent.putExtra("user_id", aquarium.getUserId());
            intent.putExtra("name", aquarium.getName());
            intent.putExtra("volume_size", aquarium.getVolumeSize());
            intent.putExtra("type", aquarium.getType());
            intent.putExtra("filter_type", aquarium.getFilterType());
            intent.putExtra("filter_capacity", aquarium.getFilterCapacity());
            intent.putExtra("filter_media", aquarium.getFilterMedia());
            intent.putExtra("min_temperature", aquarium.getMinTemperature());
            intent.putExtra("max_temperature", aquarium.getMaxTemperature());
            intent.putExtra("min_ph", aquarium.getMinPh());
            intent.putExtra("max_ph", aquarium.getMaxPh());
            intent.putExtra("min_salinity", aquarium.getMinSalinity());
            intent.putExtra("max_salinity", aquarium.getMaxSalinity());
            intent.putExtra("turbidity", aquarium.getTurbidity());
            intent.putExtra("aquarium_fishes", aquariumFishesJson);

            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return aquariumList.size();
    }

    static class AquariumViewHolder extends RecyclerView.ViewHolder {
        TextView aquariumName;
        TextView aquariumReminderStatus;
        ImageView aquariumProfile;

        public AquariumViewHolder(@NonNull View itemView) {
            super(itemView);
            aquariumName = itemView.findViewById(R.id.aquarium_name);
            aquariumReminderStatus = itemView.findViewById(R.id.aquarium_reminder_status);
            aquariumProfile = itemView.findViewById(R.id.aquarium_profile);
        }
    }
}
