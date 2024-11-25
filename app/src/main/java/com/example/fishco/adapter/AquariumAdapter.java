package com.example.fishco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fishco.R;
import com.example.fishco.model.Aquarium;

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
        // Anda bisa tambahkan logika lain seperti gambar
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
