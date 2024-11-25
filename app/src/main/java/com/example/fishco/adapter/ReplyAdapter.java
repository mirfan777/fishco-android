package com.example.fishco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fishco.R;
import com.example.fishco.model.Reply;

import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {

    private List<Reply> replies;

    public ReplyAdapter(List<Reply> replies) {
        this.replies = replies;
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
        Reply reply = replies.get(position);
        holder.replyUser.setText(reply.getUserName());
        holder.replyBody.setText(reply.getBody());
        holder.replyTimestamp.setText(reply.getCreatedAt().toString());
    }

    @Override
    public int getItemCount() {
        return replies != null ? replies.size() : 0;
    }

    static class ReplyViewHolder extends RecyclerView.ViewHolder {
        TextView replyUser, replyBody, replyTimestamp;

        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);
            replyUser = itemView.findViewById(R.id.reply_user);
            replyBody = itemView.findViewById(R.id.reply_body);
            replyTimestamp = itemView.findViewById(R.id.reply_timestamp);
        }
    }
}
