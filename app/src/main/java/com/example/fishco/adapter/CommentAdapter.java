package com.example.fishco.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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
import com.example.fishco.activity.article.ArticleDetailActivity;
import com.example.fishco.http.RetrofitClient;
import com.example.fishco.model.Comment;
import com.example.fishco.model.Reply;
import com.example.fishco.service.ArticleService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> comments;
    private Context context;
    private Comment replyToComment = null;

    public CommentAdapter(Context context, List<Comment> comments ) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);

        holder.commentUser.setText(comment.getUserName());
        holder.commentBody.setText(comment.getBody());
        holder.commentTimestamp.setText(comment.getCreatedAt().toString());

        ReplyAdapter replyAdapter = new ReplyAdapter(comment.getReplies());
        holder.commentRepliesRecycler.setLayoutManager(new LinearLayoutManager(context));
        holder.commentRepliesRecycler.setAdapter(replyAdapter);

        // Logika balas v 2
//        holder.itemView.setOnClickListener(v -> {
//            replyToComment = comment; // Simpan komentar yang ingin dibalas
//            EditText commentInput = ((ArticleDetailActivity) context).findViewById(R.id.comment_input);
//            commentInput.setHint("Balas " + comment.getUserName());
//            commentInput.requestFocus();
//        });


//        // Logika untuk tombol "Balas"
        holder.replyButton.setOnClickListener(v -> {
            // Tampilkan dialog input balasan
            EditText input = new EditText(context);
            input.setHint("Tulis balasan...");

            new AlertDialog.Builder(context)
                    .setTitle("Balas Komentar")
                    .setView(input)
                    .setPositiveButton("Balas", (dialog, which) -> {
                        String replyText = input.getText().toString().trim();
                        if (!replyText.isEmpty()) {
                            // Simpan balasan (ini harus ke server dalam implementasi nyata)
                            Reply newReply = new Reply();
                            newReply.setCommentId(comment.getId());
                            newReply.setUserName("Anda"); // Ganti dengan nama user login
                            newReply.setBody(replyText);
                            newReply.setCreatedAt("Baru saja");

                            // Tambahkan balasan ke list
                            comment.getReplies().add(newReply);
                            replyAdapter.notifyItemInserted(comment.getReplies().size() - 1);
                        } else {
                            Toast.makeText(context, "Balasan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

//        // Add reply logic (optional)
//        holder.replyButton.setOnClickListener(v -> {
//            String replyText = holder.replyInput.getText().toString();
//            if (!replyText.isEmpty()) {
//                comment.getReplies().add(new Reply(null, comment.getId(), null, replyText, null, null));
//                replyAdapter.notifyItemInserted(comment.getReplies().size() - 1);
//                holder.replyInput.setText("");
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        public View replyButton;
        TextView commentUser, commentBody, commentTimestamp;
        RecyclerView commentRepliesRecycler;
//        EditText replyInput;
//        ImageButton replyButton;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentUser = itemView.findViewById(R.id.comment_user);
            commentBody = itemView.findViewById(R.id.comment_body);
            commentTimestamp = itemView.findViewById(R.id.comment_timestamp);
            replyButton = itemView.findViewById(R.id.reply_button); // Tambahkan ini
            commentRepliesRecycler = itemView.findViewById(R.id.comment_replies_recycler);
//            replyInput = itemView.findViewById(R.id.reply_input);
//            replyButton = itemView.findViewById(R.id.reply_button);
        }
    }

}