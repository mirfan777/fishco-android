package com.example.fishco.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.fishco.R; // Sesuaikan dengan paket Anda
import com.example.fishco.model.FAQItem;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQViewHolder> {
    private final List<FAQItem> faqList;
    private int selectedItemPosition = RecyclerView.NO_POSITION; // Menyimpan posisi item yang dipilih

    public FAQAdapter(List<FAQItem> faqList) {
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_faq_item, parent, false);
        return new FAQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
        FAQItem faq = faqList.get(position);

        // Set teks pertanyaan dan jawaban
        holder.questionTextView.setText(faq.getQuestion());
        holder.answerTextView.setText(faq.getAnswer());

        // Atur visibilitas jawaban berdasarkan item yang dipilih
        holder.answerTextView.setVisibility(
                selectedItemPosition == position ? View.VISIBLE : View.GONE
        );

        // Warna background dinamis berdasarkan item yang dipilih
        holder.questionTextView.setBackgroundResource(
                selectedItemPosition == position ? R.drawable.rounded_blue_background : R.drawable.rounded_gray_background
        );

        // Atur onClickListener untuk item
        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) {
                return; // Jika posisi tidak valid, keluar dari listener
            }

            // Logika expand/collapse
            if (selectedItemPosition == currentPosition) {
                selectedItemPosition = RecyclerView.NO_POSITION; // Collapse jika di-klik lagi
            } else {
                selectedItemPosition = currentPosition; // Expand item baru
            }

            notifyDataSetChanged(); // Refresh seluruh RecyclerView
        });
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    static class FAQViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView answerTextView;

        public FAQViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            answerTextView = itemView.findViewById(R.id.answerTextView); // ID dari layout yang sudah ditambahkan
        }
    }
}
