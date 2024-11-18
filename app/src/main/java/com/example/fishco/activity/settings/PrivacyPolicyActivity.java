package com.example.fishco.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishco.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_privacy_policy); // Ganti dengan nama layout XML yang sesuai
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView textView = findViewById(R.id.textView);

        String bulletList = "<ul>" +
                "<li>Cookie: Sejumlah kecil data yang dihasilkan oleh situs web dan disimpan oleh browser Anda. Cookie digunakan untuk mengidentifikasi browser, menyediakan analitik, dan mengingat informasi seperti preferensi bahasa atau data login Anda.</li>" +
                "<li>Perusahaan: Ketika Kebijakan ini menyebut \"Perusahaan,\" \"kami,\" \"milik kami,\" atau \"kita,\" itu merujuk pada ScaleUp, HACI İSA MAHALLESİ 75.YIL CUMHURİYET CAD. NO: 5 İÇ KAPI NO: 2 URLA / İZMİR, yang bertanggung jawab atas informasi Anda berdasarkan Kebijakan Privasi ini.</li>" +
                "<li>Negara: Tempat di mana Fishco atau pemilik/pendiri Fishco berbasis, yaitu Turki.</li>" +
                "<li>Pelanggan: Mengacu pada perusahaan, organisasi, atau individu yang mendaftar untuk menggunakan Layanan Fishco untuk mengelola hubungan dengan konsumen atau pengguna layanannya.</li>" +
                "<li>Perangkat: Perangkat yang terhubung ke internet seperti ponsel, tablet, komputer, atau perangkat lain yang dapat digunakan untuk mengakses Fishco dan layanan kami.</li>" +
                "<li>Alamat IP: Nomor yang diberikan ke setiap perangkat yang terhubung ke internet. Alamat IP sering kali digunakan untuk mengidentifikasi lokasi perangkat yang terhubung ke internet.</li>" +
                "<li>Personel: Individu yang bekerja untuk Fishco atau dikontrak untuk menyediakan layanan atas nama salah satu pihak.</li>" +
                "<li>Data Pribadi: Informasi apa pun yang secara langsung, tidak langsung, atau dalam hubungan dengan informasi lain (termasuk nomor identifikasi pribadi) memungkinkan identifikasi seseorang.</li>" +
                "<li>Layanan: Layanan yang disediakan oleh Fishco sebagaimana dijelaskan dalam syarat terkait (jika tersedia) dan di platform ini.</li>" +
                "<li>Layanan pihak ketiga: Mengacu pada pengiklan, sponsor kontes, mitra promosi dan pemasaran, serta pihak lain yang menyediakan konten kami atau produk/layanan yang kami pikir menarik bagi Anda.</li>" +
                "<li>Situs web: Situs Fishco yang dapat diakses melalui URL berikut: [tambahkan URL].</li>" +
                "<li>Anda: Individu atau entitas yang terdaftar dengan Fishco untuk menggunakan Layanan.</li>" +
                "</ul>";

        textView.setText(Html.fromHtml(bulletList, Html.FROM_HTML_MODE_LEGACY));

        // Back button functionality
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            // Navigate back to ProfileActivity
            Intent intent = new Intent(PrivacyPolicyActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish(); // Close current activity
        });

    }
}
