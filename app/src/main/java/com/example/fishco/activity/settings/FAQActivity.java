package com.example.fishco.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fishco.R;
import com.example.fishco.activity.home.HomepageActivity;
import com.example.fishco.adapter.FAQAdapter;
import com.example.fishco.model.FAQItem;

import java.util.Arrays;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_faqactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tombol back pertama (misalnya untuk onBackPressed)
        ImageView backIcon = findViewById(R.id.back);
        backIcon.setOnClickListener(v -> onBackPressed());

        // Tombol back kedua (navigasi ke ProfileActivity)
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(FAQActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });

        // RecyclerView untuk FAQ
        RecyclerView faqRecyclerView = findViewById(R.id.faqRecyclerView);
        faqRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<FAQItem> faqList = Arrays.asList(
                new FAQItem("Bagaimana cara mengidentifikasi ikan?", "Saat mengambil foto, pastikan pencahayaannya bagus dan tanamanmu masuk ke dalam bingkai. Kamu bisa memotret bunga, daun, atau seluruh tanaman. Jangan menaruh banyak jenis tanaman dalam satu bingkai karena bisa menyebabkan kesalahan identifikasi. Selain itu, hindari foto yang gelap, terlalu terang, atau buram. Jika kamu tidak puas dengan hasilnya, coba lagi dengan mengikuti saran di atas."),
                new FAQItem("Tidak dapat mengidentifikasi ikan", "1. Jika Anda tidak dapat mengidentifikasi ikan, coba gunakan foto lain.\n" +
                        "\n" +
                        "2. Perhatikan, pastikan ikan terlihat jelas dan merupakan objek ikan tunggal dalam gambar.\n" +
                        "\n" +
                        "3. Jika masih tidak berhasil, Anda dapat mencari ikan secara manual melalui pencarian ikan kami."),
                new FAQItem("Berapa banyak ikan yang bisa diidentifikasi oleh Fishco?", "Saat ini, terdapat lebih dari 20.000 spesies ikan dalam database kami. \n" +
                        "\n" +
                        "Beberapa ikan belum memiliki deskripsi lengkap dan tips perawatan, namun tim ahli ikan terbaik kami sedang mengupayakannya, terus memperkaya database dengan informasi baru. Jika Anda memiliki pertanyaan atau saran tentang cara meningkatkan aplikasi kami, jangan ragu untuk menghubungi kami di fishco@gmail.com"),
                new FAQItem("Bagaimana cara saya mengetahui dengan cepat ikan mana yang membutuhkan perhatian?", "Fishco secara otomatis mengurutkan ikan Anda di aquarium berdasarkan urutan perawatan yang akan datang. Selain itu, Fishco akan mengirimkan notifikasi push jika ikan Anda membutuhkan perawatan."),
                new FAQItem("Bagaimana cara saya memperbarui jadwal perawatan ikan?", "Anda dapat memperbarui jadwal perawatan ikan di halaman aquarium atau halaman pengingat."),
                new FAQItem("Saya membutuhkan beberapa fitur/fungsi", "Berikan kami umpan balik dan kami akan memperbarui Fishco sesuai dengan keinginan Anda.")
                // Tambahkan lebih banyak item sesuai kebutuhan
        );

        FAQAdapter adapter = new FAQAdapter(faqList);
        faqRecyclerView.setAdapter(adapter);
    }
}