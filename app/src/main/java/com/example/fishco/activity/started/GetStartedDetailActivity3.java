package com.example.fishco.activity.started;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;  // Add this import

import com.example.fishco.R;

public class GetStartedDetailActivity3 extends AppCompatActivity {  // Extend AppCompatActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_detail_3);

        findViewById(R.id.back_button).setOnClickListener(v -> finish());

        // Use the correct layout
    }
}
