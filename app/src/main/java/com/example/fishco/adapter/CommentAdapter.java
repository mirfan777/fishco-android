package com.example.fishco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fishco.R;
import com.example.fishco.model.Comment;
import com.example.fishco.model.Reply;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private List<Comment> comments;

    // Perbaikan constructor untuk menerima context dan list komentar
    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);

        holder.commentUser.setText("User " + comment.getUser_id()); // Ganti dengan nama user yang sebenarnya
        holder.commentBody.setText(comment.getBody());
        holder.commentTimestamp.setText(comment.getCreatedAt().toString());

        // Setup replies
        ReplyAdapter replyAdapter = new ReplyAdapter(context, comment.getReplies());
        holder.replyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.replyRecyclerView.setAdapter(replyAdapter);

        // Logic to add reply
        holder.replyButton.setOnClickListener(v -> {
            String replyText = holder.replyInput.getText().toString().trim();
            if (!replyText.isEmpty()) {
                Reply reply = new Reply();
                reply.setUser_id(1L); // Replace with current user ID
                reply.setBody(replyText);

                // Gunakan Calendar untuk mendapatkan waktu saat ini
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String formattedDate = sdf.format(calendar.getTime());  // Format tanggal dan waktu

                comment.getReplies().add(reply);
                replyAdapter.notifyItemInserted(comment.getReplies().size() - 1);
                holder.replyInput.setText("");

                Toast.makeText(context, "Balasan ditambahkan!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Balasan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentUser, commentBody, commentTimestamp;
        RecyclerView replyRecyclerView;
        EditText replyInput;
        ImageButton replyButton;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentUser = itemView.findViewById(R.id.comment_user);
            commentBody = itemView.findViewById(R.id.comment_body);
            commentTimestamp = itemView.findViewById(R.id.comment_timestamp);
            replyRecyclerView = itemView.findViewById(R.id.comment_replies_recycler);
            replyInput = itemView.findViewById(R.id.reply_input);
            replyButton = itemView.findViewById(R.id.reply_button);
        }
    }
}
